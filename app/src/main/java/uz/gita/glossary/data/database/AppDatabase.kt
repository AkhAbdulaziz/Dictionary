package uz.gita.glossary.data.database

import android.content.ContentValues
import android.database.Cursor
import uz.gita.glossary.app.App
import uz.gita.glossary.data.model.GlossaryData
import javax.inject.Inject

class AppDatabase @Inject constructor() : DBHelper(App.instance, "Dictionary_new.db", 1) {

    /*companion object {
        private lateinit var instance: AppDatabase

        fun getAppDatabase(): AppDatabase {
            if (!Companion::instance.isInitialized) {
                instance = AppDatabase()
            }
            return instance
        }
    }*/

    fun updateFavourite(data: GlossaryData) {
        val cv = ContentValues()
        if (data.isFavourite == 0) {
            cv.put("isRemember", 1)
        } else {
            cv.put("isRemember", 0)
        }
        this.database().update("entries", cv, "entries.id = ${data.id}", null)
    }

    fun getDictionaryCursor(query: String): Cursor {
        return this.database().rawQuery(
            "SELECT * FROM entries WHERE entries.word LIKE '$query%' AND entries.id >= 12 AND length(entries.word) > 1",
            null
        )
    }

    fun getFavouriteCursor(): Cursor {
        return this.database().rawQuery(
            "SELECT * FROM entries WHERE entries.isRemember = 1", null
        )
    }
}
