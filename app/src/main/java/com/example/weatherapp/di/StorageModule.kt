package com.example.weatherapp.diimport com.example.weatherapp.storage.SharedPreferencesManagerimport org.koin.dsl.moduleval storageModule = module {    single { SharedPreferencesManager(context = get(), gson = get()) }}