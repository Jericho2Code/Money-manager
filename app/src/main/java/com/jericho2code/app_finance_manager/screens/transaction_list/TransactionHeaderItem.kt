package com.jericho2code.app_finance_manager.screens.transaction_list

import com.jericho2code.app_finance_manager.model.entity.TransactionType
import org.threeten.bp.LocalDateTime

class TransactionHeaderItem(
    val date: LocalDateTime,
    val dayDelta: Double,
    val transactionType: TransactionType
)