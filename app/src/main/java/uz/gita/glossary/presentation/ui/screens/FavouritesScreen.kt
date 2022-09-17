package uz.gita.glossary.presentation.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.glossary.R
import uz.gita.glossary.databinding.ScreenFavouritesBinding
import uz.gita.glossary.presentation.ui.adapter.FavouritesCursorAdapter
import uz.gita.glossary.presentation.ui.viewmodels.FavouritesViewModel
import uz.gita.glossary.presentation.ui.viewmodels.impl.FavouritesViewModelImpl
import uz.gita.glossary.utils.scope

@AndroidEntryPoint
class FavouritesScreen : Fragment(R.layout.screen_favourites) {
    private val binding by viewBinding(ScreenFavouritesBinding::bind)
    private val viewModel: FavouritesViewModel by viewModels<FavouritesViewModelImpl>()
    private var animation: LottieAnimationView? = null
    private lateinit var adapter: FavouritesCursorAdapter
    private var querySt = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavouritesCursorAdapter(viewModel.getFavouriteCursor(), querySt)
        favouritesList.adapter = adapter
        favouritesList.layoutManager = LinearLayoutManager(requireContext())
        animation = binding.animationView
        adapter.setRemoveFavWordListener { data, pos ->
            viewModel.updateFavourite(data)
            adapter.cursor = viewModel.getFavouriteCursor()
            adapter.notifyDataSetChanged()
            checkEmpty()
        }
        adapter.setClickItemListener { glossaryData ->
            findNavController().navigate(
                FavouritesScreenDirections.actionFavouritesScreenToInfoScreen(
                    glossaryData
                )
            )
        }
        checkEmpty()
        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun checkEmpty() {
        if (adapter.itemCount == 0) {
            animation?.playAnimation()
            animation?.visibility = View.VISIBLE
        } else {
            animation?.visibility = View.GONE
            animation?.pauseAnimation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animation = null
    }
}