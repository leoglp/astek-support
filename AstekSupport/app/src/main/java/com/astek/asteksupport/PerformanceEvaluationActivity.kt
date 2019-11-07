package com.astek.asteksupport

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.isManager
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_performance_evaluation.*
import kotlinx.android.synthetic.main.page_layout.*

class PerformanceEvaluationActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance_evaluation)

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))

        if(!isManager) {
            verySatisfyingRadioButton.isEnabled = false
            satisfyingRadioButton.isEnabled = false
            mediumRadioButton.isEnabled = false
            insufficientRadioButton.isEnabled = false
        }

        nextArrow.setOnClickListener{

            if(isManager){
                if(verySatisfyingRadioButton.isChecked || satisfyingRadioButton.isChecked
                    || mediumRadioButton.isChecked || insufficientRadioButton.isChecked) {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                } else {
                    UIUtil.showMessage(it, this.getString(R.string.err_no_checked))
                }
            } else {
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
        val performanceEvaluation = hashMapOf(
            "performanceEvaluation" to getStringValue()
        )

        addValueInDataBase(performanceEvaluation, "performanceEvaluation")
    }

    private fun updateValueInDB(){

        val performanceEvaluation = hashMapOf(
            "performanceEvaluation" to getStringValue()
        )

        updateValueInDataBase(performanceEvaluation, "performanceEvaluation", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("performanceEvaluation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("performanceEvaluation") != null) {
                        checkedOneButton(document.get("performanceEvaluation").toString())
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


    private fun getStringValue(): String {
        if(verySatisfyingRadioButton.isChecked) {
            return "Très Satisfaisant"
        }
        if(satisfyingRadioButton.isChecked) {
            return "Satisfaisant"
        }
        if(mediumRadioButton.isChecked) {
            return "Moyen"
        }
        if(insufficientRadioButton.isChecked) {
            return "Insuffisant"
        }
        return "nothing"
    }

    private fun checkedOneButton(value: String){
        if(value == "Très Satisfaisant") {
            verySatisfyingRadioButton.isChecked = true
        }
        if(value == "Satisfaisant") {
            satisfyingRadioButton.isChecked = true
        }
        if(value == "Moyen") {
            mediumRadioButton.isChecked = true
        }
        if(value == "Insuffisant") {
            insufficientRadioButton.isChecked = true
        }
    }

    companion object {
        private const val TAG = "PerformanceEvaluation"
    }

}