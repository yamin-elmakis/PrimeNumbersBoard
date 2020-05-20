package com.yamin.primeboard

import com.yamin.primeboard.repo.NumbersDataSourceFactory
import com.yamin.primeboard.repo.NumbersRepository
import com.yamin.primeboard.ui.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.util.concurrent.Executors

fun getDependencies() = Kodein.Module("dependencies") {

    bind<ViewModelFactory>() with provider {
        ViewModelFactory(
            repo = instance()
        )
    }

    bind<NumbersDataSourceFactory>() with singleton {
        NumbersDataSourceFactory()
    }

    bind<NumbersRepository>() with singleton {
        NumbersRepository(
            dataFactory = instance(),
            fetchExecutor = Executors.newSingleThreadExecutor()
        )
    }

}