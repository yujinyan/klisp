# Klisp

A Toy 🧸 Lisp interpreter written in Kotlin

## Inspiration

- [(How to Write a (Lisp) Interpreter (in Python))](https://norvig.com/lispy.html)
- [SICP](https://mitpress.mit.edu/sites/default/files/sicp/full-text/book/book.html)

## Supported Features

Syntax
- definition `(define symbol exp)`
- lambda definition `(lambda (symbol...) exp)`
- procedure call `(proc arg...)`
- conditional `(if test conseq alt)`
- function definition shorthand `(define (symbol param...) exp)`
- block structure

Types
- int
- float

## Test Programs

klisp can currently run programs from SICP like this.

```scheme
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
```

See https://github.com/yujinyan/klisp/blob/master/src/test/kotlin/SicpPrograms.kt for more test programs.

