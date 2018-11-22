package com.jericho2code.app_finance_manager.application.di.owners

import com.jericho2code.app_finance_manager.application.di.components.ApplicationComponent

interface ApplicationComponentOwner {
    fun applicationComponent(): ApplicationComponent
}