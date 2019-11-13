package com.astek.asteksupport.skill.behavioural

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.isManager
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.astek.asteksupport.utils.UIUtil.Companion.showMessage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.behaviour_skill_layout.*
import kotlinx.android.synthetic.main.page_add_layout.*
import kotlinx.android.synthetic.main.page_layout.*
import kotlinx.android.synthetic.main.page_layout.backArrow
import kotlinx.android.synthetic.main.page_layout.logout
import kotlinx.android.synthetic.main.page_layout.nextArrow
import kotlinx.android.synthetic.main.page_layout.pageNumber
import kotlinx.android.synthetic.main.page_layout.settings

class ImplicationSkillActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implication_skill)

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))


        if(!isManager) {
            managerGraduationEditText1.isEnabled = false
            managerGraduationText1.setTextColor(this.getColor(R.color.disabled))
            improvementAndGainEditText1.isEnabled = false
            improvementAndGainEditText1.background = this.getDrawable(R.drawable.round_outline_disabled)
        }

        nextArrow.setOnClickListener{
            checkField(it)
        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPreviousPage(this, this.javaClass.simpleName)
        }

        logout.setOnClickListener{
            UIUtil.backToHome(this)
        }

        employeeGraduationInfo.setOnClickListener {
            showMessage(it,this.getString(R.string.graduationInfo))
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

        val skillEvaluation = hashMapOf(
            "employeeGraduation1" to employeeGraduationEditText1.text.toString(),
            "managerGraduation1" to managerGraduationEditText1.text.toString(),
            "skillExample1" to skillExampleEditText1.text.toString(),
            "improvementAndGain1" to improvementAndGainEditText1.text.toString())

        addValueInDataBase(skillEvaluation, "implicationSkillEvaluation")
    }

    private fun updateValueInDB(){

        val skillEvaluation = hashMapOf(
            "employeeGraduation1" to employeeGraduationEditText1.text.toString(),
            "managerGraduation1" to managerGraduationEditText1.text.toString(),
            "skillExample1" to skillExampleEditText1.text.toString(),
            "improvementAndGain1" to improvementAndGainEditText1.text.toString())

        updateValueInDataBase(skillEvaluation, "implicationSkillEvaluation", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("implicationSkillEvaluation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    if (document.get("employeeGraduation1") != null) {
                        employeeGraduationEditText1.setText(document.get("employeeGraduation1").toString())
                    }
                    if (document.get("managerGraduation1") != null) {
                        managerGraduationEditText1.setText(document.get("managerGraduation1").toString())
                    }
                    if (document.get("skillExample1") != null) {
                        skillExampleEditText1.setText(document.get("skillExample1").toString())
                    }
                    if (document.get("improvementAndGain1") != null) {
                        improvementAndGainEditText1.setText(document.get("improvementAndGain1").toString())
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

    private fun checkField(it: View){
        if(isManager) {
            if(employeeGraduationEditText1.text.toString().isEmpty()
                || managerGraduationEditText1.text.toString().isEmpty() || skillExampleEditText1.text.toString().isEmpty()
                || improvementAndGainEditText1.text.toString().isEmpty()) {
                showMessage(it, this.getString(R.string.err_no_input))
            } else {
                if(managerGraduationEditText1.text.toString().toInt() in 1..4
                    && employeeGraduationEditText1.text.toString().toInt() in 1..4) {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                } else {
                    showMessage(it, this.getString(R.string.err_graduation))
                }
            }
        } else {
            if(employeeGraduationEditText1.text.toString().isEmpty()
                || skillExampleEditText1.text.toString().isEmpty()) {
                showMessage(it, this.getString(R.string.err_no_input))
            } else {
                if(employeeGraduationEditText1.text.toString().toInt() in 1..4) {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                } else {
                    showMessage(it, this.getString(R.string.err_graduation))
                }
            }
        }
    }

    companion object {
        private const val TAG = "ImplicationSkill"
    }

}