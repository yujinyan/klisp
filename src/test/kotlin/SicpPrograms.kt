import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class SicpPrograms : StringSpec({
  "1.1.2" {
    val env = DefaultEnv()
    """
      (define pi 3.14159)
      (define radius 10)
      (* pi (* radius radius))
    """
      .split("\\n".toRegex())
      .filterNot { it.isBlank() }
      .map { read(it).evaluate(env) }
      .last() shouldBe 314.159f
  }
  "1.1.4 Compound Procedures" {
    val env = DefaultEnv()
    """
      (define (square x) (* x x))
      (square 5)
    """
      .let { with(env) { run(it) } }
      .last() shouldBe 25
  }
})
