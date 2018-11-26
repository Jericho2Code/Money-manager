package com.jericho2code.app_finance_manager.screens.add_edit_category.icon_selector_list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.extensions.getIdsFromTypedArray
import com.jericho2code.app_finance_manager.screens.add_edit_category.AddEditCategoryViewModel
import kotlinx.android.synthetic.main.fragment_category_icon_selector.*
import kotlinx.android.synthetic.main.view_toolbar.*

class CategoryIconSelectorFragment : Fragment() {

    private lateinit var viewModel: AddEditCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(AddEditCategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_category_icon_selector, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        toolbar.setTitle(R.string.add_icon_to_category)
        icon_list.adapter = IconsSelectorAdapter().apply {
            items = context?.getIdsFromTypedArray(R.array.category_icons) ?: emptyList()
            onItemClickListener = { iconId ->
                viewModel.setIconId(iconId)
                val colorSelector = CategoryIconColorSelectorBottomDialogFragment()
                colorSelector.show(childFragmentManager, colorSelector.tag)
            }
        }
    }
}