package com.astek.asteksupport.evolution

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.astek.asteksupport.utils.UIUtil.Companion.backToHome
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_others_evolution.*
import kotlinx.android.synthetic.main.page_layout.*

class OtherEvolutionActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_evolution)

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))


        nextArrow.setOnClickListener{
            if(mobilityEditText.text.toString().isEmpty()) {
                UIUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                if(othersEvolutionEditText.text.toString().isEmpty()) {
                    othersEvolutionEditText.setText("/")
                }
                if(othersEditText.text.toString().isEmpty()) {
                    othersEditText.setText("/")
                }
                createOrUpdate()
                UIUtil.goToNextPage(this, this.javaClass.simpleName)            }
        }


        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPreviousPage(this, this.javaClass.simpleName)
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
        val othersEvolution = hashMapOf(
            "mobility" to mobilityEditText.text.toString(),
            "othersEvolution" to othersEvolutionEditText.text.toString(),
            "others" to othersEditText.text.toString()
        )

        addValueInDataBase(othersEvolution, "othersEvolution")
    }

    private fun updateValueInDB(){
        val othersEvolution = hashMapOf(
            "mobility" to mobilityEditText.text.toString(),
            "othersEvolution" to othersEvolutionEditText.text.toString(),
            "others" to othersEditText.text.toString()
        )

        updateValueInDataBase(othersEvolution, "othersEvolution", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("othersEvolution")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("mobility") != null) {
                        mobilityEditText.setText(document.get("mobility").toString())
                    }
                    if (document.get("othersEvolution") != null) {
                        othersEvolutionEditText.setText(document.get("othersEvolution").toString())
                    }
                    if (document.get("others") != null) {
                        othersEditText.setText(document.get("others").toString())
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
        private const val TAG = "Synthesis"
    }

}