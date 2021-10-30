package uz.gita.glossary.data.pref

import android.content.Context
import uz.gita.glossary.app.App


class SharedPref private constructor() {

    companion object {
        private lateinit var instance: SharedPref

        fun getSharedPref(): SharedPref {
            if (!::instance.isInitialized) {
                instance = SharedPref()
            }
            return instance
        }
    }

    private val pref = App.instance.getSharedPreferences("Dictionary", Context.MODE_PRIVATE)

    var isFirstRunning
        set(value) = pref.edit().putBoolean("firstRunning", value).apply()
        get() = pref.getBoolean("firstRunning", true)
}
