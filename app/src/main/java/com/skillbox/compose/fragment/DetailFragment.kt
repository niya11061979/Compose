package com.skillbox.compose.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.skillbox.compose.layout.DetailView
import com.skillbox.compose.state.LoadingItem
import com.skillbox.compose.viewmodel.DetailViewModel
import java.lang.reflect.Modifier

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent { LoadingItem() }
        viewModel.loadDetail(args.id)
        viewModel.detailLiveData.observe(viewLifecycleOwner) { detailWithEpisode ->
            setContent {
                DetailView(findNavController(), detailWithEpisode)
            }
        }
    }

}
