package uz.gita.glossary.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import uz.gita.glossary.R
import uz.gita.glossary.adapter.GlossaryCursorAdapter
import uz.gita.glossary.databinding.ScreenGlossaryBinding
import uz.gita.glossary.repository.AppRepository

class ScreenGlossaries : Fragment(R.layout.screen_glossary) {
    private var _binding: ScreenGlossaryBinding? = null
    private val binding get() = _binding!!
    private val repository = AppRepository.getRepository()
    private lateinit var adapter: GlossaryCursorAdapter
    private lateinit var handler: Handler
    private var animation:LottieAnimationView? = null
    private var querySt = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ScreenGlossaryBinding.bind(view)
        /*    querySt = ""
            searchView.setQuery(null, false)*/
        binding.imageFavourite.setOnClickListener {
            startNextFragment(ScreenFavourites())
        }
        animation = binding.animationView
        adapter = GlossaryCursorAdapter(repository.getDictionaryCursor(querySt), querySt)
        binding.dictionaryList.adapter = adapter
        binding.dictionaryList.layoutManager = LinearLayoutManager(requireActivity())
        handler = Handler(Looper.getMainLooper())

        adapter.setClickFavouriteListener { it, pos ->
            repository.updateFavourite(it)
            adapter.cursor = repository.getDictionaryCursor(querySt)
            adapter.notifyItemChanged(pos)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                query?.let {
                    querySt = it.trim()
                    adapter.cursor = repository.getDictionaryCursor(querySt)
                    adapter.query = querySt
                    adapter.notifyDataSetChanged()
                    binding.searchView.setQuery(querySt, false)
                }
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    newText?.let {
                        querySt = it.trim()
                        adapter.cursor = repository.getDictionaryCursor(querySt)
                        adapter.query = querySt
                        adapter.notifyDataSetChanged()
                        binding.searchView.setQuery(querySt, false)
                        if(adapter.cursor.count == 0 && querySt.isNotEmpty()){
                            animation?.visibility = View.VISIBLE
                            animation?.playAnimation()
                        }else{
                            animation?.visibility = View.GONE
                            animation?.pauseAnimation()
                        }
                    }
                }, 500)
                return true
            }
        })

        val closeButton = binding.searchView.findViewById(R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener {
            binding.searchView.setQuery(null, false)
            binding.searchView.clearFocus()
            requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }

        adapter.setClickItemListener {
            val fm = ScreenInfo()
            val bundle = Bundle()
            bundle.putSerializable("data", it)
            fm.arguments = bundle
            startNextFragment(fm)
        }

        /*if (adapter.itemCount == 0) {
            binding.dictionaryList.visibility = View.GONE
            binding.text.visibility = View.VISIBLE
        } else {
            binding.dictionaryList.visibility = View.VISIBLE
            binding.text.visibility = View.GONE
        }*/
    }

    private fun startNextFragment(fm: Fragment) {
        val activity = requireActivity() as MainActivity
        activity.startFragmentWithSaveStack(fm)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        animation = null
    }
}
