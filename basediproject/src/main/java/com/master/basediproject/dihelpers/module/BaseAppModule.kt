package com.master.basediproject.dihelpers.module

import android.content.Context
import com.google.gson.Gson
import com.master.basediproject.utils.FilePaths
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseAppModule {
    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideFilePaths(context: Context): FilePaths = FilePaths(context)
}