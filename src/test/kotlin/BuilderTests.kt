import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BuilderTests : StringSpec({
  "factorial" {
    val env = DefaultEnv()
    s(
      "define", "factorial", s(
        "lambda", s("n"), s(
          "if", s("=", "n", 1), 1, s("*", "n", s("factorial", s("-", "n", 1)))
        )
      )
    ).evaluate(env)

    s("factorial", 6).evaluate(env) shouldBe 720
  }
})