package com.submission.dicoding.githubuser.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.dicoding.githubuser.Activity.DetailActivity
import com.submission.dicoding.githubuser.Adapter.UserAdapter
import com.submission.dicoding.githubuser.GSONFile.GithubUser
import com.submission.dicoding.githubuser.ViewModel.FollowingViewModel
import com.submission.dicoding.githubuser.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter

    companion object {
        const val USERNAME = "username"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowingBinding.bind(view)

        followingViewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        val username = arguments?.getString(USERNAME).toString()
        followingViewModel.GetUserFollowing(username)
        showLoading(true)
        listFollowing()
    }

    private fun listFollowing() {
        followingViewModel.userFollowing.observe(requireActivity()) {
            adapter = UserAdapter(it)
            binding.rvListUser.adapter = adapter
            binding.rvListUser.layoutManager = LinearLayoutManager(requireActivity())
            showLoading(false)
            adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: GithubUser) {
                    Intent(requireActivity(), DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_DETAIL, data.login)
                        startActivity(it)
                    }
                }
            })
        }
        binding.rvListUser.setHasFixedSize(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}