package com.example.viewmodellivedata.di.module

import com.example.viewmodellivedata.repository.MainRepository
import org.koin.dsl.module

val repomodule = module {
    single { MainRepository(get()) }
}