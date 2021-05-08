package com.meuus90.zzim.di.module.activity

import com.meuus90.zzim.di.module.fragment.FavoriteFragmentModule
import com.meuus90.zzim.di.module.fragment.HomeFragmentModule
import com.meuus90.zzim.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(
        modules = [
            HomeFragmentModule::class,
            FavoriteFragmentModule::class
        ]
    )
    internal abstract fun contributeMainActivity(): MainActivity
}