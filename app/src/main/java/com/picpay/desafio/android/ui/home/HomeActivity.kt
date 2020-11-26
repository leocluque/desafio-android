package com.picpay.desafio.android.ui.home

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appfactory.domain.model.ContactModel
import com.picpay.desafio.android.R
import com.picpay.desafio.android.extensions.setVisible
import com.picpay.desafio.android.ui.home.adapter.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textError: TextView
    private val adapter by lazy {
        val adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter
    }
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
        handleViewStates()
        viewModel.getUsers()
    }

    private fun handleViewStates() {
        viewModel.run {
            homeViewState.observe(this@HomeActivity, Observer { state ->
                with(state) {
                    onLoadingState(isLoading)
                    onErrorState(showError)
                    onShowContactListState(contactList)
                }
            })
        }
    }

    private fun setView() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)
        textError = findViewById(R.id.text_error)
    }

    private fun onLoadingState(loading: Boolean) {
        progressBar.setVisible(loading)
        textError.setVisible(false)
    }

    private fun onErrorState(showError: Boolean) {
        textError.setVisible(showError)
        textError.text = getString(R.string.error)
        recyclerView.setVisible(false)
    }

    private fun onShowContactListState(list: List<ContactModel>) {
        recyclerView.setVisible(true)
        adapter.users = list
    }
}
