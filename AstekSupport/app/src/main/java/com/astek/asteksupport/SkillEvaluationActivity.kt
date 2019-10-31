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
import kotlinx.android.synthetic.main.activity_skill_evaluation.*
import kotlinx.android.synthetic.main.page_layout.*

class SkillEvaluationActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill_evaluation)

        pageNumber.text = this.getString(R.string.pageNumber,"4","5")

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
                    UIUtil.goToPage("7", this)
                } else {
                    UIUtil.showMessage(it, this.getString(R.string.err_no_checked))
                }
            } else {
                UIUtil.goToPage("7", this)
            }

        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPage("5", this)
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
        val skillEvaluation = hashMapOf(
            "skillEvaluation" to getStringValue()
        )

        addValueInDataBase(skillEvaluation, "skillEvaluation")
    }

    private fun updateValueInDB(){

        val skillEvaluation = hashMapOf(
            "skillEvaluation" to getStringValue()
        )

        updateValueInDataBase(skillEvaluation, "skillEvaluation", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("skillEvaluation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("skillEvaluation") != null) {
                        checkedOneButton(document.get("skillEvaluation").toString())
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
        private const val TAG = "ManagerAppreciation"
    }

}