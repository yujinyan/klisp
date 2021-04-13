import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly

class TokenizeTests : StringSpec({
  "simple exp" {
    tokenize("( + 1 1)") shouldContainExactly listOf(
      "(", "+", "1", "1", ")"
    )
  }
  "extra spaces" {
    tokenize("  ( + 1    1)") shouldContainExactly listOf(
      "(", "+", "1", "1", ")"
    )
  }
  "ast" {
    val result = buildAst(tokenize("( + 1 1)").iterator())
    println(result)
  }
  "read from tokens" {
    val tokens = tokenize("(+ 1 1) (+ 2 3)")
    buildAst(tokens.iterator()).also { println(it) }
  }
  "multiple expressions" {
    """
      (define sqrt-iter (lambda (guess x)
        (if (good-enough? guess x)
          guess
          (sqrt-iter (improve guess x) x))))
      (define improve (lambda (guess x) 
        (average guess (/ x guess))))
      (define average (lambda (x y) 
        (/ (+ x y) 2)))
      (define good-enough? (lambda (guess x) 
        (< (abs (- (square guess) x)) 0.001)))
      (define square (lambda (x) (* x x)))
      (define abs (lambda (x) (if (< x 0) (- x) x)))
      (define sqrt (lambda (x) (sqrt-iter 1.0 x)))
      (sqrt 25)
    """.let {
      with(DefaultEnv()) {
        buildAstList(it).map { it.evaluate(this) }.also { println(it) }
      }
    }
  }
})