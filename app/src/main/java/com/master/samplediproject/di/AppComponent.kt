/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.master.samplediproject.di

import android.content.Context
import com.example.daggerandroidapp.ws.HeaderInterceptor
import com.example.daggerandroidapp.ws.WSModule
import com.master.basediproject.dihelpers.ViewModelModule
import com.master.basediproject.dihelpers.module.BaseAppModule
import com.master.samplediproject.MainApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Named
import javax.inject.Singleton

/**
 * Main component of the app, created and persisted in the Application class.
 *
 * Whenever a new module is created, it should be added to the list of modules.
 * [AndroidSupportInjectionModule] is the module from Dagger.Android that helps with the
 * generation and location of subcomponents.
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        BaseAppModule::class,
        AppModule::class,
        WSModule::class,
        ActivityBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<MainApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun setContext(context: Context): Builder

        @BindsInstance
        fun setHeaderInterceptor(interceptor: HeaderInterceptor?): Builder

        @BindsInstance
        fun setPrefName(@Named("PREFNAME") prefName: String): Builder

        /*@BindsInstance
        fun setHorsePower(@HorsePower horsePower: Int): Builder*/

        fun build(): AppComponent
    }
}
