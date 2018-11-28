package com.jericho2code.app_finance_manager.screens.category_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.di.owners.ApplicationComponentOwner
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.view_toolbar.*


class CategoryListFragment : Fragment() {

    private val adapter = CategoryAdapter()
    private lateinit var viewModel: CategoryListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CategoryListViewModel::class.java)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusCategoryListComponent()
            ?.inject(viewModel)

        viewModel.categories().observe(this, Observer {
            adapter.items = it?.sortedBy { it.title } ?: emptyList()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_category_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        toolbar.setTitle(R.string.categories)
        category_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        category_list.adapter = adapter

        add_category_fab.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_categoryListFragment_to_addEditCategoryFragment)
        )
    }
}