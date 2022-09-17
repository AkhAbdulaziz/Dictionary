package uz.gita.glossary.presentation.ui.viewmodels

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.glossary.data.model.GlossaryData

interface MainViewModel {
    val dictionaryCursorLiveData: LiveData<Cursor>
    fun getDictionaryCursor(queryStr: String) : Cursor
    fun updateFavourite(glossaryData: GlossaryData)
}