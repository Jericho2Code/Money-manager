package com.jericho2code.app_finance_manager.screens.transaction_list

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.getColorsFromTypedArray
import kotlinx.android.synthetic.main.fragment_transaction_list.*


class TransactionListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_transaction_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transition_list.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        transition_list.adapter = TransactionAdapter()
        add_transaction_fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_transactionListFragment_to_addEditTransactionFragment2))

//        val data = mutableListOf(PieEntry(17950f, 0f), PieEntry(9606f, 1f), PieEntry(5850f, 2f), PieEntry(4509f, 3f))
//        val dataSet = PieDataSet(data, "Test")
//        dataSet.valueTextSize = 16f
//        dataSet.valueTextColor = Color.WHITE
//        dataSet.colors = context?.getColorsFromTypedArray(R.array.item_category_colors) ?: emptyList()
//        pie_chart.data = PieData(dataSet)
//        pie_chart.legend.isEnabled = false
//        pie_chart.isDrawHoleEnabled = false
//        pie_chart.description = Description().apply { text = "" }
    }
}