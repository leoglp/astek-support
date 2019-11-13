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
import kotlinx.android.synthetic.main.page_add_layout.*
import kotlinx.android.synthetic.main.page_layout.*
import kotlinx.android.synthetic.main.page_layout.backArrow
import kotlinx.android.synthetic.main.page_layout.logout
import kotlinx.android.synthetic.main.page_layout.nextArrow
import kotlinx.android.synthetic.main.page_layout.pageNumber
import kotlinx.android.synthetic.main.page_layout.settings

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
            commentaryPerformanceEditText.isEnabled = false
            commentaryPerformanceEditText.background = this.getDrawable(R.drawable.round_outline_disabled)
        }

        nextArrow.setOnClickListener{

            if(isManager){
                if(verySatisfyingRadioButton.isChecked || satisfyingRadioButton.isChecked
                    || mediumRadioButton.isChecked || insufficientRadioButton.isChecked) {
                    if(commentaryPerformanceEditText.text.toString() == "") {
                        commentaryPerformanceEditText.setText("/")
                    }
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

        settings.setOnClickListener {
            UIUtil.updateProfilInfo(this)
        }
    }

    override fun onResume() {
        super.onResume()
        retrieveData()
    }

    private fun createValueInDB(){
        val performanceEvaluation = hashMapOf(
            "performanceEvaluation" to getStringValue(),
            "commentary" to commentaryPerformanceEditText.text.toString()
        )

        addValueInDataBase(performanceEvaluation, "performanceEvaluation")
    }

    private fun updateValueInDB(){

        val performanceEvaluation = hashMapOf(
            "performanceEvaluation" to getStringValue(),
            "commentary" to commentaryPerformanceEditText.text.toString()
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
                    if (document.get("commentary") != null) {
                        commentaryPerformanceEditText.setText(document.get("commentary").toString())
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