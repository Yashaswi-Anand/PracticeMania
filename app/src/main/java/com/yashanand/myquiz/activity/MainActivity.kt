package com.yashanand.myquiz.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yashanand.myquiz.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val BounceText = findViewById<TextView>(R.id.bouncText)
        val animationBonce = AnimationUtils.loadAnimation(this,
                R.anim.bounce)
        BounceText.startAnimation(animationBonce)

        Handler().postDelayed({
            startActivity(Intent(this, DashBoard::class.java))
            finish()
        }, 3000)
    }
}