package com.jericho2code.app_finance_manager.model.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class TransactionWithCategory(
    @Embedded
    var transaction: Transaction? = null,
    @Relation(parentColumn = "category_id", entityColumn = "id")
    var category: List<Category> = emptyList()
) : Parcelable