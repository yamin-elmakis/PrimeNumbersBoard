package com.yamin.primeboard

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class App : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(getDependencies())
    }
}