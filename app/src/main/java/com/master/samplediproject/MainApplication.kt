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

package com.master.samplediproject

import com.master.samplediproject.ws.HeaderInterceptor
import com.master.basediproject.BaseDaggerApplication
import com.master.samplediproject.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Initialization of libraries.
 */
class MainApplication : BaseDaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .setContext(this)
            .setHeaderInterceptor(object : HeaderInterceptor() {
                override fun getHeaders(): Map<String, String> {
                    return HashMap<String, String>().apply {
                        put("TIMESTAMP", "${System.currentTimeMillis()}")
                    }
                }
            })
            .setPrefName("APPLICATION_NAME").build()
    }
}
