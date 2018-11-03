package com.example.user.kotlinmetry

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GamePanel(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    private val thread: MainThread
    private val r = Rect()

    private val player: RectPlayer
    private var playerPoint: Point? = null
    private var obstacleManager: ObstacleManager? = null

    private var movingPlayer = false

    private var gameOver = false
    private var gameOverTime: Long = 0



    init {
        holder.addCallback(this)

        thread = MainThread(holder, this)

        player = RectPlayer(Rect(100, 100, 200, 200), Color.rgb(255, 0, 0))
        playerPoint = Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4)
        player.update(playerPoint!!)

        obstacleManager = ObstacleManager(200, 350, 75, Color.BLACK)

        isFocusable = true

    }

    private fun reset() {
        playerPoint = Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4)
        player.update(playerPoint!!)
        obstacleManager = ObstacleManager(200, 350, 75, Color.BLACK)
        movingPlayer = false
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        Constants.INIT_TIME = System.currentTimeMillis()
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            retry = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!gameOver && player.rectangle.contains(event.x.toInt(), event.y.toInt()))
                    movingPlayer = true
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset()
                    gameOver = false
                }
            }
            MotionEvent.ACTION_MOVE -> if (!gameOver && movingPlayer)
                playerPoint!!.set(event.x.toInt(), event.y.toInt())
            MotionEvent.ACTION_UP -> movingPlayer = false
        }
        //return super.onTouchEvent(event);
        return true

    }

    fun update() {
        if(!gameOver) {

            player.update(playerPoint!!)
            obstacleManager!!.update()

            if (obstacleManager!!.playerCollide(player)) {
                gameOver = true
                gameOverTime = System.currentTimeMillis()
            }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.drawColor(Color.WHITE)

        player.draw(canvas)
        obstacleManager!!.draw(canvas)

        if (gameOver) {
            val paint = Paint()
            paint.textSize = 100f
            paint.color = Color.BLUE
            drawCenterText(canvas, paint, "GameOver")
        }
    }

    private fun drawCenterText(canvas: Canvas, paint: Paint, text: String) {
        paint.textAlign = Paint.Align.LEFT
        canvas.getClipBounds(r)
        val cHeight = r.height()
        val cWidth = r.width()
        paint.getTextBounds(text, 0, text.length, r)
        val x = cWidth / 2f - r.width() / 2f - r.left.toFloat()
        val y = cHeight / 2f + r.height() / 2f - r.bottom
        canvas.drawText(text, x, y, paint)
    }
}