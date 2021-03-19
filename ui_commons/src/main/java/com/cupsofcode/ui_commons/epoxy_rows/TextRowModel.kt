package com.cupsofcode.ui_commons.epoxy_rows

import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.DimenRes
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.cupsofcode.ui_commons.R

@EpoxyModelClass
abstract class TextRowModel : EpoxyModel<TextView>() {
    @EpoxyAttribute
    var body: String? = null

    @EpoxyAttribute @DimenRes
    var textSizeRes: Int? = null

    @EpoxyAttribute var gravity: Int = Gravity.NO_GRAVITY

    override fun getDefaultLayout(): Int {
        return R.layout.row_text
    }

    override fun bind(view: TextView) {
        super.bind(view)
        body?.let {
            view.text = it
        }
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,
            view.resources.getDimension(textSizeRes ?: R.dimen.header_size))
        view.gravity = gravity

    }
}