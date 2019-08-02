package com.master.samplediproject.ui.register

import androidx.lifecycle.ViewModel
import com.master.basediproject.dihelpers.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RegisterModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindMapFragmentViewModel(viewModel: RegisterViewModel): ViewModel
}