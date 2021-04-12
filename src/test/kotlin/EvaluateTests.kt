import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

val env = DefaultEnv()

class EvaluateTests : StringSpec({
  "1 + 1" {
    read("(+ 1 1)").evaluate(env) shouldBe 2
  }
  "nested" {
    read("(+ 1 (+ 2 3))").evaluate(env) shouldBe 6
  }

  "conditional" {
  }

  "definition" {
    read("(define a 1)").evaluate(env) shouldBe 1
  }

  "subtract" {
    read("(- 6 3 2 1)").evaluate(env) shouldBe 0
  }

  "complicated arithmetic" {
    arrayListOf(1, 2,3 )
    read("(+ (* 3 (+ (* 2 4) (+ 3 5))) (+ (- 10 7) 6 ))")
      .evaluate(env) shouldBe 57
  }

  "pretty format" {
    read(
      """
      (* (+ 2 (* 4 6))
         (+ 3 5 7))
    """.trimIndent()
    ).evaluate(env) shouldBe 390
  }

  "multiple expr" {
    read("(define x 1)").evaluate(env) shouldBe 1
    read("(+ x 1)").evaluate(env) shouldBe 2
  }

  "wrong 1 + 1" {

  }
})