package com.techjd.chatapp

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

open class Inital : View {

    private var text: String = "A"
    private var coordinate: Float = 550F
    private var x_coordinate: Float = coordinate
    private var y_coordinate: Float = coordinate
    private var radius: Float = 80F
//    private var defaultWidth = resources.getDimensionPixelSize()
    private var size = 0
    private var colors = arrayOf(
        Color.YELLOW,
        Color.GREEN,
        Color.CYAN,
        Color.MAGENTA,
        Color.GREEN
    )

    private var background_color = colors[1]

    constructor(context: Context) : super(context)
    constructor (context: Context, attibutes: AttributeSet) : super(context, attibutes) {
        parseAttributes(
            context.obtainStyledAttributes(
                attibutes,
                R.styleable.Inital
            )
        )
//        .apply {
//            text = getString(R.styleable.Inital_text).toString()
//        }
    }

    private fun parseAttributes(attr: TypedArray) {
        text = attr.getString(R.styleable.Inital_text).toString()
        coordinate = attr.getFloat(R.styleable.Inital_coordinate, coordinate)
        radius = attr.getFloat(R.styleable.Inital_radius, radius)
        setCoordinate(coordinate)
        setRadius(radius)
        setText(text)
    }

    fun setRadius(r: Float) {
        radius = r
        invalidate()
    }

    fun setText(t: String) {
        text = t
        invalidate()
    }

    fun setCoordinate(cD: Float) {
        coordinate = cD
        x_coordinate = coordinate
        y_coordinate = coordinate
        invalidate()
    }

    fun display(nameInitial: String) {
        text = nameInitial
        invalidate()
    }

    private val paint = Paint().apply {
        color = colors[Random.nextInt(0, 5)]
//        color = Color.GREEN
    }

    private val paintText = TextPaint().apply {
        color = Color.BLACK
        textSize = 80F
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val w = width
        val h = height

        val pl = paddingLeft
        val pr = paddingRight
        val pt = paddingTop
        val pb = paddingBottom

        val usableWidth = w - (pl + pr)
        val usableHeight = h - (pt + pb)

        val radius = Math.min(usableWidth, usableHeight) / 2
        val cx = pl + usableWidth / 2
        val cy = pt + usableHeight / 2

        canvas!!.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), paint)
        canvas.drawText("J",usableWidth.toFloat()/2, usableHeight.toFloat()/2, paintText)

//        canvas!!.drawCircle(x_coordinate, y_coordinate, radius, paint)
//        canvas.drawText(text, x_coordinate, (y_coordinate + 34), paintText)
    }

}