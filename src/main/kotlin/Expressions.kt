interface Expr {
  fun evaluate(env: Env): Any
}

interface ExprList : Expr {
  operator fun plusAssign(item: Expr)
}

inline class ProcedureCall(val list: MutableList<Expr> = mutableListOf()) : ExprList {
  override fun plusAssign(item: Expr) {
    list += item
  }

  override fun evaluate(env: Env): Any {
    val symbol = list[0] as Symbol
    val op = env[symbol.name] as Procedure
    return op(list.subList(1, list.size).map { it.evaluate(env) })
  }
}

inline class ProcedureDefinition(private val list: MutableList<Expr> = mutableListOf()) : ExprList {
  override fun evaluate(env: Env): Any {
    val params = (list[1] as ProcedureCall).list
    val body = list[2]

    return object : Procedure {
      override fun invoke(args: List<Any>): Any {
        env.push()

        params.forEachIndexed { index, expr ->
          env[(expr as Symbol).name] = args[index]
        }

        val result = body.evaluate(env)
        env.pop()
        return result
      }
    }
  }

  override fun plusAssign(item: Expr) {
    list += item
  }
}

inline class Conditional(private val list: MutableList<Expr> = mutableListOf()) : ExprList {
  override fun plusAssign(item: Expr) {
    list += item
  }

  override fun evaluate(env: Env): Any {
    val test = list[1].evaluate(env) as? Boolean ?: error("Cannot evaluate")
    return if (test)
      list[2].evaluate(env)
    else list[3].evaluate(env)
  }
}


inline class Definition(private val list: MutableList<Expr> = mutableListOf()) : ExprList {
  override fun plusAssign(item: Expr) {
    list += item
  }

  override fun evaluate(env: Env): Any {
    val symbol = list[1] as Symbol
    val result = list[2].evaluate(env)
    env[symbol.name] = result
    return result
  }
}

interface Atom : Expr
inline class Symbol(val name: String) : Atom {
  override fun evaluate(env: Env): Any = env[name] ?: error("cannot find Symbol[${name}]")
  override fun toString(): String = name
}

interface NumberAtom : Atom
inline class IntNumber(val value: Int) : NumberAtom {
  override fun evaluate(env: Env): Any = value
}

inline class FloatNumber(val value: Float) : NumberAtom {
  override fun evaluate(env: Env): Any = value
}
