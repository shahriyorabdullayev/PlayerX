package me.shakhriyor.mustplayer.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.shakhriyor.mustplayer.R
import me.shakhriyor.mustplayer.common.delegates.viewBinding
import me.shakhriyor.mustplayer.data.model.MusicModel
import me.shakhriyor.mustplayer.databinding.FragmentHomeBinding
import me.shakhriyor.mustplayer.ui.adapter.HomeMyPlayListAdapter
import me.shakhriyor.mustplayer.ui.adapter.HomeRecommendAdapter
import java.io.File
import kotlin.random.Random

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val TAG = "HomeFragment"
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    private val recommendAdapter by lazy { HomeRecommendAdapter() }
    private val myPlayListAdapter by lazy { HomeMyPlayListAdapter() }

    private var isPlay = false

    var musics: ArrayList<MusicModel>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musics = ArrayList()
        binding.rvRecommend.adapter = recommendAdapter
        binding.rvAllMusic.adapter = myPlayListAdapter

        if (!checkPermission()) {
            requestPermission()
        }

        with(binding) {
            btnDrawerMenu.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST
        )

        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        Log.d(TAG, "selection: $selection")

        val cursor =
            requireActivity().contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null)
        while (cursor!!.moveToNext()) {

            val songData = MusicModel(cursor.getString(1),
                cursor.getString(0),
                cursor.getString(2).toInt(),
                cursor.getString(3))

            if (File(songData.path.toString()).exists()) {
                musics!!.add(songData)
            }
        }

        val recommendMusic = ArrayList<MusicModel>()

        for (i in 0..musics!!.size) {
            recommendMusic.add(musics!![Random.nextInt(0, musics!!.size - 1)])
        }

        recommendAdapter.submitList(recommendMusic)

        myPlayListAdapter.submitList(musics!!)

        recommendAdapter.itemClickListener = {
            findNavController().navigate(R.id.action_homeFragment_to_playingNowFragment,
                bundleOf("music" to it.path))
        }

        binding.btnDrawerMenu.setOnClickListener {

        }

        binding.llPlay.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_playingNowFragment)
        }

        binding.ivPlayImage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_playingNowFragment)
        }

        binding.btnPauseHome.setOnClickListener {
            val image = if (isPlay) {
                R.drawable.ic_play_dark
            } else {
                R.drawable.ic_pause_dark
            }
            binding.btnPauseHome.setImageResource(image)
            isPlay = !isPlay
        }




    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {
            Toast.makeText(requireContext(),
                "READ PERMISSION IS REQUIRED,PLEASE ALLOW FROM SETTTINGS",
                Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 123)
        }
    }
}