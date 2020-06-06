package com.bnvs.metaler.module

import com.bnvs.metaler.data.categories.source.local.CategoriesLocalDataSource
import com.bnvs.metaler.data.categories.source.local.CategoriesLocalDataSourceImpl
import com.bnvs.metaler.data.categories.source.remote.CategoriesRemoteDataSource
import com.bnvs.metaler.data.categories.source.remote.CategoriesRemoteDataSourceImpl
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val categoriesModule = module {
    single<CategoriesRepository> { CategoriesRepositoryImpl(get(), get()) }
    single<CategoriesLocalDataSource> { CategoriesLocalDataSourceImpl(androidContext()) }
    single<CategoriesRemoteDataSource> { CategoriesRemoteDataSourceImpl(get()) }
}