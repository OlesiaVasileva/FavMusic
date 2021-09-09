package com.olesix.favmusic.di

import android.app.Application
import androidx.room.Room
import com.olesix.favmusic.local.AlbumDao
import com.olesix.favmusic.local.AlbumDatabase
import com.olesix.favmusic.network.RetrofitApi
import com.olesix.favmusic.repository.*
import com.olesix.favmusic.viewModel.AlbumViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val databaseModule = module {
    fun provideDataBase(application: Application): AlbumDatabase {
        return Room.databaseBuilder(application, AlbumDatabase::class.java, "Albums")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: AlbumDatabase): AlbumDao {
        return dataBase.albumDao()
    }
    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }

}

val mainViewModule = module {
    viewModel {
        AlbumViewModel(get())
    }
}

val networkModule = module {

    val baseUrl = "https://api.discogs.com/artists/45467/"

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun provideNetworkApi(retrofit: Retrofit): RetrofitApi =
        retrofit.create(RetrofitApi::class.java)

    factory { provideRetrofit() }
    single { provideNetworkApi(get()) }
}

val repositoryModule = module {
    factory<AlbumRepository> { AlbumRepositoryImpl(get(), get()) }
}

val dataSourceModule = module {
    factory { AlbumsPagingDataSourceFactory(get()) }
    factory<AlbumPagingDataSourceBuilder> { AlbumPagingDataSourceBuilderImpl(get()) }
}

