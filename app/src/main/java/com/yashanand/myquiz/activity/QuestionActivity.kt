package com.yashanand.myquiz.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.yashanand.myquiz.R
import com.yashanand.myquiz.models.SetQuestionAnswer
import kotlinx.android.synthetic.main.activity_question.*
import java.util.*

class QuestionActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var listName: ArrayList<SetQuestionAnswer?>
    private var currentPosition: Int = 1
    private var selectedOptionNumber: Int = 0
    private var correctAnswer: Int = 0
    var LangId: Int? = null
    var SetId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        supportActionBar?.hide()
        listName = ArrayList()
        setupActionBar()
        AllQuestions()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)

    }

    private fun AllQuestions() {
        // fetch all the collection from firebase
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("QUIZ").document("LANG$LangId")
                .collection("SET$SetId")

        docRef.get().addOnSuccessListener { result ->
            for (doc in result) {
                Log.d("questions", "AllQuestion : ${doc.data}")
                listName.add(SetQuestionAnswer(
                        doc.getString("QUESTION"),
                        doc.getString("Image"),
                        doc.getString("ANSWER"),
                        doc.getString("OptionA"),
                        doc.getString("OptionB"),
                        doc.getString("OptionC"),
                        doc.getString("OptionD"),
                ))
            }
            setQuestion()

        }.addOnFailureListener { exception ->
            Log.d("NoData", "Error getting documents:", exception)
        }

    }

    private fun setQuestion() {
        try {
            val questionSet = listName[currentPosition - 1]
            Log.d("mylist", "All Question = $questionSet")

            if (questionSet != null) {
                if (currentPosition == listName.size) {
                    btn_submit.text = "SUBMIT & FINISH"
                } else {
                    btn_submit.text = "SUBMIT"
                }

                if (questionSet != null) {
                    tv_question.text = questionSet.question
                    tv_option_one.text = questionSet.optionA
                    tv_option_two.text = questionSet.optionB
                    tv_option_three.text = questionSet.optionC
                    tv_option_four.text = questionSet.optionD

                    Log.d("come", "come->${questionSet.image}")
                    if (questionSet.image == null)
                        iv_image.visibility = View.GONE
                    else {
                        iv_image.visibility = View.VISIBLE
                        Picasso.get().load(questionSet.image).into(iv_image)
                    }
                }
                Q_no.text = "Question: $currentPosition/${listName.size} "
            } else {
                Toast.makeText(applicationContext, "No Question is available.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            //Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            btn_submit.visibility = View.INVISIBLE
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_option_one -> selcted_Option(tv_option_one, 1)
            R.id.tv_option_two -> selcted_Option(tv_option_two, 2)
            R.id.tv_option_three -> selcted_Option(tv_option_three, 3)
            R.id.tv_option_four -> selcted_Option(tv_option_four, 4)
            R.id.btn_submit -> {
                btn_submit.visibility = View.VISIBLE
                val question = listName.get(currentPosition - 1)
                if (question?.answer?.toInt() == selectedOptionNumber) {
                    correctAnswer++
                }
                currentPosition++
                when {
                    currentPosition <= listName.size -> setQuestion()
                    else -> {
                        startActivity(Intent(this, ResultActivity::class.java)
                                .putExtra("CorrectAnswer", correctAnswer).putExtra("totalQuestion", listName.size))
                        finish()
                    }
                }
                resetClickeditem()
            }
        }

    }

    private fun selcted_Option(tv: TextView, option_Number: Int) {
        selectedOptionNumber = option_Number

        when (option_Number) {
            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
                tv_option_two.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
                tv_option_three.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
                tv_option_four.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
            }
            2 -> {
                tv_option_one.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
                tv_option_two.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
                tv_option_three.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
                tv_option_four.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
            }
            3 -> {
                tv_option_one.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
                tv_option_two.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
                tv_option_three.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
                tv_option_four.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
            }
            4 -> {
                tv_option_one.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
                tv_option_two.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
                tv_option_three.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
                tv_option_four.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
            }

        }
        //tv.setTextColor(Color.parseColor("#363AA3"))
        //tv.setTypeface(tv.typeface, Typeface.BOLD_ITALIC)
        //tv.setTypeface(tv.typeface, Typeface.NORMAL)
    }

    private fun resetClickeditem() {
        when (selectedOptionNumber) {
            1 -> tv_option_one.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
            2 -> tv_option_two.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
            3 -> tv_option_three.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
            4 -> tv_option_four.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }

    }


    fun setupActionBar() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        LangId = intent.getIntExtra("LangCount", 0)
        SetId = intent.getIntExtra("Sets_Position", 0)
        supportActionBar?.title = "$LangId and $SetId"
    }

    override fun onSupportNavigateUp(): Boolean {  // goto back page on click "<-" on toolbar
        onBackPressed()
        return true
    }


}


