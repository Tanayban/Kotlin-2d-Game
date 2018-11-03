package com.example.user.kotlinmetry

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect

class RectPlayer( var rectangle: Rect, private var color: Int) : GameObject {



    init{
        this.rectangle = rectangle
        this.color = color


    }


    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.setColor(color)
        canvas.drawRect(rectangle, paint)
    }

    override fun update() {
    }

    fun update(point: Point) {
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2)
    }
}