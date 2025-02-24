package com.capstone.cleanup.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.cleanup.databinding.FragmentMainBinding
import com.capstone.cleanup.ui.ViewModelFactory
import com.capstone.cleanup.ui.adpter.ArticleAdapter
import com.capstone.cleanup.ui.adpter.ReportAdapter

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var reportAdapter: ReportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleAdapter = mainViewModel.articleAdapter
        reportAdapter = mainViewModel.reportAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        val layoutManager0 = LinearLayoutManager(requireActivity())
        val layoutManager1 = LinearLayoutManager(requireActivity())
        layoutManager0.orientation = RecyclerView.HORIZONTAL
        layoutManager1.orientation = RecyclerView.HORIZONTAL
        binding?.rvArticle?.layoutManager = layoutManager0
        binding?.rvReport?.layoutManager = layoutManager1

        binding?.rvArticle?.adapter = articleAdapter
        binding?.rvReport?.adapter = reportAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding?.progressBar?.visibility = View.VISIBLE
        else binding?.progressBar?.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        articleAdapter.startListening()
        reportAdapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        articleAdapter.stopListening()
        reportAdapter.stopListening()
    }
}