package com.submission.dicoding.githubuser.Activity

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.dicoding.githubuser.Adapter.SectionsPagerAdapter
import com.submission.dicoding.githubuser.R
import com.submission.dicoding.githubuser.ViewModel.DetailViewModel
import com.submission.dicoding.githubuser.databinding.ActivityDetailactivityBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailactivityBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EXTRA_DETAIL = "extra_detail"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = getString(R.string.app_name_account)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        detailUser()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun detailUser() {
        detailViewModel.detailUser.observe(this) {
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .circleCrop()
                    .into(binding.ivProfile)
                tvFollowerDetail.text =
                    StringBuilder(it.followers_count.toString()).append(" Followers")
                tvFollowingDetail.text =
                    StringBuilder(it.followingcount.toString()).append(" Following")
                tvNamaDetail.text =
                    StringBuilder(it.nama)
                tvIdDetail.text = StringBuilder(it.id.toString())
                tvUsernameDetail.text = it.login
                val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity)
                sectionsPagerAdapter.username = it.login
                viewPager.adapter = sectionsPagerAdapter
                val tabLayout: TabLayout =
                    findViewById(R.id.tabs)
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        val detailUser = intent.getStringExtra(EXTRA_DETAIL).toString()
        detailViewModel.getUserDetail(detailUser)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}