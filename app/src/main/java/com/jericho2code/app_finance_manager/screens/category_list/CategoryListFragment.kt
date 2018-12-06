package com.jericho2code.app_finance_manager.screens.category_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.di.owners.ApplicationComponentOwner
import com.jericho2code.app_finance_manager.application.extensions.gone
import com.jericho2code.app_finance_manager.application.extensions.visible
import com.jericho2code.app_finance_manager.utils.ScreenState
import com.jericho2code.app_finance_manager.utils.StateFragment
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.view_toolbar.*

class CategoryListFragment : StateFragment<CategoryListViewModel>() {
    private val adapter = CategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.categories().observe(this, Observer { categories ->
            if (categories?.isNullOrEmpty() == true) {
                viewModel.setState(ScreenState.EMPTY)
            } else {
                adapter.items = categories.sortedBy { it.title }
                viewModel.setState(ScreenState.CONTENT)
            }
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

    override fun provideViewModel(): CategoryListViewModel {
        val viewModel = ViewModelProviders.of(this).get(CategoryListViewModel::class.java)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusCategoryListComponent()
            ?.inject(viewModel)
        return viewModel
    }

    override fun showLoading() {
        category_list_progress.visible()
        category_list.gone()
    }

    override fun showContent() {
        category_list_progress.gone()
        category_list.visible()
    }

    override fun showError() {}

    override fun showEmpty() {
        category_list_empty.visible()
        category_list_progress.gone()
        category_list.gone()
    }
}