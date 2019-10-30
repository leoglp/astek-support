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
import kotlinx.android.synthetic.main.activity_manager_appreciation.*

class ManagerAppreciationActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_appreciation)

        pageNumber.text = this.getString(R.string.pageNumber,"4","5")

        if(!isManager) {
            gainEditText.isEnabled = false
            gainEditText.background = this.getDrawable(R.drawable.round_outline_disabled)
            weaknessesEditText.isEnabled = false
            weaknessesEditText.background = this.getDrawable(R.drawable.round_outline_disabled)
            improvementEditText.isEnabled = false
            improvementEditText.background = this.getDrawable(R.drawable.round_outline_disabled)

        }

        nextArrow.setOnClickListener{
            if(gainEditText.text.toString().isEmpty()
                || weaknessesEditText.text.toString().isEmpty()
                || improvementEditText.text.toString().isEmpty()) {
                UIUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                createOrUpdate()
                UIUtil.goToPage("5", this)
            }
        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPage("3", this)
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
        val appreciation = hashMapOf(
            "gain" to gainEditText.text.toString(),
            "weaknesses" to weaknessesEditText.text.toString(),
            "improvement" to improvementEditText.text.toString()
        )

        addValueInDataBase(appreciation, "managerAppreciation")
    }

    private fun updateValueInDB(){

        val appreciation = hashMapOf(
            "gain" to gainEditText.text.toString(),
            "weaknesses" to weaknessesEditText.text.toString(),
            "improvement" to improvementEditText.text.toString()
        )

        updateValueInDataBase(appreciation, "managerAppreciation", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("managerAppreciation")
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
        private const val TAG = "ManagerAppreciation"
    }

}