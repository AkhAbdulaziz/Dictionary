package uz.gita.glossary.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import uz.gita.glossary.R
import uz.gita.glossary.adapter.FavouritesCursorAdapter
import uz.gita.glossary.databinding.ScreenFavouritesBinding
import uz.gita.glossary.repository.AppRepository

class ScreenFavourites : Fragment(R.layout.screen_favourites) {
    private var _binding: ScreenFavouritesBinding? = null
    private val binding get() = _binding!!
    private var animation: LottieAnimationView? = null

    private val repository = AppRepository.getRepository()
    private lateinit var adapter: FavouritesCursorAdapter
    private var querySt = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        _binding = ScreenFavouritesBinding.bind(view)
        adapter = FavouritesCursorAdapter(repository.getFavouriteCursor(), querySt)
        binding.favouritesList.adapter = adapter
        binding.favouritesList.layoutManager = LinearLayoutManager(requireActivity())

        animation = binding.animationView

        adapter.setRemoveFavWordListener { data, pos ->
            repository.updateFavourite(data)
            adapter.cursor = repository.getFavouriteCursor()
            adapter.notifyDataSetChanged()
            checkEmpty()
        }
        adapter.setClickItemListener {
            val fm = ScreenInfo()
            val bundle = Bundle()
            bundle.putSerializable("data", it)
            fm.arguments = bundle
            startNextFragment(fm)
        }
       checkEmpty()
    }

    fun checkEmpty(){
        if (adapter.itemCount == 0) {
            animation?.playAnimation()
            animation?.visibility = View.VISIBLE
        } else {
            animation?.visibility = View.GONE
            animation?.pauseAnimation()
        }
    }

    private fun startNextFragment(fm: Fragment) {
        val activity = requireActivity() as MainActivity
        activity.startFragmentWithSaveStack(fm)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        animation = null
        _binding = null
    }
}