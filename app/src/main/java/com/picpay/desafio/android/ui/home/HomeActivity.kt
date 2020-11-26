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
import com.picpay.desafio.android.ui.home.states.ViewEvent
import com.picpay.desafio.android.ui.home.states.ViewState
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
        viewModel.getUsers()
        setObservables()
    }

    private fun setView() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)
        textError = findViewById(R.id.text_error)
    }

    private fun showLoading(loading: Boolean) {
        progressBar.setVisible(loading)
        textError.setVisible(false)
    }

    private fun showError() {
        textError.setVisible(true)
        textError.text = getString(R.string.error)
    }

    private fun setObservables() {
        viewModel.run {
            viewState.observe(this@HomeActivity, Observer {
                when (it) {
                    is ViewState.ShowContacts -> showContactList(it.contacts)
                }
            })
            viewEvent.observe(this@HomeActivity, Observer {
                when (it) {
                    is ViewEvent.ShowError -> showError()
                    is ViewEvent.ShowLoading -> showLoading(it.isLoading)
                }
            })
        }
    }

    private fun showContactList(list: List<ContactModel>) {
        adapter.users = list
    }
}
