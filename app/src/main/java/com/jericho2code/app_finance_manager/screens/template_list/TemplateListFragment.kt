package com.jericho2code.app_finance_manager.screens.template_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.di.owners.ApplicationComponentOwner
import com.jericho2code.app_finance_manager.application.extensions.gone
import com.jericho2code.app_finance_manager.application.extensions.visible
import com.jericho2code.app_finance_manager.model.entity.TemplateFullObject
import com.jericho2code.app_finance_manager.screens.add_edit_transaction.AddEditTransactionViewModel
import com.jericho2code.app_finance_manager.screens.add_edit_transaction.TransactionAddEditViewModelFactory
import com.jericho2code.app_finance_manager.utils.*
import kotlinx.android.synthetic.main.fragment_template_list.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

class TemplateListFragment : StateFragment<TemplateListViewModel>() {

    @Inject
    lateinit var viewModelFactory: TransactionAddEditViewModelFactory
    private lateinit var sharedViewModel: AddEditTransactionViewModel
    private val adapter: TemplateAdapter = TemplateAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusTransactionAddEditComponent()
            ?.inject(this)
        sharedViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(AddEditTransactionViewModel::class.java)
        viewModel.templates().observe(activity!!, Observer { templates ->
            if (templates?.isNullOrEmpty() == true) {
                viewModel.setState(EmptyState())
            } else {
                viewModel.setState(ContentState(templates.sortedByDescending { it.template?.usageCount }))
            }
        })
        adapter.onItemClickListener = { template ->
            template.template?.let {
                viewModel.incrementTemplateUsageCount(it)
                sharedViewModel.setTemplate(template)
                findNavController().popBackStack()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_template_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        toolbar.setTitle(R.string.templates)
        template_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        template_list.adapter = adapter
    }

    override fun provideViewModel(): TemplateListViewModel {
        val viewModel = ViewModelProviders.of(this).get(TemplateListViewModel::class.java)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusTemplateListComponent()
            ?.inject(viewModel)
        return viewModel
    }

    override fun onStateChange(state: State) {
        when (state) {
            is LoadingState -> showLoading()
            is ContentState<*> -> {
                val contentValue = (state as? ContentState<List<TemplateFullObject>>)?.value
                contentValue?.let { showContent(contentValue) }
            }
            is EmptyState -> showEmpty()
        }
    }

    private fun showLoading() {
        template_list_progress.visible()
        template_list.gone()
    }

    private fun showContent(content: List<TemplateFullObject>) {
        adapter.items = content
        template_list_progress.gone()
        template_list.visible()
    }

    private fun showEmpty() {
        template_list_empty.visible()
        template_list_progress.gone()
        template_list.gone()
    }
}