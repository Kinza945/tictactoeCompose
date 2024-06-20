package com.kynzai.tictactoe.runtime

import android.app.Application
import android.content.Context
import com.kynzai.tictactoe.BuildConfig
import com.kynzai.tictactoe.model.app
import com.kynzai.tictactoe.model.appContext
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TicTacToeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        app = this
        appContext = base
    }
}