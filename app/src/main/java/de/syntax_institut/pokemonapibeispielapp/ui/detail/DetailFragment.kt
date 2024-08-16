package de.syntax_institut.pokemonapibeispielapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import coil.load
import de.syntax_institut.pokemonapibeispielapp.databinding.FragmentDetailBinding
import de.syntax_institut.pokemonapibeispielapp.util.getImageLoader
import de.syntax_institut.pokemonapibeispielapp.util.getTypeColor
import de.syntax_institut.pokemonapibeispielapp.util.toCM
import de.syntax_institut.pokemonapibeispielapp.util.toKG

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
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
        viewModel.currentSelectedPokemon.observe(viewLifecycleOwner) { pokemon ->
            context?.let {
                binding.ivPokemonDetailImage.load(
                    pokemon.sprites.image,
                    getImageLoader(it)
                )
            }
            binding.tvPokemonName.text = pokemon.name.capitalize()
            binding.tvHeight.text = pokemon.height.toCM()
            binding.tvWeight.text = pokemon.weight.toKG()
            binding.tvTypeOne.text = pokemon.types[0].type.name.capitalize()
            if(pokemon.types.size > 1) binding.tvTypeTwo.text = pokemon.types[1].type.name.capitalize()
            binding.root.setBackgroundColor(getTypeColor(pokemon.types[0].type.name))
        }
    }

}