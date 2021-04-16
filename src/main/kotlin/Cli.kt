import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option

class Cli : CliktCommand() {
  private val expressionToEvaluate: String? by option("-e", "--evaluate", help = "evaluate expression")

  override fun run() {
    val env = DefaultEnv()
    expressionToEvaluate?.let {
      println(buildAst(it).evaluate(env))
      return@run
    }

    repl()
  }
}