package me.shakhriyor.mustplayer.ui.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import me.shakhriyor.mustplayer.R
import me.shakhriyor.mustplayer.ui.viewpager.CardAdapter.Companion.MAX_ELEVATION_FACTOR

class CardPagerAdapter : PagerAdapter(), CardAdapter {

    private var mViews: ArrayList<CardView?>? = null
    private var mData: ArrayList<CardItem?>? = null
    private var mBaseElevation = 0f

    init {
        mData = ArrayList()
        mViews = ArrayList()
    }

    fun addCardItem(item: CardItem) {
        mViews?.add(null)
        mData?.add(item)
    }

    fun getBaseElevetion(): Float {
        return mBaseElevation
    }

    override fun getCardViewAt(position: Int): CardView? {
        return mViews?.get(position)
    }

    override fun getBaseElevation(): Float {
        return mBaseElevation
    }

    override fun getCount(): Int {
        return mData!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context).inflate(R.layout.adapter, container, false)
        container.addView(view)
        bind(mData?.get(position)!!, view)
        val cardView = view.findViewById<CardView>(R.id.cardView)

        if (mBaseElevation.toInt() == 0) {
            mBaseElevation = cardView.cardElevation
        }

        cardView.maxCardElevation = mBaseElevation * MAX_ELEVATION_FACTOR
        mViews?.set(position, cardView)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        mViews?.set(position, null)
    }

    private fun bind(item: CardItem, view: View) {
//        val titleTextView = view.findViewById<View>(R.id.titleTextView) as TextView
//        val contentTextView = view.findViewById<View>(R.id.contentTextView) as TextView
//        titleTextView.text = item.title
//        contentTextView.text = item.text
    }
}