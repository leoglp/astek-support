package com.astek.asteksupport.evolution

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.evolution_layout.*
import kotlinx.android.synthetic.main.page_add_layout.*
import kotlinx.android.synthetic.main.page_layout.*
import kotlinx.android.synthetic.main.page_layout.backArrow
import kotlinx.android.synthetic.main.page_layout.logout
import kotlinx.android.synthetic.main.page_layout.nextArrow
import kotlinx.android.synthetic.main.page_layout.pageNumber

class LongEvolutionActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_evolution)

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))

        nextArrow.setOnClickListener{
            if(evolutionEditText.text.toString().isEmpty()
                || justificationEvolutionEditText.text.toString().isEmpty()
                || meansEvolutionEditText.text.toString().isEmpty()) {
                UIUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                createOrUpdate()
                UIUtil.goToNextPage(this, this.javaClass.simpleName)
            }
        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPreviousPage(this, this.javaClass.simpleName)
        }

        logout.setOnClickListener{
            UIUtil.backToHome(this)
        }
    }

    override fun onResume() {
        super.onResume()
        evolution.text = this.getString(R.string.longEvolution)
        retrieveData()
    }

    private fun createValueInDB(){
        val longEvolution = hashMapOf(
            "evolution" to evolutionEditText.text.toString(),
            "justification" to justificationEvolutionEditText.text.toString(),
            "means" to meansEvolutionEditText.text.toString()
        )

        addValueInDataBase(longEvolution, "longEvolution")
    }

    private fun updateValueInDB(){

        val longEvolution = hashMapOf(
            "evolution" to evolutionEditText.text.toString(),
            "justification" to justificationEvolutionEditText.text.toString(),
            "means" to meansEvolutionEditText.text.toString()
        )

        updateValueInDataBase(longEvolution, "longEvolution", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("longEvolution")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("evolution") != null) {
                        evolutionEditText.setText(document.get("evolution").toString())
                    }
                    if (document.get("justification") != null) {
                        justificationEvolutionEditText.setText(document.get("justification").toString())
                    }
                    if (document.get("means") != null) {
                        meansEvolutionEditText.setText(document.get("means").toString())
                    }
                    updateValue = true
                    documentUpdateId = document.id
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents.", exception)
            }
    }

    private fun createOrUpdate(){
        if(updateValue){
            updateValueInDB()
        } else {
            createValueInDB()
        }
    }

    companion object {
        private const val TAG = "LongEvolution"
    }

}