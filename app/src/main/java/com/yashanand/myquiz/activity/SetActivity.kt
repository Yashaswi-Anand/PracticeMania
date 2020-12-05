package com.yashanand.myquiz.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.yashanand.myquiz.R
import com.yashanand.myquiz.adapters.Sets_Adapter
import kotlinx.android.synthetic.main.activity_set.*


class SetActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var gridLayoutManger: GridLayoutManager? = null
    private var list_Of_Sets: ArrayList<String>? = null
    private var sets_Adapter: Sets_Adapter? = null
    lateinit var ProgressLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var relativeLayout: RelativeLayout
    var ItemId: Int? = null
    var Lang_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recycer_view_sets)
        list_Of_Sets = ArrayList()
        ProgressLayout = findViewById(R.id.progressLayout)
        progressBar = findViewById(R.id.progressbar)
        ProgressLayout.visibility = View.VISIBLE

        if (intent != null) {
            ItemId = intent.getIntExtra("Position", 0)
            Lang_name = intent.getStringExtra("language_name")
            supportActionBar?.title = "SETS OF : $Lang_name"
        } else {
            finish()
            Toast.makeText(this, "Some unexpected error occurred!", Toast.LENGTH_SHORT).show()
        }

        UploadSets()
    }

    private fun UploadSets() {

        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("QUIZ").document("LANG$ItemId")

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val doc = task.result
                Log.d("Setdoc", "Cached  sets document data: ${doc?.data}")
                if (doc != null) {
                    progressLayout.visibility = View.GONE
                    if (doc.exists()) {

                        val count: Long = doc.get("NO_OF_SETS") as Long
                        for (i in 1..count) {
                            val SetsNumber: String? = doc.getString("SET$i")
                            if (SetsNumber != null) {
                                list_Of_Sets!!.add(SetsNumber)
                            }
                        }

                    }
                    gridLayoutManger = GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
                    recyclerView?.layoutManager = gridLayoutManger
                    recyclerView?.setHasFixedSize(true)
                    sets_Adapter = ItemId?.let { Sets_Adapter(this, list_Of_Sets!!, it) }
                    recyclerView?.adapter = sets_Adapter
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
