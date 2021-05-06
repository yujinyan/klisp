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

Types
- int
- float

## Test Programs

Currently, klisp can run programs from SICP like this.

```scheme
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
```

See https://github.com/yujinyan/klisp/blob/master/src/test/kotlin/SicpPrograms.kt for more test programs.



