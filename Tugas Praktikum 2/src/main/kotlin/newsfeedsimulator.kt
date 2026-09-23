import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class News(val id: Int, val category: String)

class CounterManager {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()
    fun increment() { _count.value++ }
}
fun newsFlow(): Flow<News> = flow {
    val listSumberBerita = listOf(
        News(1, "Tech"),
        News(2, "Food"),
        News(3, "Entertainment"),
        News(4, "Sports"),
        News(5, "Politics"),
        News(6, "Lifestyle"),
        News(7, "Education")
    )

    repeat(10) {
        delay(2000)
        val beritaAcak = listSumberBerita.random()
        emit(beritaAcak)
    }
}

suspend fun fetchDetail(judul: String): String {
    delay(500)
    return "Deskripsi lengkap untuk $judul"
}

fun main() = runBlocking {
    val counter = CounterManager()

    val job = launch {
        counter.count.collect { println("Berita dibaca: $it") }
    }

    newsFlow()
        .filter { it.category in listOf("Tech", "Sports", "Food") }
        .map { "BERITA BARU: ${it.id} [${it.category}]" }
        .collect { berita ->
            println(berita)

            val detailDeferred = async { fetchDetail(berita) }
            val detail = detailDeferred.await()
            println(" -> $detail\n")

            counter.increment()
        }

    job.cancel()
}