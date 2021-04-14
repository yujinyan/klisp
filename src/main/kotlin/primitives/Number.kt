package primitives

import Procedure

object Add : Procedure {
  override fun invoke(args: List<Any>): Any {
    val args = args as List<Number>
    val floatMode = args.any { it is Float }
    return if (floatMode) args.fold(0f) { x, y -> x + y.toFloat() }
    else args.fold(0) { x, y -> x + y.toInt() }
  }
}

object Subtract : Procedure {
  override fun invoke(args: List<Any>): Any {
    if (args.size == 1) {
      return when (val operand = args.first()) {
        is Int -> -1 * operand
        is Float -> -1 * operand
        else -> error("$operand is not a number")
      }
    }

    return (args as List<Number>).accumulate(
      { x, y -> x - y.toFloat() },
      { x, y -> x - y.toInt() }
    )
  }
}

object Multiply : Procedure {
  override fun invoke(args: List<Any>): Any = (args as List<Number>).accumulate(
    { x, y -> x * y.toFloat() },
    { x, y -> x * y.toInt() }
  )
}

object Divide : Procedure {
  override fun invoke(args: List<Any>): Any = (args as List<Number>).accumulate(
    { x, y -> x / y.toFloat() },
    { x, y -> x / y.toInt() }
  )
}

object GreaterThan : Procedure {
  override fun invoke(args: List<Any>): Any = (args as List<Number>).firstTwo(
    { x, y -> x > y },
    { x, y -> x > y }
  )
}

object LessThan : Procedure {
  override fun invoke(args: List<Any>): Any = (args as List<Number>).firstTwo(
    { x, y -> x < y },
    { x, y -> x < y }
  )
}

object Equal : Procedure {
  override fun invoke(args: List<Any>): Any = args.all { it == args.first() }
}

private fun List<Number>.accumulate(
  floatOp: (acc: Float, x: Number) -> Float,
  intOp: (acc: Int, x: Number) -> Int
): Any = if (any { it is Float }) {
  drop(1).fold(first().toFloat(), floatOp)
} else {
  drop(1).fold(first().toInt(), intOp)
}

private fun List<Number>.firstTwo(
  floatOp: (x: Float, y: Float) -> Any,
  intOp: (x: Int, y: Int) -> Any
): Any = if (any { it is Float }) {
  floatOp(this[0].toFloat(), this[1].toFloat())
} else {
  intOp(this[0].toInt(), this[1].toInt())
}

