package com.jericho2code.app_finance_manager.screens.transaction_list

import com.jericho2code.app_finance_manager.model.entity.TransactionType
import com.jericho2code.app_finance_manager.model.entity.TransactionWithCategory
import org.threeten.bp.LocalDateTime

sealed class TransactionListItem

class TransactionRegularListItem(
    val transaction: TransactionWithCategory
) : TransactionListItem()

class TransactionHeaderListItem(
    val date: LocalDateTime,
    val dayDelta: Double,
    val transactionType: TransactionType
) : TransactionListItem()
