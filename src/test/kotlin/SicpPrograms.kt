import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SicpPrograms : StringSpec({
  fun String.eval(): Any {
    val string = this
    return with(DefaultEnv()) { eval(string) }.last()
  }

  "1.1.2 Naming and the Environment" {
    """
      (define pi 3.14159)
      (define radius 10)
      (* pi (* radius radius))
    """.eval() shouldBe 314.159f
  }

  "1.1.4 Compound Procedures" {
    """
      (define square (lambda (x) (* x x)))
      (square 5)
    """.eval() shouldBe 25
  }

  "1.1.6 Conditionals" {
    """
      (define abs (lambda (x) (if (< x 0) (- x) x)))
      (abs -1)
    """.eval() shouldBe 1
  }

  "1.1.7 Square Roots by Newton's Method" {
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
    """.eval().also { println(it) }
  }
})
