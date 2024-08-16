package de.syntax_institut.pokemonapibeispielapp.ui.adapter

import android.annotation.SuppressLint
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import de.syntax_institut.pokemonapibeispielapp.R
import de.syntax_institut.pokemonapibeispielapp.data.model.Pokemon
import de.syntax_institut.pokemonapibeispielapp.databinding.PokemonListItemBinding
import de.syntax_institut.pokemonapibeispielapp.ui.detail.DetailViewModel
import de.syntax_institut.pokemonapibeispielapp.ui.favorites.FavoritesViewModel
import de.syntax_institut.pokemonapibeispielapp.ui.list.ListViewModel
import de.syntax_institut.pokemonapibeispielapp.util.getTypeColor
import okhttp3.internal.immutableListOf
import okhttp3.internal.notifyAll

class PokemonListAdapter(
    private var dataset: List<Pokemon>,
    private val viewModel: AndroidViewModel,
    private val detailViewModel: DetailViewModel
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {
    inner class PokemonViewHolder(val binding: PokemonListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonViewHolder {
        val binding = PokemonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataset(dataset: List<Pokemon>) {
        this.dataset = dataset
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(
        holder: PokemonViewHolder,
        position: Int
    ) {
        val pokemon = dataset[position]

        holder.binding.tvPokemonItemName.text = pokemon.name.capitalize()
        holder.binding.tvPokemonItemID.text = pokemon.id.toString()
        holder.binding.tvPokemonItemTypeOne.text = pokemon.types[0].type.name.capitalize()
        holder.binding.tvPokemonItemTypeTwo.text = if(pokemon.types.size > 1) pokemon.types[1]
                .type.name.capitalize() else ""
        holder.binding.ivPokemonItemImage.load(pokemon.sprites.image)

        holder.binding.cvPokemonListItem.setCardBackgroundColor(getTypeColor(pokemon.types[0].type.name))

        val favFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(1.0f) })
        val nonFavFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0.0f) })

        if(pokemon.favorite) {
            holder.binding.ivFavorite.colorFilter = favFilter
        } else {
            holder.binding.ivFavorite.colorFilter = nonFavFilter
        }

        holder.binding.cvPokemonListItem.setOnClickListener {
            detailViewModel.setSelectedPokemon(pokemon)
            holder.itemView.findNavController().navigate(R.id.detailFragment)
        }

        holder.binding.ivFavorite.setOnClickListener {
            pokemon.favorite = !pokemon.favorite
            if(viewModel is ListViewModel) {
                viewModel.updatePokemon(pokemon)
            } else if (viewModel is FavoritesViewModel) {
                viewModel.updatePokemon(pokemon)
            }
        }

    }
}