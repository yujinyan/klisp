fun <T> Iterator<T>.peekable() = PeekableIterator(this)

class PeekableIterator<T>(
  private val iterator: Iterator<T>
) : Iterator<T> {

  private var peeked: T? = null

  fun peek(): T {
    val peeked = this.peeked ?: iterator.next()
    this.peeked = peeked
    return peeked
  }

  override fun hasNext(): Boolean = if (peeked != null) true else iterator.hasNext()

  override fun next(): T = peeked?.let<T, T> {
    peeked = null
    it
  } ?: iterator.next()
}
