package com.astek.asteksupport

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.astek.asteksupport.utils.UIUtil.Companion.backToHome
import com.astek.asteksupport.utils.UIUtil.Companion.showMessage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_bilan_formation.*
import kotlinx.android.synthetic.main.page_add_layout.*

class BilanFormationActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""
    private var numberTarget = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilan_formation)

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))


        nextArrow.setOnClickListener{
            checkField(numberTarget,it)
        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPreviousPage(this, this.javaClass.simpleName)
        }

        addTarget.setOnClickListener {
            updateTargetView(true)
        }

        deleteTarget.setOnClickListener {
            updateTargetView(false)
        }

        logout.setOnClickListener{
            backToHome(this)
        }
    }


    override fun onResume() {
        super.onResume()
        retrieveData()
    }

    private fun createValueInDB(){
        var bilanFormation : HashMap<String,String>? = null

        when (numberTarget) {
            1 -> bilanFormation = hashMapOf(
                "entitled1" to entitledEditText1.text.toString(),
                "date1" to dateEditText1.text.toString(),
                "duration1" to durationEditText1.text.toString(),
                "initiative1" to getStringValue(employerRadioButton1, employeeRadioButton1),
                "implementation1" to implementationEditText1.text.toString(),
                "commentary1" to commentaryEditText1.text.toString(),

                "numberTarget" to numberTarget.toString()
            )
            2 -> bilanFormation = hashMapOf(
                "entitled1" to entitledEditText1.text.toString(),
                "date1" to dateEditText1.text.toString(),
                "duration1" to durationEditText1.text.toString(),
                "initiative1" to getStringValue(employerRadioButton1, employeeRadioButton1),
                "implementation1" to implementationEditText1.text.toString(),
                "commentary1" to commentaryEditText1.text.toString(),

                "entitled2" to entitledEditText2.text.toString(),
                "date2" to dateEditText2.text.toString(),
                "duration2" to durationEditText2.text.toString(),
                "initiative2" to getStringValue(employerRadioButton2, employeeRadioButton2),
                "implementation2" to implementationEditText2.text.toString(),
                "commentary2" to commentaryEditText2.text.toString(),

                "numberTarget" to numberTarget.toString()
            )

        }

        addValueInDataBase(bilanFormation!!, "bilanFormation")
    }

    private fun updateValueInDB(){

        var bilanFormation : HashMap<String,String>? = null

        when (numberTarget) {
            1 -> bilanFormation = hashMapOf(
                "entitled1" to entitledEditText1.text.toString(),
                "date1" to dateEditText1.text.toString(),
                "duration1" to durationEditText1.text.toString(),
                "initiative1" to getStringValue(employerRadioButton1,employeeRadioButton1),
                "implementation1" to implementationEditText1.text.toString(),
                "commentary1" to commentaryEditText1.text.toString(),

                "numberTarget" to numberTarget.toString())
            2 -> bilanFormation = hashMapOf(
                "entitled1" to entitledEditText1.text.toString(),
                "date1" to dateEditText1.text.toString(),
                "duration1" to durationEditText1.text.toString(),
                "initiative1" to getStringValue(employerRadioButton1,employeeRadioButton1),
                "implementation1" to implementationEditText1.text.toString(),
                "commentary1" to commentaryEditText1.text.toString(),

                "entitled2" to entitledEditText2.text.toString(),
                "date2" to dateEditText2.text.toString(),
                "duration2" to durationEditText2.text.toString(),
                "initiative2" to getStringValue(employerRadioButton2,employeeRadioButton2),
                "implementation2" to implementationEditText2.text.toString(),
                "commentary2" to commentaryEditText2.text.toString(),

                "numberTarget" to numberTarget.toString())
        }

        updateValueInDataBase(bilanFormation!!, "bilanFormation", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("bilanFormation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("numberTarget") != null) {
                        numberTarget = Integer.valueOf(document.get("numberTarget").toString())
                    }

                    if(numberTarget == 1) {
                        if (document.get("entitled1") != null) {
                            entitledEditText1.setText(document.get("entitled1").toString())
                        }
                        if (document.get("date1") != null) {
                            dateEditText1.setText(document.get("date1").toString())
                        }
                        if (document.get("duration1") != null) {
                            durationEditText1.setText(document.get("duration1").toString())
                        }
                        if (document.get("initiative1") != null) {
                            checkedOneButton(document.get("initiative1").toString(),employerRadioButton1,employeeRadioButton1)
                        }
                        if (document.get("implementation1") != null) {
                            implementationEditText1.setText(document.get("implementation1").toString())
                        }
                        if (document.get("commentary1") != null) {
                            commentaryEditText1.setText(document.get("commentary1").toString())
                        }
                    }

                    else if (numberTarget == 2){
                        if (document.get("entitled1") != null) {
                            entitledEditText1.setText(document.get("entitled1").toString())
                        }
                        if (document.get("date1") != null) {
                            dateEditText1.setText(document.get("date1").toString())
                        }
                        if (document.get("duration1") != null) {
                            durationEditText1.setText(document.get("duration1").toString())
                        }
                        if (document.get("initiative1") != null) {
                            checkedOneButton(document.get("initiative1").toString(),employerRadioButton1,employeeRadioButton1)
                        }
                        if (document.get("implementation1") != null) {
                            implementationEditText1.setText(document.get("implementation1").toString())
                        }
                        if (document.get("commentary1") != null) {
                            commentaryEditText1.setText(document.get("commentary1").toString())
                        }

                        if (document.get("entitled2") != null) {
                            entitledEditText2.setText(document.get("entitled2").toString())
                        }
                        if (document.get("date2") != null) {
                            dateEditText1.setText(document.get("date2").toString())
                        }
                        if (document.get("duration2") != null) {
                            durationEditText2.setText(document.get("duration2").toString())
                        }
                        if (document.get("initiative2") != null) {
                            checkedOneButton(document.get("initiative2").toString(),employerRadioButton2,employeeRadioButton2)
                        }
                        if (document.get("implementation2") != null) {
                            implementationEditText2.setText(document.get("implementation2").toString())
                        }
                        if (document.get("commentary2") != null) {
                            commentaryEditText2.setText(document.get("commentary2").toString())
                        }
                    }
                    updateValue = true
                    documentUpdateId = document.id
                    updateTargetView()
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
            deleteTarget.visibility = View.VISIBLE
            addTarget.visibility = View.GONE

            entitledLayout2.visibility = View.VISIBLE
            dateLayout2.visibility = View.VISIBLE
            durationLayout2.visibility = View.VISIBLE
            initiativeLayout2.visibility = View.VISIBLE
            implementationEditText2.visibility = View.VISIBLE
            implementation2.visibility = View.VISIBLE
            commentaryEditText2.visibility = View.VISIBLE
            commentary2.visibility = View.VISIBLE
        }
    }

    private fun updateTargetView(isAdded: Boolean) {
        if(isAdded){
            numberTarget++
            if(numberTarget == 2) {
                deleteTarget.visibility = View.VISIBLE
                addTarget.visibility = View.GONE

                entitledLayout2.visibility = View.VISIBLE
                dateLayout2.visibility = View.VISIBLE
                durationLayout2.visibility = View.VISIBLE
                initiativeLayout2.visibility = View.VISIBLE
                implementationEditText2.visibility = View.VISIBLE
                implementation2.visibility = View.VISIBLE
                commentaryEditText2.visibility = View.VISIBLE
                commentary2.visibility = View.VISIBLE
            }
        } else {
            numberTarget--
            if(numberTarget == 1) {
                deleteTarget.visibility = View.GONE
                addTarget.visibility = View.VISIBLE

                entitledLayout2.visibility = View.GONE
                dateLayout2.visibility = View.GONE
                durationLayout2.visibility = View.GONE
                initiativeLayout2.visibility = View.GONE
                implementationEditText2.visibility = View.GONE
                implementation2.visibility = View.GONE
                commentaryEditText2.visibility = View.GONE
                commentary2.visibility = View.GONE
            }
        }
    }



    private fun getStringValue(radioButtonEmployer: RadioButton , radioButtonEmployee: RadioButton): String {
        if(radioButtonEmployer.isChecked) {
            return "Employeur"
        }
        if(radioButtonEmployee.isChecked) {
            return "Salarié"
        }
        return "nothing"
    }

    private fun checkedOneButton(value: String , radioButtonEmployer: RadioButton , radioButtonEmployee: RadioButton){
        if(value == "Salarié") {
            radioButtonEmployee.isChecked = true
        } else {
            radioButtonEmployer.isChecked = true
        }
    }


    private fun checkField(numberTarget: Int , it: View){
        when (numberTarget) {
            1 -> if(entitledEditText1.text.toString().isEmpty() || dateEditText1.text.toString().isEmpty()
                || durationEditText1.text.toString().isEmpty() || implementationEditText1.text.toString().isEmpty()
                || commentaryEditText1.text.toString().isEmpty()) {
                showMessage(it, this.getString(R.string.err_no_input))
            } else {
                createOrUpdate()
                UIUtil.goToNextPage(this, this.javaClass.simpleName)
            }


            2 -> if(entitledEditText1.text.toString().isEmpty() || dateEditText1.text.toString().isEmpty()
                || durationEditText1.text.toString().isEmpty() || implementationEditText1.text.toString().isEmpty()
                || commentaryEditText1.text.toString().isEmpty()
                || entitledEditText2.text.toString().isEmpty() || dateEditText2.text.toString().isEmpty()
                || durationEditText2.text.toString().isEmpty() || implementationEditText2.text.toString().isEmpty()
                || commentaryEditText2.text.toString().isEmpty()) {
                showMessage(it, this.getString(R.string.err_no_input))
            } else {
                createOrUpdate()
                UIUtil.goToNextPage(this, this.javaClass.simpleName)
            }
        }
    }

    companion object {
        private const val TAG = "BilanFormation"
    }

}