package uz.gita.glossary.data.database

import android.content.ContentValues
import android.database.Cursor
import uz.gita.glossary.app.App
import uz.gita.glossary.data.model.GlossaryData

class AppDatabase private constructor() : DBHelper(App.instance, "Dictionary_new.db", 1) {

    companion object {
        private lateinit var instance: AppDatabase

        fun getAppDatabase(): AppDatabase {
            if (!Companion::instance.isInitialized) {
                instance = AppDatabase()
            }
            return instance
        }
    }

    fun updateFavourite(data: GlossaryData) {
        val cv = ContentValues()
        if (data.isFavourite == 0) {
            cv.put("isRemember", 1)
        } else {
            cv.put("isRemember", 0)
        }
        instance.database().update("entries", cv, "entries.id = ${data.id}", null)
    }

    fun getDictionaryCursor(query: String): Cursor {
        return instance.database().rawQuery(
            "SELECT * FROM entries WHERE entries.word LIKE '$query%' AND entries.id >= 12 AND length(entries.word) > 1",
            null
        )
    }

    fun getFavouriteCursor(): Cursor {
        return instance.database().rawQuery(
            "SELECT * FROM entries WHERE entries.isRemember = 1", null
        )
    }
}
