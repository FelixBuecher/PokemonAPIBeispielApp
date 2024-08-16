package de.syntax_institut.pokemonapibeispielapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import de.syntax_institut.pokemonapibeispielapp.databinding.FragmentFavoritesBinding
import de.syntax_institut.pokemonapibeispielapp.ui.adapter.PokemonListAdapter
import de.syntax_institut.pokemonapibeispielapp.ui.detail.DetailViewModel

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by activityViewModels()
    private val detailViewModel: DetailViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
        viewModel.favorites.observe(viewLifecycleOwner) {
            binding.rvFavorites.adapter = PokemonListAdapter(it, viewModel, detailViewModel)
        }
    }


}