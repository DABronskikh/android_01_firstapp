package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryFileImpl

private val empty = Post(
    id = 0,
    content = "",
    video = null,
    author = "",
    likedByMe = false,
    published = "",
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFileImpl(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        clearEdited()
    }

    fun clearEdited() {
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content != text) {
            edited.value = edited.value?.copy(content = text)
        }
    }

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

    fun likeById(id: Long) = repository.likeById(id)
    fun removeById(id: Long) = repository.removeById(id)

}
