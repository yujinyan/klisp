interface Expr {
  fun evaluate(env: Env): Any
}

interface ExprList : Expr {
  operator fun plusAssign(item: Expr)
}

inline class ProcedureCall(private val list: MutableList<Expr> = mutableListOf()) : ExprList {
  override fun plusAssign(item: Expr) {
    list += item
  }

  override fun evaluate(env: Env): Any {
    val symbol = list[0] as Symbol
    val op = env[symbol.name] as Procedure
    return op(list.subList(1, list.size).map { it.evaluate(env) })
  }
}

inline class Conditional(private val list: MutableList<Expr> = mutableListOf()) : ExprList {
  override fun plusAssign(item: Expr) {
    list += item
  }

  override fun evaluate(env: Env): Any {
    val test = list[0].evaluate(env) as? Boolean ?: error("Cannot evaluate")
    return if (test) list[1].evaluate(env) else list[2].evaluate(env)
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


fun tokenize(string: String): List<String> {
  val splits = string.replace("(", " ( ")
    .replace(")", " ) ")
    .split("\\s+".toRegex())

  return splits.subList(1, splits.lastIndex)
}

fun String.toAtom(): Atom = toIntOrNull()?.let { IntNumber(it) }
  ?: toFloatOrNull()?.let { FloatNumber(it) }
  ?: Symbol(this)

fun readFromTokens(tokens: Iterator<String>): Expr {
  val tokens = tokens.peekable()
  return when (val item = tokens.next()) {
    "(" -> {
      val list = when (tokens.peek()) {
        "if" -> Conditional()
        "define" -> Definition()
        else -> ProcedureCall()
      }
      while (tokens.hasNext() && tokens.peek() != ")") list += readFromTokens(tokens)
      tokens.next()
      list
    }
    ")" -> error("unexpected )")
    else -> item.toAtom()
  }
}

fun read(string: String): Expr = readFromTokens(tokenize(string).iterator())

fun main() {
  read("(+ 1 1)").evaluate(DefaultEnv()).also { println(it) }
}