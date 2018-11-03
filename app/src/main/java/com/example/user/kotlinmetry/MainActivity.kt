package com.example.user.kotlinmetry

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        Constants.SCREEN_WIDTH = dm.widthPixels
        Constants.SCREEN_HEIGHT = dm.heightPixels


        setContentView(GamePanel(this))
    }
}
