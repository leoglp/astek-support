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

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))

        if(!isManager) {
            answerARadioButton.isEnabled = false
            answerBRadioButton.isEnabled = false
            answerCRadioButton.isEnabled = false
            answerDRadioButton.isEnabled = false
        }

        nextArrow.setOnClickListener{

            if(isManager){
                if(answerARadioButton.isChecked || answerBRadioButton.isChecked
                    || answerCRadioButton.isChecked || answerDRadioButton.isChecked) {
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
        if(answerARadioButton.isChecked) {
            return "Expertise"
        }
        if(answerBRadioButton.isChecked) {
            return "Capacité Autonome"
        }
        if(answerCRadioButton.isChecked) {
            return "Capacité Partielle"
        }
        if(answerDRadioButton.isChecked) {
            return "Notion"
        }
        return "nothing"
    }

    private fun checkedOneButton(value: String){
        if(value == "Expertise") {
            answerARadioButton.isChecked = true
        }
        if(value == "Capacité Autonome") {
            answerBRadioButton.isChecked = true
        }
        if(value == "Capacité Partielle") {
            answerCRadioButton.isChecked = true
        }
        if(value == "Notion") {
            answerDRadioButton.isChecked = true
        }
    }

    companion object {
        private const val TAG = "SkillEvaluation"
    }

}