import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

val env = DefaultEnv()

class EvaluateTests : StringSpec({
  "1 + 1" {
    read("(+ 1 1)").evaluate(env) shouldBe 2
  }
  "nested" {
    read("(+ 1 (+ 2 3))").evaluate(env) shouldBe 6
  }

  "wrong 1 + 1" {

  }
})