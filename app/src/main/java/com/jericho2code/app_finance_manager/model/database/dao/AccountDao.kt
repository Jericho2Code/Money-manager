package com.jericho2code.app_finance_manager.model.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.jericho2code.app_finance_manager.model.entity.Account
import com.jericho2code.app_finance_manager.model.entity.Account.Companion.ACCOUNT_TABLE
import com.jericho2code.app_finance_manager.model.entity.Account.Companion.ID

@Dao
interface AccountDao : BaseDao<Account> {
    @Query("SELECT * FROM $ACCOUNT_TABLE")
    fun accounts(): LiveData<List<Account>>

    @Query("SELECT * FROM $ACCOUNT_TABLE WHERE $ID = :id")
    fun accountById(id: Long): LiveData<Account>
}