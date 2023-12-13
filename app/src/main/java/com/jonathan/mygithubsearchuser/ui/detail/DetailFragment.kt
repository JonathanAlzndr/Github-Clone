package com.jonathan.mygithubsearchuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonathan.mygithubsearchuser.data.response.ItemsItem
import com.jonathan.mygithubsearchuser.databinding.FragmentDetailBinding
import com.jonathan.mygithubsearchuser.ui.ListGithubAdapter
import com.jonathan.mygithubsearchuser.ui.helper.ViewModelFactory

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ARG_POSITION, 0)
        val username = arguments?.getString(ARG_USERNAME)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFragment.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFragment.addItemDecoration(itemDecoration)

        username?.let {
            position?.let { position ->
                if(position == 1) {
                    viewModel.showFollowers(username)
                } else {
                    viewModel.showFollowing(username)
                }
            }
        }

        viewModel.followers.observe(viewLifecycleOwner) {
            setShowFollowers(it)
        }

        viewModel.following.observe(viewLifecycleOwner) {
            setShowFollowing(it)
        }

        viewModel.isLoadingFragment.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setShowFollowers(list: List<ItemsItem>) {
        val adapter = ListGithubAdapter()
        adapter.submitList(list)
        binding.rvFragment.adapter = adapter
    }

    private fun setShowFollowing(list: List<ItemsItem>) {
        val adapter = ListGithubAdapter()
        adapter.submitList(list)
        binding.rvFragment.adapter = adapter
    }

    private fun showLoading(loading: Boolean) {
        if(loading) binding.progressBar3.visibility = View.VISIBLE
        else binding.progressBar3.visibility = View.INVISIBLE
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }

}