package uz.gita.glossary.domain.repository

import android.database.Cursor
import uz.gita.glossary.data.model.GlossaryData

interface AppRepository {
    fun getDictionaryCursor(query: String): Cursor

    fun getFavouriteCursor(): Cursor

    fun updateFavourite(data: GlossaryData)
}
