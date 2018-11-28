package com.jericho2code.app_finance_manager.screens.add_edit_transaction

import android.app.DatePickerDialog
import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class TransactionDatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: AddEditTransactionViewModel

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        viewModel.setTransactionDate(LocalDateTime.of(year, month + 1, day, 0, 0, 0))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(DATE) as? LocalDate ?: LocalDate.now()
        viewModel = ViewModelProviders.of(activity!!).get(AddEditTransactionViewModel::class.java)
        return DatePickerDialog(activity!!, this, date.year, date.monthValue - 1, date.dayOfMonth)
    }

    companion object {
        const val DATE = "date"
        fun instance(date: LocalDate = LocalDate.now()) = TransactionDatePickerDialog().apply {
            arguments = Bundle().apply {
                putSerializable(DATE, date)
            }
        }
    }
}