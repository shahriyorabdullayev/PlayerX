package me.shakhriyor.mustplayer.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import me.shakhriyor.mustplayer.R
import me.shakhriyor.mustplayer.common.delegates.viewBinding
import me.shakhriyor.mustplayer.data.model.MusicModel
import me.shakhriyor.mustplayer.databinding.FragmentPlayingNowBinding
import me.shakhriyor.mustplayer.ui.MyMediaPlayer
import me.shakhriyor.mustplayer.ui.viewpager.CardPagerAdapter
import me.shakhriyor.mustplayer.ui.viewpager.ShadowTransformer
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class PlayingNowFragment : Fragment(R.layout.fragment_playing_now) {

    private val binding by viewBinding { FragmentPlayingNowBinding.bind(it) }

    private var mCardAdapter: CardPagerAdapter? = null
    private var mCardShadowTransformer: ShadowTransformer? = null


    private lateinit var currentSong: MusicModel

    private lateinit var musicList: ArrayList<MusicModel>
    private val mediaPlayer = MyMediaPlayer.getInstance()!!

    private lateinit var listCard: ArrayList<MusicModel?>
    private lateinit var currentMusic: MusicModel

    private var isPlay = false
    private val x = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listCard = it.getParcelableArrayList<MusicModel>("musics")!!
            currentMusic = it.getParcelable("music")!!

            Log.d("@@@", "onCreate: $listCard")
            Log.d("@@@", "onCreate: $currentMusic")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCardAdapter = CardPagerAdapter()


        for (i in listCard) {
            mCardAdapter!!.addCardItem(i!!)
        }




        mCardShadowTransformer = ShadowTransformer(binding.vpPlayingNow, mCardAdapter!!)

        binding.vpPlayingNow.adapter = mCardAdapter
        binding.vpPlayingNow.setPageTransformer(false, mCardShadowTransformer)
        binding.vpPlayingNow.offscreenPageLimit = 2
        mCardShadowTransformer!!.enableScaling(true)


        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }


    }

    private fun playMusic() {
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(currentSong.path)
            mediaPlayer.prepare()
            mediaPlayer.start()
            binding.seekBar.progress = 0
            binding.seekBar.max = currentSong.duration!!
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun playNextMusic() {
        if (MyMediaPlayer.currentIndex == musicList.size - 1) return
        MyMediaPlayer.currentIndex += 1
        mediaPlayer.reset()
        setResourcesWithMusic()
    }

    private fun setResourcesWithMusic() {
        currentSong = musicList[MyMediaPlayer.currentIndex]


        binding.btnNextMusic.setOnClickListener {
            binding.vpPlayingNow.currentItem++
            playNextMusic()
        }

        binding.btnPreviousMusic.setOnClickListener {
            binding.vpPlayingNow.currentItem--
            playPreviousMusic()
        }

        binding.btnPauseMusic.setOnClickListener {
            val pauseMusic = if (isPlay) {
                R.drawable.ic_play_dark
            } else {
                R.drawable.ic_pause_dark
            }
            binding.btnPauseMusic.setImageResource(pauseMusic)
            isPlay = !isPlay
            pausePlay()
        }

        playMusic()
    }

    private fun playPreviousMusic() {
        if (MyMediaPlayer.currentIndex == 0) return
        MyMediaPlayer.currentIndex -= 1
        mediaPlayer.reset()
        setResourcesWithMusic()
    }

    private fun pausePlay() {
        if (mediaPlayer.isPlaying) mediaPlayer.pause() else mediaPlayer.start()
    }

    fun convertToMMSS(duration: String): String? {
        val millis = duration.toLong()
        return String.format("%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1))
    }
}