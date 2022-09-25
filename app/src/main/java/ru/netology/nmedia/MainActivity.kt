package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLikeListener(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShareListener(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun getCountDisplay(count: Int): String {
                return viewModel.getCountDisplay(count)
            }

            override fun onRemoveListener(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEditListener(post: Post) {
                viewModel.edit(post)

            }

        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                with(binding.content) {
                    requestFocus()
                    setText(post.content)
                }
                binding.close.visibility = View.VISIBLE

            } else {
                return@observe
            }
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT,
                    ).show()
                    return@setOnClickListener
                } else {
                    viewModel.changeContent(text.toString())
                    viewModel.save()

                    setText("")
                    clearFocus()
                    binding.close.visibility = View.GONE
                    AndroidUtils.hideKeyboard(this)
                }
            }
        }

        binding.close.setOnClickListener {
            with(binding.content) {
                setText("")
                clearFocus()
            }
            binding.close.visibility = View.GONE
            viewModel.clearEdited()
        }

    }

}