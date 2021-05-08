package com.meuus90.zzim.di.component

import android.app.Application
import com.meuus90.zzim.Zzim
import com.meuus90.zzim.di.module.AppModule
import com.meuus90.zzim.di.module.activity.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class
    ]
)

interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

    fun inject(app: Zzim)
}