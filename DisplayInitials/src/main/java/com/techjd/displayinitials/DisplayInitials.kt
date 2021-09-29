package com.techjd.displayinitials

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View


class DisplayInitials(context: Context, attibutes: AttributeSet) : View(context, attibutes) {

    private val paint = Paint().apply {
        color = Color.GREEN
    }

    private val paintText = TextPaint().apply {
        color = Color.BLACK
        textSize = 100F
        textAlign = Paint.Align.CENTER
    }

    fun display(name: String) {

    }

    val typedArray = context.obtainStyledAttributes(
        attibutes,
        com.techjd.displayinitials.R.styleable.DisplayInitials
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)



        canvas!!.drawCircle(300F, 300F, 100f, paint)
        canvas.drawText("S", 300F, 334F, paintText)
    }
}