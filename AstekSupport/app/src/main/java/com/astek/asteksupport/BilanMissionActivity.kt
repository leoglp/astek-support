package com.astek.asteksupport

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.astek.asteksupport.utils.UIUtil.Companion.backToHome
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_bilan_mission.*
import kotlinx.android.synthetic.main.page_layout.*

class BilanMissionActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilan_mission)

        pageNumber.text = this.getString(R.string.pageNumber,"2","5")

        nextArrow.setOnClickListener{
            if(explanationEditText.text.toString().isEmpty()) {
                UIUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                createOrUpdate()
                UIUtil.goToPage("3", this)
            }
        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPage("1", this)
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
        val bilanMission = hashMapOf(
            "bilanMission" to explanationEditText.text.toString()
        )

        addValueInDataBase(bilanMission, "bilanMission")
    }

    private fun updateValueInDB(){

        val bilanMission = hashMapOf(
            "bilanMission" to explanationEditText.text.toString()
        )

        updateValueInDataBase(bilanMission, "bilanMission", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("bilanMission")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("bilanMission") != null) {
                        explanationEditText.setText(document.get("bilanMission").toString())
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
        private const val TAG = "BilanMission"
    }

}