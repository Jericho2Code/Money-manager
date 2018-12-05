package com.jericho2code.app_finance_manager.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.jericho2code.app_finance_manager.model.entity.Account.Companion.ACCOUNT_TABLE

@Entity(tableName = ACCOUNT_TABLE)
data class Account(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var balance: Double = 0.0,
    var title: String? = null
) {
    companion object {
        const val ACCOUNT_TABLE = "account_table"
        const val ID = "id"
    }
}