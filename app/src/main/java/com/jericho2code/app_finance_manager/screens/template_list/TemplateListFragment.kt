package com.jericho2code.app_finance_manager.screens.template_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.jericho2code.app_finance_manager.screens.add_edit_transaction.AddEditTransactionFragment
import com.jericho2code.app_finance_manager.utils.ScreenState
import com.jericho2code.app_finance_manager.utils.StateFragment
import kotlinx.android.synthetic.main.fragment_template_list.*
import kotlinx.android.synthetic.main.view_toolbar.*

class TemplateListFragment : StateFragment<TemplateListViewModel>() {

    private val adapter: TemplateAdapter = TemplateAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.templates().observe(this, Observer { templates ->
            if (templates?.isNullOrEmpty() == true) {
                viewModel.setState(ScreenState.EMPTY)
            } else {
                adapter.items = templates.sortedByDescending { it.template?.usageCount }
                viewModel.setState(ScreenState.CONTENT)
            }
        })
        adapter.onItemClickListener = { template ->
            template.template?.let {
                viewModel.incrementTemplateUsageCount(it).subscribe({
                    findNavController().navigate(
                        R.id.action_templateListFragment_to_addEditTransactionFragment,
                        AddEditTransactionFragment.createArgs(template)
                    )
                }, {
                    Snackbar.make(view!!, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
                })
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

    override fun showLoading() {
        template_list_progress.visible()
        template_list.gone()
    }

    override fun showContent() {
        template_list_progress.gone()
        template_list.visible()
    }

    override fun showError() {}

    override fun showEmpty() {
        template_list_empty.visible()
        template_list_progress.gone()
        template_list.gone()
    }
}