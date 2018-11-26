package com.jericho2code.app_finance_manager.screens.add_edit_category.icon_selector_list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.extensions.color
import com.jericho2code.app_finance_manager.screens.add_edit_category.AddEditCategoryViewModel
import kotlinx.android.synthetic.main.bottom_sheet_fragment_icon_color_selector.*

class CategoryIconColorSelectorBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: AddEditCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(parentFragment!!).get(AddEditCategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_fragment_icon_color_selector, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        select_color_button.setOnClickListener {
            viewModel.setIconColor(
                context!!.color(
                    when (color_select_group.checkedChipId) {
                        R.id.color_light -> R.color.icon_white
                        R.id.color_dark -> R.color.icon_grey
                        else -> R.color.icon_white
                    }
                )
            )
            findNavController().navigateUp()
        }
    }
}