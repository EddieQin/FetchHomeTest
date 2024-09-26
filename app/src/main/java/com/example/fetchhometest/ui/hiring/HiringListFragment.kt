package com.example.fetchhometest.ui.hiring

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchhometest.R
import com.example.fetchhometest.databinding.FragmentHiringListBinding
import com.example.fetchhometest.ui.adapter.ItemCardAdapter
import com.google.android.material.tabs.TabLayout

class HiringListFragment: Fragment() {
    private var _binding: FragmentHiringListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HiringListViewModel by viewModels()
    private lateinit var adapter: ItemCardAdapter
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHiringListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout()
        setupRecyclerView()
        setupFilterButton()
        observeViewModel()

    }

    private fun setupTabLayout() {
        tabLayout = binding.tablayout

        tabLayout.addTab(tabLayout.newTab().setText("All"))
        for (i in 1..4) {
            tabLayout.addTab(tabLayout.newTab().setText("ListId $i"))
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.updateListFilter(tab.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


    private fun setupRecyclerView() {
        adapter = ItemCardAdapter(emptyList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupFilterButton() {
        binding.filterButton.setOnClickListener{
            viewModel.filterItems()
        }
    }

    private fun observeViewModel() {
        viewModel.hiringList.observe(viewLifecycleOwner) { items ->
            adapter.updateItems(items)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.hiring_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                viewModel.toggleFilter()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}