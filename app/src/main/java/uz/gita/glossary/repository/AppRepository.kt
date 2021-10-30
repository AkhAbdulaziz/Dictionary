package uz.gita.glossary.repository

import android.database.Cursor
import uz.gita.glossary.data.database.AppDatabase
import uz.gita.glossary.data.model.GlossaryData

class AppRepository private constructor() {
    private val database = AppDatabase.getAppDatabase()

    init {

    }

    companion object {
        private lateinit var instance: AppRepository

        fun getRepository(): AppRepository {
            if (!::instance.isInitialized) {
                instance = AppRepository()
            }
            return instance
        }
    }

    fun getDictionaryCursor(query: String): Cursor = database.getDictionaryCursor(query)

    fun getFavouriteCursor(): Cursor = database.getFavouriteCursor()

    fun updateFavourite(data: GlossaryData) {
        database.updateFavourite(data)
    }
}
