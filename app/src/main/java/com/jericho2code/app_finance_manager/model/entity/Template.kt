package com.jericho2code.app_finance_manager.model.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Template.TEMPLATE_TABLE)
class Template(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var usageCount: Long = 0,
    @ColumnInfo(name = "transaction_id")
    var transactionId: Long? = null,
    @ColumnInfo(name = "category_id")
    var categoryId: Long? = null
): Parcelable {
    companion object {
        const val TEMPLATE_TABLE = "template_table"
        const val ID = "id"
    }
}