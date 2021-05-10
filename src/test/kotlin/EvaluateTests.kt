import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

val env = DefaultEnv()

class EvaluateTests : StringSpec({
  "1 + 1" {
    buildAst("(+ 1 1)").evaluate(env) shouldBe 2
  }
  "nested" {
    buildAst("(+ 1 (+ 2 3))").evaluate(env) shouldBe 6
  }

  "conditional" {
    buildAst("(if (< 1 2) 1 0)").evaluate(env) shouldBe 1
    buildAst("(if (< 2 1) 1 0)").evaluate(env) shouldBe 0
  }

  "definition" {
    buildAst("(define a 1)").evaluate(env) shouldBe 1
  }

  "subtract" {
    buildAst("(- 6 3 2 1)").evaluate(env) shouldBe 0
  }

  "negative" {
    buildAst("(- 1)").evaluate(env) shouldBe -1
  }

  "complicated arithmetic" {
    arrayListOf(1, 2, 3)
    buildAst("(+ (* 3 (+ (* 2 4) (+ 3 5))) (+ (- 10 7) 6 ))")
      .evaluate(env) shouldBe 57
  }

  "pretty format" {
    buildAst(
      """
      (* (+ 2 (* 4 6))
         (+ 3 5 7))
    """.trimIndent()
    ).evaluate(env) shouldBe 390
  }

  "function definition syntactic sugar" {
    buildAst(
      """
        (define (square x) (* x x))
      """
    ).evaluate(env)
    buildAst("(square 10)").evaluate(env) shouldBe 100
  }

  "block structure" {
    buildAstList(
      """
  (define (sqrt x)
    (define (abs x)
      (if (< x 0) (* -1 x) x))
    (define (average x y)
      (/ (+ x y) 2))
    (define (square x)
      (* x x))
    (define (good-enough? guess)
      (< (abs (- (square guess) x)) 0.001))
    (define (improve guess)
      (average guess (/ x guess)))
    (define (sqrt-iter guess)
      (if (good-enough? guess)
          guess
          (sqrt-iter (improve guess))))
    (sqrt-iter 1.0))
  (sqrt 25)
    """
    ).map { it.evaluate(env) }
      .last()
      .shouldBeTypeOf<Float>()
      .shouldBe(5f plusOrMinus 0.001f)
  }

  "multiple expr" {
    buildAst("(define x 1)").evaluate(env) shouldBe 1
    buildAst("(+ x 1)").evaluate(env) shouldBe 2
  }

  "wrong 1 + 1" {

  }
})