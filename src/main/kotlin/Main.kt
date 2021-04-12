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
        "lambda" -> ProcedureDefinition().apply {}
        else -> ProcedureCall()
      }
      while (tokens.hasNext() && tokens.peek() != ")") list += readFromTokens(tokens)
      tokens.next() // consume ")"
      list
    }
    ")" -> error("unexpected )")
    else -> item.toAtom()
  }
}

fun read(string: String): Expr = readFromTokens(tokenize(string).iterator())

fun Env.eval(string: String) = string
  .split("\\n".toRegex())
  .filterNot { it.isBlank() }
  .map { read(it).evaluate(this) }

fun main() {
  read("(+ 1 1)").evaluate(DefaultEnv()).also { println(it) }
}