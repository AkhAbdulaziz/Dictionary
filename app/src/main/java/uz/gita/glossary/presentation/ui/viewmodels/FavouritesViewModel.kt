package uz.gita.glossary.presentation.ui.viewmodels

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.glossary.data.model.GlossaryData

interface FavouritesViewModel {
    val favouritesCursorLiveData: LiveData<Cursor>
    fun getFavouriteCursor() : Cursor
    fun updateFavourite(glossaryData: GlossaryData)
}