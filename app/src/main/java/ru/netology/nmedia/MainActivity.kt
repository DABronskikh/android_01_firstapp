package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tag = "tag-stuff"
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            shared = 999,
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_active_like_24)
            }
            likeCount?.text = getCountDisplay(post.likes)
            shareCount?.text = getCountDisplay(post.shared)

            share.setOnClickListener {
                Log.d(tag, "share")
                post.shared++
                shareCount?.text = getCountDisplay(post.shared)
            }

            root.setOnClickListener {
                Log.d(tag, "stuff")
            }

            avatar.setOnClickListener {
                Log.d(tag, "avatar")
            }

            like?.setOnClickListener {
                Log.d(tag, "like")
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_active_like_24 else R.drawable.ic_baseline_like_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likeCount?.text = getCountDisplay(post.likes)
            }
        }
    }

    private fun getCountDisplay(count: Int): String {
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