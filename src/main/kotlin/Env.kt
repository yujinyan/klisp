class Env(
  private val map: MutableMap<String, Any> = mutableMapOf()
) : MutableMap<String, Any> by map

@Suppress("FunctionName")
fun DefaultEnv() = Env().apply {
  put("+", Sum)
}

interface Procedure {
  operator fun invoke(args: List<Any>): Any
}

object Sum : Procedure {
  override fun invoke(args: List<Any>): Any {
    val args = args as List<Number>
    val floatMode = args.any { it is Float }
    return if (floatMode) args.fold(0f) { x, y -> x + y.toFloat() }
    else args.fold(0) { x, y -> x + y.toInt() }
  }
}


fun sum(nums: Array<Number>): Number {
  val floatMode = nums.any { it is Float }
  return if (floatMode) nums.fold(0f) { x, y -> x + y.toFloat() }
  else nums.fold(0) { x, y -> x + y.toInt() }
}