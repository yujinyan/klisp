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
    val result = readFromTokens(tokenize("( + 1 1)").iterator())
    println(result)
  }
  "evaluate" {

  }
})