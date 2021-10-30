package uz.gita.glossary.app

import android.app.Application

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        // bu yerda this -> Application clasining contexti va u dastur davomida o'zgarmaydi.
    }
}