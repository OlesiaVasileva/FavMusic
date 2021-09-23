package com.olesix.favmusic.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.olesix.favmusic.R
import com.olesix.favmusic.adapter.AlbumAdapter
import com.olesix.favmusic.databinding.FragmentAlbumsBinding
import com.olesix.favmusic.repository.State
import com.olesix.favmusic.viewModel.AlbumViewModel
import dmax.dialog.SpotsDialog
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val TAG = "FavMusic"

class FragmentAlbums : Fragment(), KoinComponent {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlbumViewModel by inject()

    lateinit var adapter: AlbumAdapter
    lateinit var recyclerAlbumList: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        recyclerAlbumList = binding.recyclerAlbumsList
        dialog = SpotsDialog.Builder().setCancelable(true).setContext(context).build()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(context)
        recyclerAlbumList.layoutManager = layoutManager
        recyclerAlbumList.setHasFixedSize(true)
        initAdapter()
        initState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        dialog.show()
        adapter = AlbumAdapter()
        recyclerAlbumList.adapter = adapter
        viewModel.albumList.observe(viewLifecycleOwner, { updatedList ->
            adapter.submitList(updatedList)
            dialog.dismiss()
            adapter.notifyDataSetChanged()
        })

    }

    private fun initState() {
        viewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                State.LOADING -> {
                    dialog.show()
                    Log.d(TAG, "State.LOADING")
                }
                State.ERROR -> {
                    Toast.makeText(context, getString(R.string.no_connect), Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                    Log.d(TAG, "State.ERROR")
                }
                State.DONE -> {
                    dialog.dismiss()
                    Log.d(TAG, " State.DONE")
                }
            }
        })
    }
}