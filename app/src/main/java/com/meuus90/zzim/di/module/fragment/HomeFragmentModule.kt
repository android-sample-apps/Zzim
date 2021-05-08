package com.meuus90.zzim.di.module.fragment

import com.meuus90.zzim.view.fragment.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment
}