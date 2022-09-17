package uz.gita.glossary.domain.repository.impl

import android.database.Cursor
import uz.gita.glossary.data.database.AppDatabase
import uz.gita.glossary.data.model.GlossaryData
import uz.gita.glossary.data.pref.LocalStorage
import uz.gita.glossary.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val localStorage: LocalStorage,
    private val dataBase: AppDatabase
) : AppRepository {

    override fun getDictionaryCursor(query: String): Cursor = dataBase.getDictionaryCursor(query)

    override fun getFavouriteCursor(): Cursor = dataBase.getFavouriteCursor()

    override fun updateFavourite(data: GlossaryData) {
        dataBase.updateFavourite(data)
    }
}
