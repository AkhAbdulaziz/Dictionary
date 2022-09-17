package uz.gita.glossary.presentation.ui.viewmodels.impl

import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.glossary.data.model.GlossaryData
import uz.gita.glossary.domain.repository.AppRepository
import uz.gita.glossary.presentation.ui.viewmodels.InfoViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModelImpl @Inject constructor(private val appRepository: AppRepository) :
    InfoViewModel, ViewModel() {

    override val dictionaryCursorLiveData = MutableLiveData<Cursor>()

    override fun getDictionaryCursor(queryStr: String) {
        dictionaryCursorLiveData.value = appRepository.getDictionaryCursor(queryStr)
    }

    override fun updateFavourite(glossaryData: GlossaryData) {
        appRepository.updateFavourite(glossaryData)
    }
}
