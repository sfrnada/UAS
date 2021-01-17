package com.github.api.ui.main

import android.content.Intent
import android.content.Intent.EXTRA_USER
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.api.data.model.User
import com.github.api.databinding.ActivityMainBinding
import com.github.api.ui.detail.DetailUserActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME,data.login)
                    startActivity(it)
                }
            }
        })
        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.apply {
            rv_login.layoutManager = LinearLayoutManager(this@MainActivity)
            rv_login.setHasFixedSize(true)
            rv_login.adapter =adapter

           search.setOnClickListener{

            }

            edit_query.setOnKeyListener{v, keycode, event ->
                if (event.action==KeyEvent.ACTION_DOWN&&keycode==KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true

                }
                return@setOnKeyListener false
            }

        }
        viewModel.getSearchUsers().observe(this,{
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }

        })
    }

    private fun searchUser(){
        binding.apply {
            var query = edit_query.text.toString()
            if (query.isEmpty())return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state:Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE

        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}