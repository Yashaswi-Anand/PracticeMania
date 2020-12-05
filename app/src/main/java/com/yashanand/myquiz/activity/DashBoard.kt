package com.yashanand.myquiz.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.yashanand.myquiz.R
import com.yashanand.myquiz.adapters.ProgramTypeAdapter
import com.yashanand.myquiz.models.LangaugesList
import kotlinx.android.synthetic.main.activity_dash_board.*

class DashBoard : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var gridLayoutManger: GridLayoutManager? = null
    private var listOfLangs: ArrayList<LangaugesList>? = null
    private var ProgTypeAdapter: ProgramTypeAdapter? = null
    lateinit var ProgressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recycer_view)
        listOfLangs = ArrayList()
        ProgressLayout = findViewById(R.id.progressLayout)
        progressBar = findViewById(R.id.progressbar)
        ProgressLayout.visibility = View.VISIBLE

        UploadData()
    }

    private fun UploadData() {

        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("QUIZ").document("LANGUAGES")
        // Source can be CACHE, SERVER, or DEFAULT.
        // val source = Source.CACHE

        // Get the document, forcing the SDK to use the offline cache
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val doc = task.result
                Log.d("exist", "Cached document data: ${doc?.data}")
                if (doc != null) {
                    progressLayout.visibility = View.GONE
                    if (doc.exists()) {
                        val count: Long = doc.get("COUNT") as Long

                        for (i in 1..count) {
                            val LangName: String? = doc.getString("LANG$i")
                            val LangImage = doc.getString("IMAGE$i")
                            listOfLangs!!.add(LangaugesList(LangName, LangImage))

                        }
                    }
                    gridLayoutManger = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
                    recyclerView?.layoutManager = gridLayoutManger
                    recyclerView?.setHasFixedSize(true)
                    ProgTypeAdapter = ProgramTypeAdapter(this, listOfLangs!!)
                    recyclerView?.adapter = ProgTypeAdapter
                }

            } else {
                Log.d("noexits", "Cached get failed: ", task.exception)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {  // goto back page on click "<-" on toolbar
        onBackPressed()
        return true
    }
}






