package com.astek.asteksupport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.documentId
import com.astek.asteksupport.utils.ShowUtil
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_interview_context.*

class InterviewContextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview_context)

        pageNumber.text = this.getString(R.string.pageNumber,"1","2")

        nextArrow.setOnClickListener{
            if(bilanDateEditText.text.toString().isEmpty()
                || previousInterviewEditText.text.toString().isEmpty()
                || managerNameEditText.text.toString().isEmpty()
                || interviewDateEditText.text.toString().isEmpty()) {
                ShowUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                saveValueInDataBase()
                val intent = Intent(this,BilanMissionActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        retrieveBilanData()
    }

    private fun saveValueInDataBase(){
        val interviewContext = hashMapOf(
            "bilanDate" to bilanDateEditText.text.toString(),
            "previousDate" to previousInterviewEditText.text.toString(),
            "interviewDate" to interviewDateEditText.text.toString(),
            "managerName" to managerNameEditText.text.toString()
        )

        // Add a new document with a generated ID
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(documentId)
            .collection("interviewContext")
            .add(interviewContext)
            .addOnSuccessListener { documentReference ->
                Log.d("TITI", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("TITI", "Error adding document", e)
            }
    }


    private fun retrieveBilanData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(documentId)
            .collection("interviewContext")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TITI", "document bilanDate ${document.get("bilanDate")}")
                    Log.d("TITI", "document previousDate ${document.get("previousDate")}")
                    Log.d("TITI", "document interviewDate ${document.get("interviewDate")}")
                    Log.d("TITI", "document managerName ${document.get("managerName")}")


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
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TITI", "Error getting documents.", exception)
            }

    }
}