package com.jericho2code.app_finance_manager

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.jericho2code.app_finance_manager.application.di.components.ApplicationComponent
import com.jericho2code.app_finance_manager.application.di.components.DaggerApplicationComponent
import com.jericho2code.app_finance_manager.application.di.modules.ContextModule
import com.jericho2code.app_finance_manager.application.di.owners.ApplicationComponentOwner

class Application : Application(), ApplicationComponentOwner {
    private lateinit var component: ApplicationComponent
    override fun applicationComponent(): ApplicationComponent = component
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        component = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }
}