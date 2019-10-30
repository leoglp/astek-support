package com.astek.asteksupport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.documentId
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.isManager
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updatePageValue
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.astek.asteksupport.utils.UIUtil.Companion.goToPage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_interview_context.*

class InterviewContextActivity : AppCompatActivity() {


    private var updateValue = false
    private var documentUpdateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview_context)

        pageNumber.text = this.getString(R.string.pageNumber,"1","2")

        nextArrowInterview.setOnClickListener{
            if(bilanDateEditText.text.toString().isEmpty()
                || previousInterviewEditText.text.toString().isEmpty()
                || managerNameEditText.text.toString().isEmpty()
                || interviewDateEditText.text.toString().isEmpty()) {
                UIUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                if(updateValue){
                    updateValueInDataBase()
                } else {
                    createValueInDataBase()
                }

                goToPage("2",this)

            }
        }

        if(isManager){
            backArrowInterview.visibility = View.VISIBLE
            backArrowInterview.setOnClickListener{
                onBackPressed()
            }
        } else {
            backArrowInterview.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        retrieveBilanData()
    }

    private fun createValueInDataBase(){
        val interviewContext = hashMapOf(
            "bilanDate" to bilanDateEditText.text.toString(),
            "previousDate" to previousInterviewEditText.text.toString(),
            "interviewDate" to interviewDateEditText.text.toString(),
            "managerName" to managerNameEditText.text.toString()
        )

        addValueInDataBase(interviewContext,"interviewContext")
    }

    private fun updateValueInDataBase(){

        val interviewContext = hashMapOf(
            "bilanDate" to bilanDateEditText.text.toString(),
            "previousDate" to previousInterviewEditText.text.toString(),
            "interviewDate" to interviewDateEditText.text.toString(),
            "managerName" to managerNameEditText.text.toString()
        )

        updateValueInDataBase(interviewContext,"interviewContext",documentUpdateId)
    }


    private fun retrieveBilanData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(documentId)
            .collection("interviewContext")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("bilanDate") != null) {
                        bilanDateEditText.setText(document.get("bilanDate").toString())
                    }
                    if (document.get("previousDate") != null) {
                        previousInterviewEditText.setText(document.get("previousDate").toString())
                    }
                    if (document.get("interviewDate") != null) {
                        interviewDateEditText.setText(document.get("interviewDate").toString())
                    }
                    if (document.get("managerName") != null) {
                        managerNameEditText.setText(document.get("managerName").toString())
                    }
                    updateValue = true
                    documentUpdateId = document.id
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents.", exception)
            }

    }


    companion object {
        private const val TAG = "InterviewContext"
    }

}