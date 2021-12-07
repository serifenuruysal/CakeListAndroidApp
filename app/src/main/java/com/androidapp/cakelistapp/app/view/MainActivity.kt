package com.androidapp.cakelistapp.app.view

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.androidapp.cakelistapp.app.view.adapter.CakeListAdapters
import com.androidapp.cakelistapp.app.viewmodel.MainViewModel
import com.androidapp.cakelistapp.app.viewmodel.Status
import com.androidapp.cakelistapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by Nur Uysal on 06/12/2021.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    //Handle orientation changes, ideally without reloading the list ((MVVM)
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var adapter: CakeListAdapters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUiComponents()
        setObservables()

    }

    private fun setUiComponents() {
        binding.rvCakeList.layoutManager = LinearLayoutManager(this)
        binding.rvCakeList.adapter = adapter

        // pull to refresh feature that reloads the list
        binding.swipeContainer.setOnRefreshListener(OnRefreshListener(mainViewModel::getCakeList))

    }

    //observe the datta changes
    private fun setObservables() {

        mainViewModel.data.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.swipeContainer.isRefreshing = false
                    it.data?.let { data ->
                        //Display the data to uer as a list
                        adapter.submitList(data)
                        //show fading animation after loading the data
                        startListAnimation()

                    }
                }
                Status.LOADING -> {
                    //show loading progress
                    binding.swipeContainer.isRefreshing = true
                }
                Status.ERROR -> {
                    binding.swipeContainer.isRefreshing = false
                    //Display an error message
                    it.errorMessage?.let { it1 -> showErrorDialog(it1) }


                }
            }
        })
    }

    //Provide an option to retry when an error is presented
    private fun showErrorDialog(message: String) {
        val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
        builder1.setTitle("Error")
        builder1.setMessage(message)


        builder1.setPositiveButton(
            "Try Again"
        ) { _, _ -> mainViewModel.getCakeList() }

        builder1.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }

        val alert11: AlertDialog = builder1.create()
        alert11.show()
    }

    private fun startListAnimation() {
        //fade in with alpha animation
        binding.rvCakeList.translationY = 0F
        binding.rvCakeList.alpha = 0f
        binding.rvCakeList.animate()
            .translationY(0F)
            .setDuration(3000)
            .alpha(1f)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }
}
