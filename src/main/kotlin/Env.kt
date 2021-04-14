import primitives.*

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
  put(">", GreaterThan)
  put("<", LessThan)
  put("and", And)
  put("or", Or)
  put("=", Equal)
}

interface Procedure {
  operator fun invoke(args: List<Any>): Any
}


