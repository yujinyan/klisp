fun s(vararg items: Any): Expr {
  val iter = items.iterator().peekable()
  val expr = when (iter.peek()) {
    "if" -> Conditional()
    "define" -> Definition()
    "lambda" -> ProcedureDefinition()
    else -> ProcedureCall()
  }

  while (iter.hasNext()) {
    val item = iter.next()
    expr += (item as? Expr) ?: item.toString().toAtom()
  }

  return expr
}

fun main() {
  s("define", "pi", 3.14159f)
  s("*", "pi", s("*", "radius", "radius")).also { println(it) }
}