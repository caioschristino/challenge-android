package br.com.amedigital.lodjinha.base.view.custom

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class StrikeThruTextView: AppCompatTextView {
    constructor(context: Context) : super(context) {
        strikeThru()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        strikeThru()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        strikeThru()
    }

    private fun strikeThru() {
        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }
}