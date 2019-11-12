package com.astek.asteksupport

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.isManager
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.MailUtil.Companion.sendMail
import com.astek.asteksupport.utils.UIUtil
import com.astek.asteksupport.utils.UIUtil.Companion.backToHome
import com.astek.asteksupport.utils.pdf.PdfUtil.Companion.createPdf
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_synthesis.*
import kotlinx.android.synthetic.main.page_layout.*

class SynthesisActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_synthesis)

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))

        nextArrow.isEnabled = false
        nextArrow.visibility = View.INVISIBLE

        if(isManager) {
            managerEmailEditText.isEnabled = false
            managerEmailEditText.visibility = View.GONE
            resultButton.text = this.getString(R.string.generateReport)
        } else {
            synthesisEditText.isEnabled = false
            synthesisEditText.background = this.getDrawable(R.drawable.round_outline_disabled)
            resultButton.text = this.getString(R.string.notifyManager)
        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPreviousPage(this, this.javaClass.simpleName)
        }

        logout.setOnClickListener{
            backToHome(this)
        }



        resultButton.setOnClickListener {
            if(isManager){
                createOrUpdate()
                createPdf(this)
            } else {
                if(managerEmailEditText.text.toString().isEmpty()){
                    UIUtil.showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    sendMail(this,managerEmailEditText.text.toString())
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        retrieveData()
    }

    private fun createValueInDB(){
        val synthesis = hashMapOf(
            "synthesis" to synthesisEditText.text.toString()
        )

        addValueInDataBase(synthesis, "synthesis")
    }

    private fun updateValueInDB(){

        val synthesis = hashMapOf(
            "synthesis" to synthesisEditText.text.toString()
        )

        updateValueInDataBase(synthesis, "synthesis", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("synthesis")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("synthesis") != null) {
                        synthesisEditText.setText(document.get("synthesis").toString())
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