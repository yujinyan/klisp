import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PeekableTests : StringSpec({
  "peekable" {
    val iter = listOf(1, 2, 3, 4, 5).iterator().peekable()
    iter.peek() shouldBe 1
    iter.peek() shouldBe 1
    iter.next() shouldBe 1

    iter.next() shouldBe 2
    iter.peek() shouldBe 3
    iter.peek() shouldBe 3
    iter.next() shouldBe 3
  }
})