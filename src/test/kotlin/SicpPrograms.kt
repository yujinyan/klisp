import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SicpPrograms : StringSpec({
  "1.1.2 Naming and the Environment" {
    with(DefaultEnv()) {
      """
      (define pi 3.14159)
      (define radius 10)
      (* pi (* radius radius))
    """.let { eval(it) }.last() shouldBe 314.159f
    }
  }
  "1.1.4 Compound Procedures" {
    with(DefaultEnv()) {
      """
      (define square (lambda (x) (* x x)))
      (square 5)
    """.let { eval(it) }.last() shouldBe 25
    }
  }
})
