package com.jericho2code.app_finance_manager.screens.add_edit_category

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jericho2code.app_finance_manager.R
import kotlinx.android.synthetic.main.bottom_sheet_fragment_color_selector.*


class CategoryBackgroundColorSelectorBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: AddEditCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(AddEditCategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_fragment_color_selector, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        color_slider.setOnColorChangeListener { _, _, color ->
            val buttonDrawable = DrawableCompat.wrap(select_color_button.background)
            DrawableCompat.setTint(buttonDrawable, color)
            select_color_button.background = buttonDrawable
        }

        select_color_button.setOnClickListener {
            viewModel.setBackgroundColor(color_slider.color)
            this.dismiss()
        }
    }

}