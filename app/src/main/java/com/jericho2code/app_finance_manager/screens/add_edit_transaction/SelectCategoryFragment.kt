package com.jericho2code.app_finance_manager.screens.add_edit_transaction

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
import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import com.jericho2code.app_finance_manager.screens.category_list.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

class SelectCategoryFragment : Fragment() {

    @Inject
    lateinit var categoryRepository: CategoryRepository
    private val adapter = CategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusCategoryListComponent()
            ?.inject(this)
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
            findNavController().popBackStack()
        }
        toolbar.setTitle(R.string.select_category)
        category_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        category_list.adapter = adapter

        categoryRepository.categories().subscribe(
            {
                adapter.items = it
            }, {}
        )
    }
}