package uz.gita.glossary.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import uz.gita.glossary.R
import uz.gita.glossary.adapter.GlossaryCursorAdapter
import uz.gita.glossary.data.model.GlossaryData
import uz.gita.glossary.databinding.ScreenInfoBinding
import uz.gita.glossary.repository.AppRepository

class ScreenInfo : Fragment(R.layout.screen_info) {
    private var _binding: ScreenInfoBinding? = null
    private val binding get() = _binding!!

    lateinit var data: GlossaryData
    private val repository = AppRepository.getRepository()
    private lateinit var adapter: GlossaryCursorAdapter
    private var querySt = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ScreenInfoBinding.bind(view)
        view.findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        adapter = GlossaryCursorAdapter(repository.getDictionaryCursor(querySt), querySt)
        arguments?.let {
            data = it.getSerializable("data") as GlossaryData
        }

        view.findViewById<ImageView>(R.id.remember).setOnClickListener {
            val img = it as ImageView
            if (data.isFavourite == 0) {
                img.setImageResource(R.drawable.ic_favourite)
                repository.updateFavourite(
                    GlossaryData(
                        data.id,
                        data.word,
                        data.wordType,
                        data.definition,
                        0
                    )
                )
                data.isFavourite = 1
            } else {
                repository.updateFavourite(
                    GlossaryData(
                        data.id,
                        data.word,
                        data.wordType,
                        data.definition,
                        1
                    )
                )
                data.isFavourite = 0
                img.setImageResource(R.drawable.ic_unfavourite)
            }
        }

        if (data.isFavourite == 1)
            binding.remember.setImageResource(R.drawable.ic_favourite)
        else binding.remember.setImageResource(R.drawable.ic_unfavourite)

        binding.word.text = data.word
        binding.wordType.text = data.wordType
        binding.definition.text = data.definition
    }
/*
    override fun onResume() {
        super.onResume()
        if (repository.isEmptySearchView) {
            requireActivity().supportFragmentManager.popBackStack()
            dictionaryList.visibility = View.VISIBLE
        }
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
