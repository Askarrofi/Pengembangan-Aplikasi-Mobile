import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
data class News(val title: String, val category: String)

class CounterManager {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()
    fun increment() { _count.value++ }
}

fun newsFlow(): Flow<News> = flow {
    val listBerita = listOf(
        News("Kotlin 2.0 Dirilis", "Tech"),
        News("Resep Nasi Goreng", "Food"),
        News("AI Makin Pintar", "Tech")
    )

    for (berita in listBerita) {
        delay(2000)
        emit(berita)
    }
}

suspend fun fetchDetail(judul: String): String {
    delay(500)
    return "Deskripsi lengkap untuk $judul"
}

fun main() = runBlocking {
    val counter = CounterManager()

    launch {
        counter.count.collect { println("Berita dibaca: $it") }
    }

    newsFlow()
        .filter { it.category == "Tech" }
        .map { "BERITA BARU: [${it.category}] ${it.title}" }
        .collect { berita ->
            println(berita)

            val detailDeferred = async { fetchDetail(berita) }
            val detail = detailDeferred.await()
            println(" -> $detail\n")

            counter.increment()
        }
}