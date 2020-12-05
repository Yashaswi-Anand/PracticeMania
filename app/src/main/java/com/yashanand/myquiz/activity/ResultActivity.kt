package com.yashanand.myquiz.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yashanand.myquiz.R
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val correct_ans = intent.getIntExtra("CorrectAnswer", 0)
        val total = intent.getIntExtra("totalQuestion", 0)
        tvScore.text = "Your score is $correct_ans out of $total."

        val percent = (correct_ans * 100 / total)
        when {
            percent > 85 -> {
                tvstatus.text = " Excellent!!! "
            }
            percent in 61..84 -> {
                tvstatus.text = " Very Good "
            }
            percent in 30..60 -> {
                tvstatus.text = " Good "
            }
            else -> {
                tvstatus.text = " Not Bad. Need to more practice."
            }
        }

        btnFinish.setOnClickListener {
            startActivity(Intent(this, DashBoard::class.java))
            finish()
        }
    }
}
