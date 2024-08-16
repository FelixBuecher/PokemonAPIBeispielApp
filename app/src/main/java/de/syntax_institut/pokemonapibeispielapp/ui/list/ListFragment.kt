package de.syntax_institut.pokemonapibeispielapp.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import coil.load
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import de.syntax_institut.pokemonapibeispielapp.MainActivity
import de.syntax_institut.pokemonapibeispielapp.R
import de.syntax_institut.pokemonapibeispielapp.databinding.FragmentListBinding
import de.syntax_institut.pokemonapibeispielapp.ui.adapter.PokemonListAdapter
import de.syntax_institut.pokemonapibeispielapp.util.LoadingStatus
import de.syntax_institut.pokemonapibeispielapp.util.getImageLoader
import de.syntax_institut.pokemonapibeispielapp.util.listOfTypes

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllPokemon()
        val adapter = PokemonListAdapter(listOf(), viewModel)
        binding.rvPokemonList.adapter = adapter


        viewModel.pokemonList.observe(viewLifecycleOwner) { pokemonList ->
            adapter.updateDataset(pokemonList)
        }

        // Spinner
        val filterData = mutableListOf("All")
        listOfTypes.sorted().forEach { type ->
            filterData.add(type.capitalize())
        }
        binding.spinner.adapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            filterData
        )
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setType(filterData[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Loading status
        viewModel.loadingStatus.observe(viewLifecycleOwner) {
            if(it == LoadingStatus.LOADING) {
                (requireActivity() as MainActivity).bottomNavigationView.visibility = View.GONE
                (requireActivity() as MainActivity).actionBar.hide()
                context?.let {
                    binding.ivLoadingScreen.load(
                        R.drawable.simple_pokeball,
                        getImageLoader(it)
                    )
                }
                binding.ivLoadingScreen.visibility = View.VISIBLE
            } else if(it == LoadingStatus.DONE) {
                (requireActivity() as MainActivity).bottomNavigationView.visibility = View.VISIBLE
                (requireActivity() as MainActivity).actionBar.show()

                binding.ivLoadingScreen.visibility = View.GONE
            }
        }
    }
}