package com.jericho2code.app_finance_manager.screens.add_edit_transaction

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.hideKeyboard
import com.jericho2code.app_finance_manager.showKeyboard
import kotlinx.android.synthetic.main.fragment_add_edit_transaction.*
import kotlinx.android.synthetic.main.view_toolbar.*

class AddEditTransactionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_edit_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setTitle(R.string.add_transaction)
        toolbar.inflateMenu(R.menu.save_transaction)
        toolbar.setNavigationOnClickListener {
            context?.hideKeyboard(this.view!!)
            findNavController(view).popBackStack()
        }
        context?.showKeyboard(transition_sum_input)
    }

    override fun onPause() {
        super.onPause()
        context?.hideKeyboard(view!!)
    }
}