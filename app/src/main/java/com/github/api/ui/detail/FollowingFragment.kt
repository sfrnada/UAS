package com.github.api.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.api.R
import com.github.api.databinding.FragmenFollowBinding
import com.github.api.ui.main.UserAdapter

class FollowingFragment:Fragment(R.layout.fragmen_follow) {
    private var _binding:FragmenFollowBinding?=null
    private val binding get()=_binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args=arguments
        username=args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmenFollowBinding.bind(view)


        adapter= UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvLogin.setHasFixedSize(true)
            rvLogin.layoutManager= LinearLayoutManager(activity)
            rvLogin.adapter=adapter
        }

        showLoading(true)
        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner,{
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    private fun showLoading(state:Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE

        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}