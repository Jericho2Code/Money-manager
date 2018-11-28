package com.jericho2code.app_finance_manager.screens.template_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.di.owners.ApplicationComponentOwner
import com.jericho2code.app_finance_manager.screens.add_edit_transaction.AddEditTransactionFragment
import kotlinx.android.synthetic.main.fragment_template_list.*
import kotlinx.android.synthetic.main.view_toolbar.*

class TemplateListFragment : Fragment() {

    private lateinit var viewModel: TemplateListViewModel
    private val adapter: TemplateAdapter = TemplateAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TemplateListViewModel::class.java)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusTemplateListComponent()
            ?.inject(viewModel)
        viewModel.templates().observe(this, Observer {
            adapter.items = it ?: emptyList()
        })
        adapter.onItemClickListener = { template ->
            findNavController().navigate(
                R.id.action_templateListFragment_to_addEditTransactionFragment,
                AddEditTransactionFragment.createArgs(template)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_template_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        toolbar.setTitle(R.string.templates)
        template_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        template_list.adapter = adapter
    }
}