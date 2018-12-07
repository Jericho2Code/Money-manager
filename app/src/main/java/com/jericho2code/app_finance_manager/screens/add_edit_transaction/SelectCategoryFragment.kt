package com.jericho2code.app_finance_manager.screens.add_edit_transaction

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
import com.jericho2code.app_finance_manager.model.entity.Category
import com.jericho2code.app_finance_manager.screens.category_list.CategoryAdapter
import com.jericho2code.app_finance_manager.screens.category_list.CategoryListViewModel
import com.jericho2code.app_finance_manager.utils.*
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

class SelectCategoryFragment : StateFragment<CategoryListViewModel>() {
    @Inject
    lateinit var viewModelFactory: TransactionAddEditViewModelFactory
    lateinit var sharedViewModel: AddEditTransactionViewModel
    private val adapter = CategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusTransactionAddEditComponent()
            ?.inject(this)
        sharedViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(AddEditTransactionViewModel::class.java)


        viewModel.categories().observe(this, Observer { categories ->
            if (categories?.isNullOrEmpty() == true) {
                viewModel.setState(EmptyState())
            } else {
                viewModel.setState(ContentState(categories.sortedBy { it.title }))
            }
        })
        adapter.onItemClickListener = {
            sharedViewModel.setCategory(it)
            findNavController().navigateUp()
        }
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
        toolbar.setTitle(R.string.select_category)
        category_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        category_list.adapter = adapter

        add_category_fab.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_selectCategoryFragment_to_addEditCategoryFragment)
        )

    }

    override fun provideViewModel(): CategoryListViewModel {
        val viewModel = ViewModelProviders.of(activity!!).get(CategoryListViewModel::class.java)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusCategoryListComponent()
            ?.inject(viewModel)
        return viewModel
    }

    override fun onStateChange(state: State) {
        when (state) {
            is LoadingState -> showLoading()
            is ContentState<*> -> {
                val contentValue = (state as? ContentState<List<Category>>)?.value
                contentValue?.let { showContent(it) }
            }
            is EmptyState -> showEmpty()
        }
    }

    private fun showLoading() {
        category_list_progress.visible()
        category_list.gone()
    }

    private fun showContent(content: List<Category>) {
        adapter.items = content
        category_list_progress.gone()
        category_list.visible()
    }

    private fun showEmpty() {
        category_list_empty.visible()
        category_list_progress.gone()
        category_list.gone()
    }
}