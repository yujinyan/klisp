interface Expr {
  fun evaluate(env: Env): Any
}

sealed class ListExpr(
  protected val list: MutableList<Expr> = mutableListOf()
) : Expr, MutableList<Expr> by list {
  operator fun plusAssign(item: Expr) {
    list += item
  }

  override fun toString(): String {
    return "(" + list.joinToString(" ") + ")"
  }
}


class ProcedureCall(list: MutableList<Expr> = mutableListOf()) : ListExpr(list) {
  override fun evaluate(env: Env): Any {
    val symbol = list[0] as Symbol
    val op = (env[symbol.name] ?: error("Cannot find Symbol($symbol)")) as Procedure
    return op(list.subList(1, list.size).map { it.evaluate(env) })
  }
}

/**
 * ```
 * (define square (lambda (x) (* x x)))
 * ```
 */
class ProcedureDefinition() : ListExpr() {
  override fun evaluate(env: Env): Any {
    val params = (list[1] as? ProcedureCall) ?: error("${list[1]} is not a ProcedureCall")
    val body: List<Expr> = list.subList(2, list.size)

    return object : Procedure {
      override fun invoke(args: List<Any>): Any {
        env.push()

        params.forEachIndexed { index, expr ->
          env[(expr as Symbol).name] = args[index]
        }

        val result = body.asSequence()
          .map { it.evaluate(env) }
          .last()
        env.pop()
        return result
      }
    }
  }
}

class Conditional : ListExpr() {
  override fun evaluate(env: Env): Any {
    val test = list[1].evaluate(env) as? Boolean ?: error("Cannot evaluate")
    return if (test)
      list[2].evaluate(env)
    else list[3].evaluate(env)
  }
}


class Definition : ListExpr() {

  override fun evaluate(env: Env): Any {
    val firstItem = list[1]

    // (define (square x) (* x x))
    //         ^^^^^^^^^^
    if (firstItem is ProcedureCall) {
      val symbol = firstItem[0] as Symbol
      val expr = ProcedureDefinition().apply {
        this += Symbol("lambda")
        this += ProcedureCall(firstItem.drop(1).toMutableList())
        this += list.subList(2, list.size)
      }
      env[symbol.name] = expr.evaluate(env)
      return expr
    }

    val symbol = list[1] as Symbol
    val result = list[2].evaluate(env)
    env[symbol.name] = result
    return result
  }
}

interface Atom : Expr

@JvmInline
value class Symbol(val name: String) : Atom {
  override fun evaluate(env: Env): Any = env[name] ?: error("cannot find Symbol[${name}]")
  override fun toString(): String = name
}

interface NumberAtom : Atom

@JvmInline
value class IntNumber(val value: Int) : NumberAtom {
  override fun evaluate(env: Env): Any = value
}

@JvmInline
value class FloatNumber(val value: Float) : NumberAtom {
  override fun evaluate(env: Env): Any = value
}
