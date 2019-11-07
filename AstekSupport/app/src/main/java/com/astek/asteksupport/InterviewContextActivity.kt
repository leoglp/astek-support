package com.astek.asteksupport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.employeeDocumentId
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.isManager
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.astek.asteksupport.utils.UIUtil.Companion.getPage
import com.astek.asteksupport.utils.UIUtil.Companion.getTotalPage
import com.astek.asteksupport.utils.UIUtil.Companion.goToPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_interview_context.*
import kotlinx.android.synthetic.main.page_layout.*

class InterviewContextActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview_context)

        pageNumber.text = this.getString(R.string.pageNumber, getPage(this, this.javaClass.simpleName).toString(),
            getTotalPage(this))


        nextArrow.setOnClickListener{
            if(bilanDateEditText.text.toString().isEmpty()
                || previousInterviewEditText.text.toString().isEmpty()
                || managerNameEditText.text.toString().isEmpty()
                || interviewDateEditText.text.toString().isEmpty()) {
                UIUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                createOrUpdate()
                UIUtil.goToNextPage(this, this.javaClass.simpleName)
            }
        }


        backArrow.setOnClickListener{
            createOrUpdate()
            if(isManager) {
                goToPage("-1", this)
            } else {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }

        logout.setOnClickListener{
            UIUtil.backToHome(this)
        }

    }

    override fun onResume() {
        super.onResume()
        retrieveData()
    }

    private fun createValueInDB(){
        val interviewContext = hashMapOf(
            "bilanDate" to bilanDateEditText.text.toString(),
            "previousDate" to previousInterviewEditText.text.toString(),
            "interviewDate" to interviewDateEditText.text.toString(),
            "managerName" to managerNameEditText.text.toString()
        )

        addValueInDataBase(interviewContext,"interviewContext")
    }

    private fun updateValueInDB(){

        val interviewContext = hashMapOf(
            "bilanDate" to bilanDateEditText.text.toString(),
            "previousDate" to previousInterviewEditText.text.toString(),
            "interviewDate" to interviewDateEditText.text.toString(),
            "managerName" to managerNameEditText.text.toString()
        )

        updateValueInDataBase(interviewContext,"interviewContext",documentUpdateId)
    }


    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(employeeDocumentId)
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

    private fun createOrUpdate(){
        if(updateValue){
            updateValueInDB()
        } else {
            createValueInDB()
        }
    }


    companion object {
        private const val TAG = "InterviewContext"
    }

}