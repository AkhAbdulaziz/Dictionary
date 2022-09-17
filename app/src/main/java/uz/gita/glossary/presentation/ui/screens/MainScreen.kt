package uz.gita.glossary.presentation.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.glossary.R
import uz.gita.glossary.databinding.ScreenMainBinding
import uz.gita.glossary.presentation.ui.adapter.GlossaryCursorAdapter
import uz.gita.glossary.presentation.ui.viewmodels.MainViewModel
import uz.gita.glossary.presentation.ui.viewmodels.impl.MainViewModelImpl
import uz.gita.glossary.utils.scope

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private lateinit var adapter: GlossaryCursorAdapter
    private lateinit var handler: Handler
    private var animation: LottieAnimationView? = null
    private var querySt = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        /*    querySt = ""
            searchView.setQuery(null, false)*/

        adapter = GlossaryCursorAdapter(viewModel.getDictionaryCursor(querySt), querySt)
        dictionaryList.adapter = adapter
        dictionaryList.layoutManager = LinearLayoutManager(requireActivity())
        handler = Handler(Looper.getMainLooper())

        adapter.setClickFavouriteListener { it, pos ->
            viewModel.updateFavourite(it)
            adapter.cursor = viewModel.getDictionaryCursor(querySt)
            adapter.notifyItemChanged(pos)
        }

        adapter.setClickItemListener { glossaryData ->
            findNavController().navigate(
                MainScreenDirections.actionMainScreenToInfoScreen(
                    glossaryData
                )
            )
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                query?.let {
                    querySt = it.trim()
                    adapter.cursor = viewModel.getDictionaryCursor(querySt)
                    adapter.query = querySt
                    adapter.notifyDataSetChanged()
                    searchView.setQuery(querySt, false)
                }
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    newText?.let {
                        querySt = it.trim()
                        adapter.cursor = viewModel.getDictionaryCursor(querySt)
                        adapter.query = querySt
                        adapter.notifyDataSetChanged()
                        searchView.setQuery(querySt, false)

                        if (adapter.cursor.count == 0 && querySt.isNotEmpty()) {
                            animation?.visibility = View.VISIBLE
                            animation?.playAnimation()
                        } else {
                            animation?.visibility = View.GONE
                            animation?.pauseAnimation()
                        }
                    }
                }, 500)
                return true
            }
        })

        imageFavourite.setOnClickListener {
            findNavController().navigate(MainScreenDirections.actionMainScreenToFavouritesScreen())
        }
        animation = binding.animationView

        val closeButton = searchView.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            searchView.setQuery(null, false)
            searchView.clearFocus()
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }

        /*if (adapter.itemCount == 0) {
            binding.dictionaryList.visibility = View.GONE
            binding.text.visibility = View.VISIBLE
        } else {
            binding.dictionaryList.visibility = View.VISIBLE
            binding.text.visibility = View.GONE
        }*/
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animation = null
    }
}
