package com.example.user.kotlinmetry

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Obstacle(rectHeight: Int, private val color: Int, startX: Int, startY: Int, playerGap: Int) : GameObject {

    val rectangle: Rect
    private val rectangle2: Rect


    fun incrementY(y: Float) {
        rectangle.top += y.toInt()
        rectangle.bottom += y.toInt()
        rectangle2.top += y.toInt()
        rectangle2.bottom += y.toInt()
    }

    init {
        rectangle = Rect(0, startY, startX, startY + rectHeight)
        rectangle2 = Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight)
    }

    fun playerCollide(player: RectPlayer): Boolean {
        return Rect.intersects(rectangle, player.rectangle) || Rect.intersects(rectangle2, player.rectangle)
    }

     override fun update() {

    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = color
        canvas.drawRect(rectangle, paint)
        canvas.drawRect(rectangle2, paint)
    }
}
