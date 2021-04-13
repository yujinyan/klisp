import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class EnvironmentTests : StringSpec({
  "name shadow" {
    val env = DefaultEnv()
    env["x"] = 1
    env.push()
    env["x"] = 2
    env["x"] shouldBe 2
    env.pop()
    env["x"] shouldBe 1
  }
})