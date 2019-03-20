package fa.kz.aua

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import fa.kz.aua.adapter.RepositoryRecyclerViewAdapter
import fa.kz.aua.entity.Repository
import fa.kz.aua.viewmodel.MainViewModel
import android.arch.lifecycle.Observer
import android.os.Handler
import android.support.v7.widget.SearchView
import fa.kz.aua.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), RepositoryRecyclerViewAdapter.OnItemClickListener {

    lateinit var binding: ActivityMainBinding
    private val repositoryRecyclerViewAdapter = RepositoryRecyclerViewAdapter(arrayListOf(), this)
    private val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.repositoryRv.layoutManager = LinearLayoutManager(this)
        binding.repositoryRv.adapter = repositoryRecyclerViewAdapter
        viewModel.repositories.observe(
            this,
            Observer<ArrayList<Repository>> { it -> it?.let { repositoryRecyclerViewAdapter.replaceData(it) } })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                viewModel.getSearchedData(text)
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    viewModel.getSearchedData(text)
                }, 300)
                return false
            }
        })

        viewModel.loadRepositories()
    }

    override fun onItemClick(position: Int) {

    }
}
