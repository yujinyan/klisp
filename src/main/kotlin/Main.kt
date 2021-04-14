fun tokenize(string: String): List<String> {
  val splits = string
    .replace("\\n+".toRegex(), "")
    .replace("(", " ( ")
    .replace(")", " ) ")
    .split("\\s+".toRegex())

  return splits.subList(1, splits.lastIndex)
}

fun String.toAtom(): Atom = toIntOrNull()?.let { IntNumber(it) }
  ?: toFloatOrNull()?.let { FloatNumber(it) }
  ?: Symbol(this)

fun buildAst(tokens: Iterator<String>): Expr {
  val tokens = tokens.peekable()
  return when (val item = tokens.next()) {
    "(" -> {
      val list = when (tokens.peek()) {
        "if" -> Conditional()
        "define" -> Definition()
        "lambda" -> ProcedureDefinition()
        else -> ProcedureCall()
      }
      while (tokens.hasNext() && tokens.peek() != ")") list += buildAst(tokens)
      tokens.next() // consume ")"
      list
    }
    ")" -> error("unexpected )")
    else -> item.toAtom()
  }
}

fun buildAst(string: String): Expr = buildAst(tokenize(string).iterator())

@OptIn(ExperimentalStdlibApi::class)
fun buildAstList(string: String): List<Expr> = buildList {
  tokenize(string).iterator().let {
    while (it.hasNext()) add(buildAst(it))
  }
}


fun main() {
  buildAst("(+ 1 1)").evaluate(DefaultEnv()).also { println(it) }
}