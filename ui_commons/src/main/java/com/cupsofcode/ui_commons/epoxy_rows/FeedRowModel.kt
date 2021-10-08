package com.cupsofcode.ui_commons.epoxy_rows

import android.view.View
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.bumptech.glide.Glide
import com.cupsofcode.ui_commons.R
import kotlinx.android.synthetic.main.row_feed.view.*

@EpoxyModelClass
abstract class FeedRowModel : EpoxyModel<CardView>() {
    @EpoxyAttribute
    var name: String? = null

    @EpoxyAttribute
    var logo: String? = null

    @EpoxyAttribute
    var description: String? = null

    @EpoxyAttribute
    var status: String? = null

    @EpoxyAttribute
    var onClickListener: View.OnClickListener? = null

    @EpoxyAttribute
    var distance: String? = null

    @EpoxyAttribute
    var likedStatus: Boolean = false

    @EpoxyAttribute
    var likeClickListener: View.OnClickListener? = null


    override fun getDefaultLayout(): Int {
        return R.layout.row_feed
    }

    override fun bind(view: CardView) {
        super.bind(view)
        name?.let {
            view.name.text = it
        }
        description?.let {
            view.type.text = it
        }

        status?.let {
            view.time.text = it
        }

        distance?.let {
            view.distance.text = it
        }

        view.liked.setImageResource(
            if (likedStatus) {
                R.drawable.ic_outline_thumb_up_24
            } else {
                R.drawable.ic_baseline_thumb_up_24
            }
        )
        view.liked.setOnClickListener(likeClickListener)

        Glide.with(view)
            .load(logo)
            .circleCrop()
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(view.img)

        view.setOnClickListener(onClickListener)
    }
}