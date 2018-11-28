package com.jericho2code.app_finance_manager.screens.add_edit_category

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.di.owners.ApplicationComponentOwner
import com.jericho2code.app_finance_manager.application.extensions.*
import com.jericho2code.app_finance_manager.model.entity.Category
import com.jericho2code.app_finance_manager.model.entity.TransactionType
import kotlinx.android.synthetic.main.fragment_add_edit_category.*
import kotlinx.android.synthetic.main.view_category_color_item.*
import kotlinx.android.synthetic.main.view_category_icon_item.*
import kotlinx.android.synthetic.main.view_toolbar.*

class AddEditCategoryFragment : Fragment() {

    private lateinit var viewModel: AddEditCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(AddEditCategoryViewModel::class.java)
        (activity?.application  as? ApplicationComponentOwner)
            ?.applicationComponent()
            ?.plusCategoryAddEditComponent()
            ?.inject(viewModel)

        viewModel.backgroundColorLiveData.observe(this, Observer { color ->
            setColorViewBackgroundColor(color)
        })
        viewModel.iconIdLiveData.observe(this, Observer { iconId ->
            category_icon.setImageDrawable(context?.drawable(iconId ?: R.drawable.ic_money))
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_edit_category, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setTitle(R.string.add_category)
        toolbar.inflateMenu(R.menu.save_category)
        toolbar.setNavigationOnClickListener {
            context?.hideKeyboard(this.view!!)
            findNavController().navigateUp()
        }

        color_selector_field.setOnClickListener {
            val colorSelector = CategoryBackgroundColorSelectorBottomDialogFragment()
            colorSelector.show(childFragmentManager, colorSelector.tag)
        }

        icon_selector_field.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_addEditCategoryFragment_to_categoryIconSelectorFragment)
        )

        setColorViewBackgroundColor(viewModel.backgroundColorLiveData.value)

        toolbar.menu.findItem(R.id.save_changes).setOnMenuItemClickListener {
            context?.hideKeyboard(this.view!!)
            viewModel.saveCategory(
                Category(
                    title = category_title_input.text.toString(),
                    baseTransactionType = when (category_operation_type_group.checkedChipId) {
                        R.id.spending -> TransactionType.SPENDING_TRANSACTION
                        R.id.profit -> TransactionType.PROFIT_TRANSACTION
                        else -> TransactionType.SPENDING_TRANSACTION
                    },
                    backgroundColor = viewModel.backgroundColorLiveData.value ?: context!!.color(R.color.icon_grey),
                    iconIdName = context?.nameForId(viewModel.iconIdLiveData.value ?: R.drawable.ic_money)
                )
            ).subscribe({
                context?.showToast(R.string.category_saved)
                findNavController().navigateUp()
            }, {})
            true
        }
    }

    private fun setColorViewBackgroundColor(color: Int?) {
        color_view.background = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(color ?: context!!.color(R.color.icon_grey))
        }
    }

    override fun onDetach() {
        super.onDetach()
        viewModel.iconIdLiveData.value = null
        viewModel.backgroundColorLiveData.value = null
    }

}