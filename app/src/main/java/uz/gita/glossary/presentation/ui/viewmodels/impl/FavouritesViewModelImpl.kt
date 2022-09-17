package uz.gita.glossary.presentation.ui.viewmodels.impl

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.glossary.data.model.GlossaryData
import uz.gita.glossary.domain.repository.AppRepository
import uz.gita.glossary.presentation.ui.viewmodels.FavouritesViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModelImpl @Inject constructor(private val appRepository: AppRepository) :
    FavouritesViewModel, ViewModel() {
    override val favouritesCursorLiveData = MutableLiveData<Cursor>()

    override fun getFavouriteCursor() : Cursor {
    return appRepository.getFavouriteCursor()
    }

    override fun updateFavourite(glossaryData: GlossaryData) {
        appRepository.updateFavourite(glossaryData)
    }
}