package dev.pseudo.userapi.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.pseudo.userapi.databinding.FragmentMainBinding
import dev.pseudo.userapi.presentation.adapter.UserAdapter
import dev.pseudo.userapi.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter { user ->
            val action = MainFragmentDirections.actionMainFragmentToInfoFragment(user)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.loadUsers()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is MainViewModel.UiState.Loading -> {
                            binding.swipeRefreshLayout.isRefreshing = true
                        }

                        is MainViewModel.UiState.Success -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            adapter.submitList(state.users)
                        }

                        is MainViewModel.UiState.Error -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            Snackbar.make(requireView(), state.message, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadUsers(forceRefresh = true)
        }
    }
}