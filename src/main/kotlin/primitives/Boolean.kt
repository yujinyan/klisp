package primitives

import Procedure

object And : Procedure {
  override fun invoke(args: List<Any>): Any {
    return args[1] as Boolean && args[2] as Boolean
  }
}

object Or : Procedure {
  override fun invoke(args: List<Any>): Any {
    return args[1] as Boolean || args[2] as Boolean
  }
}
