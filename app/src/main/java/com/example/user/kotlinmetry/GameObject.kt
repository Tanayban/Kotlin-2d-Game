package com.example.user.kotlinmetry

import android.graphics.Canvas

interface GameObject {
    fun draw(canvas: Canvas)
    fun update()
}