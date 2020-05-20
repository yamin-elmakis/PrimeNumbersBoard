package com.yamin.primeboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yamin.primeboard.ui.main.MainFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MainActivity : AppCompatActivity(), KodeinAware {

    private val closest by closestKodein()
    override val kodein by Kodein.lazy { extend(closest) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}
