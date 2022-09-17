package uz.gita.glossary.presentation.ui.viewmodels

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.glossary.data.model.GlossaryData

interface InfoViewModel {
    val dictionaryCursorLiveData: LiveData<Cursor>
    fun getDictionaryCursor(queryStr: String)
    fun updateFavourite(glossaryData: GlossaryData)
}