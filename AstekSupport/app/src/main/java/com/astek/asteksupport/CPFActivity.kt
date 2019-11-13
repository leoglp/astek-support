package com.astek.asteksupport

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.isManager
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.astek.asteksupport.utils.UIUtil.Companion.backToHome
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cpf.*
import kotlinx.android.synthetic.main.page_add_layout.*
import kotlinx.android.synthetic.main.page_layout.*
import kotlinx.android.synthetic.main.page_layout.backArrow
import kotlinx.android.synthetic.main.page_layout.logout
import kotlinx.android.synthetic.main.page_layout.nextArrow
import kotlinx.android.synthetic.main.page_layout.pageNumber
import kotlinx.android.synthetic.main.page_layout.settings

class CPFActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpf)

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))

        if(!isManager) {
            commentaryCpfEditText.isEnabled = false
            commentaryCpfEditText.background = this.getDrawable(R.drawable.round_outline_disabled)
        }

        nextArrow.setOnClickListener{
            if(!isManager) {
                if(numberHoursCpfEditText.text.toString().isEmpty() || dateCpfEditText.text.toString().isEmpty()) {
                    UIUtil.showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }
            } else {
                if(numberHoursCpfEditText.text.toString().isEmpty() || dateCpfEditText.text.toString().isEmpty()
                    || commentaryCpfEditText.text.toString().isEmpty()) {
                    UIUtil.showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }
            }
        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPreviousPage(this, this.javaClass.simpleName)
        }

        logout.setOnClickListener{
            backToHome(this)
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
        val cpf = hashMapOf(
            "numberHours" to numberHoursCpfEditText.text.toString(),
            "date" to dateCpfEditText.text.toString(),
            "question" to getStringValue(),
            "commentary" to commentaryCpfEditText.text.toString()
        )

        addValueInDataBase(cpf, "cpf")
    }

    private fun updateValueInDB(){
        val cpf = hashMapOf(
            "numberHours" to numberHoursCpfEditText.text.toString(),
            "date" to dateCpfEditText.text.toString(),
            "question" to getStringValue(),
            "commentary" to commentaryCpfEditText.text.toString()
        )

        updateValueInDataBase(cpf, "cpf", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("cpf")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("numberHours") != null) {
                        numberHoursCpfEditText.setText(document.get("numberHours").toString())
                    }
                    if (document.get("date") != null) {
                        dateCpfEditText.setText(document.get("date").toString())
                    }
                    if (document.get("question") != null) {
                        checkedOneButton(document.get("question").toString())
                    }
                    if (document.get("commentary") != null) {
                        commentaryCpfEditText.setText(document.get("commentary").toString())
                    }
                    updateValue = true
                    documentUpdateId = document.id
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents.", exception)
            }
    }

    private fun getStringValue(): String {
        return if(yesRadioButton.isChecked) {
            "OUI"
        } else {
            "NON"
        }
    }

    private fun checkedOneButton(value: String){
        if(value == "OUI") {
            yesRadioButton.isChecked = true
        } else {
            noRadioButton.isChecked = true
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
        private const val TAG = "CPF"
    }

}