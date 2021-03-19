package com.cupsofcode.ui_commons.epoxy_rows

import android.util.TypedValue
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.cupsofcode.ui_commons.R
import kotlinx.android.synthetic.main.row_header.view.*

@EpoxyModelClass
abstract class FeedHeaderRowModel : EpoxyModel<ConstraintLayout>() {
    @EpoxyAttribute
    var body: String? = null

    @EpoxyAttribute
    @DimenRes
    var textSizeRes: Int? = null


    @EpoxyAttribute
    @DrawableRes
    var imgRes: Int? = null

    override fun getDefaultLayout(): Int {
        return R.layout.row_header
    }

    override fun bind(view: ConstraintLayout) {
        super.bind(view)
        body?.let {
            view.header.text = it

            view.header.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                view.resources.getDimension(textSizeRes ?: R.dimen.header_size)
            )
        }

        imgRes?.let {
            view.img.setImageResource(it)
        }


    }
}