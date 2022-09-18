package ru.netology.nmedia

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tag = "tag-stuff"
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likeCount?.text = viewModel.getCountDisplay(post.likes)
                shareCount?.text = viewModel.getCountDisplay(post.shared)
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_active_like_24 else R.drawable.ic_baseline_like_24
                )
            }
        }
        binding.like.setOnClickListener {
            Log.d(tag, "like")
            viewModel.like()
        }
        binding.share.setOnClickListener {
            Log.d(tag, "share")
            viewModel.share()
        }

    }

}