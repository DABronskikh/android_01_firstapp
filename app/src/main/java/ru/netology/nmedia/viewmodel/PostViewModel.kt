package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)

    fun getCountDisplay(count: Int): String {
        return if (count == 0) {
            ""
        } else if (count <= 999) {
            count.toString()
        } else if (count in 1_000..1_099) {
            "%.0fK".format((count.toFloat() / 1_000))
        } else if (count in 1_100..99_999) {
            "%.1fK".format((count.toFloat() / 1_000))
        } else if (count in 1_000_000..1_099_999) {
            "%.0fK".format((count.toFloat() / 1_000))
        } else {
            "%.1fM".format((count.toFloat() / 1_000_000))
        }
    }
}
