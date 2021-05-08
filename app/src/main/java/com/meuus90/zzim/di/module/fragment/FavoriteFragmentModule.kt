package com.meuus90.zzim.di.module.fragment

import com.meuus90.zzim.view.fragment.FavoriteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoriteFragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeFavoriteFragment(): FavoriteFragment
}