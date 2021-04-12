typealias Frame = MutableMap<String, Any>

class Env(
  private val frames: ArrayDeque<Frame> = ArrayDeque()
) {
  init {
    push()
  }

  private val stack = frames.asReversed()
  private val current get() = stack.first()

  operator fun get(key: String): Any? {
    for (data in stack) {
      val item = data[key]
      if (item != null) return item
    }
    return null
  }

  operator fun set(key: String, value: Any) {
    current[key] = value
  }

  fun put(key: String, value: Any) = set(key, value)

  fun push() {
    frames.addLast(hashMapOf())
  }

  fun pop() {
    frames.removeLast()
  }
}

@Suppress("FunctionName")
fun DefaultEnv() = Env().apply {
  put("+", Add)
  put("-", Subtract)
  put("*", Multiply)
  put("/", Divide)
}

interface Procedure {
  operator fun invoke(args: List<Any>): Any
}

private fun List<Number>.op(
  floatOp: (acc: Float, x: Number) -> Float,
  intOp: (acc: Int, x: Number) -> Int
): Any = if (any { it is Float }) {
  drop(1).fold(first().toFloat(), floatOp)
} else {
  drop(1).fold(first().toInt(), intOp)
}

object Add : Procedure {
  override fun invoke(args: List<Any>): Any {
    val args = args as List<Number>
    val floatMode = args.any { it is Float }
    return if (floatMode) args.fold(0f) { x, y -> x + y.toFloat() }
    else args.fold(0) { x, y -> x + y.toInt() }
  }
}

object Subtract : Procedure {
  override fun invoke(args: List<Any>): Any = (args as List<Number>).op(
    { x, y -> x - y.toFloat() },
    { x, y -> x - y.toInt() }
  )
}

object Multiply : Procedure {
  override fun invoke(args: List<Any>): Any = (args as List<Number>).op(
    { x, y -> x * y.toFloat() },
    { x, y -> x * y.toInt() }
  )
}

object Divide : Procedure {
  override fun invoke(args: List<Any>): Any = (args as List<Number>).op(
    { x, y -> x / y.toFloat() },
    { x, y -> x / y.toInt() }
  )
}

