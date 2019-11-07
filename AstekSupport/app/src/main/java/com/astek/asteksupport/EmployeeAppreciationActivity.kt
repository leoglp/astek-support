package com.astek.asteksupport

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.appreciation_layout.*
import kotlinx.android.synthetic.main.page_layout.*

class EmployeeAppreciationActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_appreciation)

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))

        nextArrow.setOnClickListener{
            if(gainEditText.text.toString().isEmpty()
                || weaknessesEditText.text.toString().isEmpty()
                || improvementEditText.text.toString().isEmpty()) {
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
        retrieveData()
    }

    private fun createValueInDB(){
        val employeeAppreciation = hashMapOf(
            "gain" to gainEditText.text.toString(),
            "weaknesses" to weaknessesEditText.text.toString(),
            "improvement" to improvementEditText.text.toString()
        )

        addValueInDataBase(employeeAppreciation, "employeeAppreciation")
    }

    private fun updateValueInDB(){

        val employeeAppreciation = hashMapOf(
            "gain" to gainEditText.text.toString(),
            "weaknesses" to weaknessesEditText.text.toString(),
            "improvement" to improvementEditText.text.toString()
        )

        updateValueInDataBase(employeeAppreciation, "employeeAppreciation", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("employeeAppreciation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("gain") != null) {
                        gainEditText.setText(document.get("gain").toString())
                    }
                    if (document.get("weaknesses") != null) {
                        weaknessesEditText.setText(document.get("weaknesses").toString())
                    }
                    if (document.get("improvement") != null) {
                        improvementEditText.setText(document.get("improvement").toString())
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
        private const val TAG = "EmployeeAppreciation"
    }

}