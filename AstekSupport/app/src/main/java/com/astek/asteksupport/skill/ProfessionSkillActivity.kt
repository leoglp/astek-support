package com.astek.asteksupport.skill

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
import kotlinx.android.synthetic.main.multiple_skills_layout.*
import kotlinx.android.synthetic.main.page_add_layout.*
import kotlinx.android.synthetic.main.page_add_layout.backArrow
import kotlinx.android.synthetic.main.page_add_layout.logout
import kotlinx.android.synthetic.main.page_add_layout.nextArrow
import kotlinx.android.synthetic.main.page_add_layout.pageNumber
import kotlinx.android.synthetic.main.page_layout.*

class ProfessionSkillActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""
    private var numberTarget = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profession_skill)

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))



        if(!isManager) {
            managerGraduationEditText1.isEnabled = false
            managerGraduationText1.setTextColor(this.getColor(R.color.disabled))
            improvementAndGainEditText1.isEnabled = false
            improvementAndGainEditText1.background = this.getDrawable(R.drawable.round_outline_disabled)
            managerGraduationEditText2.isEnabled = false
            managerGraduationText2.setTextColor(this.getColor(R.color.disabled))
            improvementAndGainEditText2.isEnabled = false
            improvementAndGainEditText2.background = this.getDrawable(R.drawable.round_outline_disabled)
            managerGraduationEditText3.isEnabled = false
            managerGraduationText3.setTextColor(this.getColor(R.color.disabled))
            improvementAndGainEditText3.isEnabled = false
            improvementAndGainEditText3.background = this.getDrawable(R.drawable.round_outline_disabled)
        }

        nextArrow.setOnClickListener{
            checkField(numberTarget,it)
        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPreviousPage(this, this.javaClass.simpleName)
        }

        logout.setOnClickListener{
            UIUtil.backToHome(this)
        }


        addTarget.setOnClickListener {
            updateTargetView(true)
        }

        deleteTarget.setOnClickListener {
            updateTargetView(false)
        }

        employeeGraduationInfo.setOnClickListener {
            showMessage(it,this.getString(R.string.graduationInfo))
        }
    }

    override fun onResume() {
        super.onResume()
        retrieveData()


    }

    private fun createValueInDB(){

        var skillEvaluation : HashMap<String,String>? = null

        when (numberTarget) {
            1 -> skillEvaluation = hashMapOf(
                "skill1" to skill1EditText.text.toString(),
                "employeeGraduation1" to employeeGraduationEditText1.text.toString(),
                "managerGraduation1" to managerGraduationEditText1.text.toString(),
                "skillExample1" to skillExampleEditText1.text.toString(),
                "improvementAndGain1" to improvementAndGainEditText1.text.toString(),
                "numberTarget" to numberTarget.toString())
            2 -> skillEvaluation = hashMapOf(
                "skill1" to skill1EditText.text.toString(),
                "employeeGraduation1" to employeeGraduationEditText1.text.toString(),
                "managerGraduation1" to managerGraduationEditText1.text.toString(),
                "skillExample1" to skillExampleEditText1.text.toString(),
                "improvementAndGain1" to improvementAndGainEditText1.text.toString(),

                "skill2" to skill2EditText.text.toString(),
                "employeeGraduation2" to employeeGraduationEditText2.text.toString(),
                "managerGraduation2" to managerGraduationEditText2.text.toString(),
                "skillExample2" to skillExampleEditText2.text.toString(),
                "improvementAndGain2" to improvementAndGainEditText2.text.toString(),
                "numberTarget" to numberTarget.toString())

            3 -> skillEvaluation = hashMapOf(
                "skill1" to skill1EditText.text.toString(),
                "employeeGraduation1" to employeeGraduationEditText1.text.toString(),
                "managerGraduation1" to managerGraduationEditText1.text.toString(),
                "skillExample1" to skillExampleEditText1.text.toString(),
                "improvementAndGain1" to improvementAndGainEditText1.text.toString(),

                "skill2" to skill2EditText.text.toString(),
                "employeeGraduation2" to employeeGraduationEditText2.text.toString(),
                "managerGraduation2" to managerGraduationEditText2.text.toString(),
                "skillExample2" to skillExampleEditText2.text.toString(),
                "improvementAndGain2" to improvementAndGainEditText2.text.toString(),

                "skill3" to skill3EditText.text.toString(),
                "employeeGraduation3" to employeeGraduationEditText3.text.toString(),
                "managerGraduation3" to managerGraduationEditText3.text.toString(),
                "skillExample3" to skillExampleEditText3.text.toString(),
                "improvementAndGain3" to improvementAndGainEditText3.text.toString(),
                "numberTarget" to numberTarget.toString())
        }


        addValueInDataBase(skillEvaluation!!, "professionSkillEvaluation")
    }

    private fun updateValueInDB(){

        var skillEvaluation : HashMap<String,String>? = null

        when (numberTarget) {
            1 -> skillEvaluation = hashMapOf(
                "skill1" to skill1EditText.text.toString(),
                "employeeGraduation1" to employeeGraduationEditText1.text.toString(),
                "managerGraduation1" to managerGraduationEditText1.text.toString(),
                "skillExample1" to skillExampleEditText1.text.toString(),
                "improvementAndGain1" to improvementAndGainEditText1.text.toString(),
                "numberTarget" to numberTarget.toString())
            2 -> skillEvaluation = hashMapOf(
                "skill1" to skill1EditText.text.toString(),
                "employeeGraduation1" to employeeGraduationEditText1.text.toString(),
                "managerGraduation1" to managerGraduationEditText1.text.toString(),
                "skillExample1" to skillExampleEditText1.text.toString(),
                "improvementAndGain1" to improvementAndGainEditText1.text.toString(),

                "skill2" to skill2EditText.text.toString(),
                "employeeGraduation2" to employeeGraduationEditText2.text.toString(),
                "managerGraduation2" to managerGraduationEditText2.text.toString(),
                "skillExample2" to skillExampleEditText2.text.toString(),
                "improvementAndGain2" to improvementAndGainEditText2.text.toString(),
                "numberTarget" to numberTarget.toString())

            3 -> skillEvaluation = hashMapOf(
                "skill1" to skill1EditText.text.toString(),
                "employeeGraduation1" to employeeGraduationEditText1.text.toString(),
                "managerGraduation1" to managerGraduationEditText1.text.toString(),
                "skillExample1" to skillExampleEditText1.text.toString(),
                "improvementAndGain1" to improvementAndGainEditText1.text.toString(),

                "skill2" to skill2EditText.text.toString(),
                "employeeGraduation2" to employeeGraduationEditText2.text.toString(),
                "managerGraduation2" to managerGraduationEditText2.text.toString(),
                "skillExample2" to skillExampleEditText2.text.toString(),
                "improvementAndGain2" to improvementAndGainEditText2.text.toString(),

                "skill3" to skill3EditText.text.toString(),
                "employeeGraduation3" to employeeGraduationEditText3.text.toString(),
                "managerGraduation3" to managerGraduationEditText3.text.toString(),
                "skillExample3" to skillExampleEditText3.text.toString(),
                "improvementAndGain3" to improvementAndGainEditText3.text.toString(),
                "numberTarget" to numberTarget.toString())
        }

        updateValueInDataBase(skillEvaluation!!, "professionSkillEvaluation", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("professionSkillEvaluation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("numberTarget") != null) {
                        numberTarget = Integer.valueOf(document.get("numberTarget").toString())
                    }

                    if(numberTarget == 1) {
                        if (document.get("skill1") != null) {
                            skill1EditText.setText(document.get("skill1").toString())
                        }
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
                    }

                    else if (numberTarget == 2){
                        if (document.get("skill1") != null) {
                            skill1EditText.setText(document.get("skill1").toString())
                        }
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

                        if (document.get("skill2") != null) {
                            skill2EditText.setText(document.get("skill2").toString())
                        }
                        if (document.get("employeeGraduation2") != null) {
                            employeeGraduationEditText2.setText(document.get("employeeGraduation2").toString())
                        }
                        if (document.get("managerGraduation2") != null) {
                            managerGraduationEditText2.setText(document.get("managerGraduation2").toString())
                        }
                        if (document.get("skillExample2") != null) {
                            skillExampleEditText2.setText(document.get("skillExample2").toString())
                        }
                        if (document.get("improvementAndGain2") != null) {
                            improvementAndGainEditText2.setText(document.get("improvementAndGain2").toString())
                        }
                    }

                    else if (numberTarget == 3){
                        if (document.get("skill1") != null) {
                            skill1EditText.setText(document.get("skill1").toString())
                        }
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

                        if (document.get("skill2") != null) {
                            skill2EditText.setText(document.get("skill2").toString())
                        }
                        if (document.get("employeeGraduation2") != null) {
                            employeeGraduationEditText2.setText(document.get("employeeGraduation2").toString())
                        }
                        if (document.get("managerGraduation2") != null) {
                            managerGraduationEditText2.setText(document.get("managerGraduation2").toString())
                        }
                        if (document.get("skillExample2") != null) {
                            skillExampleEditText2.setText(document.get("skillExample2").toString())
                        }
                        if (document.get("improvementAndGain2") != null) {
                            improvementAndGainEditText2.setText(document.get("improvementAndGain2").toString())
                        }

                        if (document.get("skill3") != null) {
                            skill3EditText.setText(document.get("skill3").toString())
                        }
                        if (document.get("employeeGraduation3") != null) {
                            employeeGraduationEditText3.setText(document.get("employeeGraduation3").toString())
                        }
                        if (document.get("managerGraduation3") != null) {
                            managerGraduationEditText3.setText(document.get("managerGraduation3").toString())
                        }
                        if (document.get("skillExample3") != null) {
                            skillExampleEditText3.setText(document.get("skillExample3").toString())
                        }
                        if (document.get("improvementAndGain3") != null) {
                            improvementAndGainEditText3.setText(document.get("improvementAndGain3").toString())
                        }
                    }
                    updateTargetView()
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



    private fun updateTargetView() {
        if(numberTarget == 2) {
            skill2.visibility = View.VISIBLE
            skill2EditText.visibility = View.VISIBLE
            employeeGraduationLayout2.visibility = View.VISIBLE
            managerGraduationLayout2.visibility = View.VISIBLE
            skillExample2.visibility = View.VISIBLE
            skillExampleEditText2.visibility = View.VISIBLE
            improvementAndGain2.visibility = View.VISIBLE
            improvementAndGainEditText2.visibility = View.VISIBLE
            skill2.text = this.getString(R.string.skillDynamic,"2")
        }
        if(numberTarget == 3) {
            addTarget.visibility = View.GONE
            skill2.visibility = View.VISIBLE
            skill2EditText.visibility = View.VISIBLE
            employeeGraduationLayout2.visibility = View.VISIBLE
            managerGraduationLayout2.visibility = View.VISIBLE
            skillExample2.visibility = View.VISIBLE
            skillExampleEditText2.visibility = View.VISIBLE
            improvementAndGain2.visibility = View.VISIBLE
            improvementAndGainEditText2.visibility = View.VISIBLE

            skill3.visibility = View.VISIBLE
            skill3EditText.visibility = View.VISIBLE
            employeeGraduationLayout3.visibility = View.VISIBLE
            managerGraduationLayout3.visibility = View.VISIBLE
            skillExample3.visibility = View.VISIBLE
            skillExampleEditText3.visibility = View.VISIBLE
            improvementAndGain3.visibility = View.VISIBLE
            improvementAndGainEditText3.visibility = View.VISIBLE
            skill2.text = this.getString(R.string.skillDynamic,"2")
            skill3.text = this.getString(R.string.skillDynamic,"3")
        }
    }

    private fun updateTargetView(isAdded: Boolean) {
        if(isAdded){
            numberTarget++
            if(numberTarget == 2) {
                deleteTarget.visibility = View.VISIBLE

                skill2.visibility = View.VISIBLE
                skill2EditText.visibility = View.VISIBLE
                employeeGraduationLayout2.visibility = View.VISIBLE
                managerGraduationLayout2.visibility = View.VISIBLE
                skillExample2.visibility = View.VISIBLE
                skillExampleEditText2.visibility = View.VISIBLE
                improvementAndGain2.visibility = View.VISIBLE
                improvementAndGainEditText2.visibility = View.VISIBLE

                skill2.text = this.getString(R.string.skillDynamic,"2")
            }
            if(numberTarget == 3) {
                addTarget.visibility = View.GONE

                skill3.visibility = View.VISIBLE
                skill3EditText.visibility = View.VISIBLE
                employeeGraduationLayout3.visibility = View.VISIBLE
                managerGraduationLayout3.visibility = View.VISIBLE
                skillExample3.visibility = View.VISIBLE
                skillExampleEditText3.visibility = View.VISIBLE
                improvementAndGain3.visibility = View.VISIBLE
                improvementAndGainEditText3.visibility = View.VISIBLE

                skill3.text = this.getString(R.string.skillDynamic,"3")

            }
        } else {
            numberTarget--
            if(numberTarget == 1) {
                deleteTarget.visibility = View.GONE
                skill2.visibility = View.GONE
                skill2EditText.visibility = View.GONE
                employeeGraduationLayout2.visibility = View.GONE
                managerGraduationLayout2.visibility = View.GONE
                skillExample2.visibility = View.GONE
                skillExampleEditText2.visibility = View.GONE
                improvementAndGain2.visibility = View.GONE
                improvementAndGainEditText2.visibility = View.GONE
            }
            if(numberTarget == 2) {
                addTarget.visibility = View.VISIBLE

                skill3.visibility = View.GONE
                skill3EditText.visibility = View.GONE
                employeeGraduationLayout3.visibility = View.GONE
                managerGraduationLayout3.visibility = View.GONE
                skillExample3.visibility = View.GONE
                skillExampleEditText3.visibility = View.GONE
                improvementAndGain3.visibility = View.GONE
                improvementAndGainEditText3.visibility = View.GONE
            }
        }
    }


    private fun checkField(numberTarget: Int , it: View){

        if(isManager) {

            when (numberTarget) {
                1 -> if(skill1EditText.text.toString().isEmpty() || employeeGraduationEditText1.text.toString().isEmpty()
                    || managerGraduationEditText1.text.toString().isEmpty() || skillExampleEditText1.text.toString().isEmpty()
                    || improvementAndGainEditText1.text.toString().isEmpty()) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }


                2 -> if(skill1EditText.text.toString().isEmpty() || employeeGraduationEditText1.text.toString().isEmpty()
                    || managerGraduationEditText1.text.toString().isEmpty() || skillExampleEditText1.text.toString().isEmpty()
                    || improvementAndGainEditText1.text.toString().isEmpty()
                    || skill2EditText.text.toString().isEmpty() || employeeGraduationEditText2.text.toString().isEmpty()
                    || managerGraduationEditText2.text.toString().isEmpty() || skillExampleEditText2.text.toString().isEmpty()
                    || improvementAndGainEditText2.text.toString().isEmpty()) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }


                3 -> if(skill1EditText.text.toString().isEmpty() || employeeGraduationEditText1.text.toString().isEmpty()
                    || managerGraduationEditText1.text.toString().isEmpty() || skillExampleEditText1.text.toString().isEmpty()
                    || improvementAndGainEditText1.text.toString().isEmpty()
                    || skill2EditText.text.toString().isEmpty() || employeeGraduationEditText2.text.toString().isEmpty()
                    || managerGraduationEditText2.text.toString().isEmpty() || skillExampleEditText2.text.toString().isEmpty()
                    || improvementAndGainEditText2.text.toString().isEmpty()
                    || skill3EditText.text.toString().isEmpty() || employeeGraduationEditText3.text.toString().isEmpty()
                    || managerGraduationEditText3.text.toString().isEmpty() || skillExampleEditText3.text.toString().isEmpty()
                    || improvementAndGainEditText3.text.toString().isEmpty()) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }
            }
        } else {
            when (numberTarget) {
                1 -> if(skill1EditText.text.toString().isEmpty() || employeeGraduationEditText1.text.toString().isEmpty()
                   || skillExampleEditText1.text.toString().isEmpty()) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }


                2 -> if(skill1EditText.text.toString().isEmpty() || employeeGraduationEditText1.text.toString().isEmpty()
                    || skillExampleEditText1.text.toString().isEmpty()
                    || skill2EditText.text.toString().isEmpty() || employeeGraduationEditText2.text.toString().isEmpty()
                    ||  skillExampleEditText2.text.toString().isEmpty()) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }


                3 -> if(skill1EditText.text.toString().isEmpty() || employeeGraduationEditText1.text.toString().isEmpty()
                    || skillExampleEditText1.text.toString().isEmpty()
                    || skill2EditText.text.toString().isEmpty() || employeeGraduationEditText2.text.toString().isEmpty()
                    || skillExampleEditText2.text.toString().isEmpty()
                    || skill3EditText.text.toString().isEmpty() || employeeGraduationEditText3.text.toString().isEmpty()
                    || skillExampleEditText3.text.toString().isEmpty()) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }
            }
        }
    }

    companion object {
        private const val TAG = "ProfessionSkill"
    }

}