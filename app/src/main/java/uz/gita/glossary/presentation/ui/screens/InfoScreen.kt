package uz.gita.glossary.presentation.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.glossary.R
import uz.gita.glossary.databinding.ScreenInfoBinding
import uz.gita.glossary.presentation.ui.viewmodels.InfoViewModel
import uz.gita.glossary.presentation.ui.viewmodels.impl.InfoViewModelImpl
import uz.gita.glossary.utils.scope

@AndroidEntryPoint
class InfoScreen : Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val viewModel: InfoViewModel by viewModels<InfoViewModelImpl>()
    private val args: InfoScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        val data = args.glossaryData

        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        remember.setOnClickListener {
            val img = it as ImageView
            viewModel.updateFavourite(data)

            if (data.isFavourite == 0) {
                img.setImageResource(R.drawable.ic_favourite)
                data.isFavourite = 1
            } else {
                data.isFavourite = 0
                img.setImageResource(R.drawable.ic_unfavourite)
            }
        }

        if (data.isFavourite == 1)
            remember.setImageResource(R.drawable.ic_favourite)
        else remember.setImageResource(R.drawable.ic_unfavourite)

        word.text = data.word
        wordType.text = data.wordType
        definition.text = data.definition
    }
}
