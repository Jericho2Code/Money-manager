package com.jericho2code.app_finance_manager.model.entity

enum class TransactionType(
    val value: String
) {
    SPENDING_TRANSACTION("spending"), PROFIT_TRANSACTION("profit");

    companion object {
        fun fromString(value: String) = when(value) {
            "spending" -> SPENDING_TRANSACTION
            "profit" -> PROFIT_TRANSACTION
            else -> SPENDING_TRANSACTION
        }
    }
}