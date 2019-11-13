package com.astek.asteksupport

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.employeeDocumentId
import com.astek.asteksupport.utils.DataBaseUtil.Companion.readAndGoToPage
import com.astek.asteksupport.utils.UIUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profil_creation.*

class ProfilModificationActivity : AppCompatActivity() {

    private var profilFunction = ""
    private var documentUpdateId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_modification)

        profilCreationButton.setOnClickListener{
            if(nameEditText.text.toString().isEmpty()
                || surnameEditText.text.toString().isEmpty()
                || societyEditText.text.toString().isEmpty()
                || birthEditText.text.toString().isEmpty()
                || enterEditText.text.toString().isEmpty()
                || experimentEditText.text.toString().isEmpty()
                || functionEditText.text.toString().isEmpty()
                || diplomEditText.text.toString().isEmpty()
                || obtentionEditText.text.toString().isEmpty()) {
                UIUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                updateValueInDB()
                readAndGoToPage(this)
            }
        }

        // Get radio group selected item using on checked change listener
        whoRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            profilFunction = if(checkedId == R.id.managerRadioButton) {
                "manager"
            } else {
                "salarie"
            }
        }
        checkValueOfRadioButton()
    }

    override fun onResume() {
        super.onResume()
        retrieveData()
    }

    private fun checkValueOfRadioButton(){
        profilFunction = if(managerRadioButton.isChecked) {
            "manager"
        } else {
            "salarie"
        }
    }

    private fun setValueOfRadioButton(value: String){
        if(value == "manager") {
            managerRadioButton.isChecked = true
        } else {
            salarieRadioButton.isChecked = true
        }
    }

    private fun updateValueInDB(){

        val profilModification = hashMapOf(
            "name" to nameEditText.text.toString(),
            "surname" to surnameEditText.text.toString(),
            "profilFunction" to profilFunction,
            "society" to societyEditText.text.toString(),
            "birthdate" to birthEditText.text.toString(),
            "enterDate" to enterEditText.text.toString(),
            "experimentDate" to experimentEditText.text.toString(),
            "function" to functionEditText.text.toString(),
            "diplom" to diplomEditText.text.toString(),
            "obtentionDate" to obtentionEditText.text.toString()
        )

        FirebaseFirestore.getInstance().collection("users").document(documentUpdateId)
            .update(profilModification as Map<String, Any>)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot updated")
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Error adding document", e)
            }
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document.get("mail") == FirebaseAuth.getInstance().currentUser?.email) {
                        nameEditText.setText(document.get("name").toString())
                        surnameEditText.setText(document.get("surname").toString())
                        setValueOfRadioButton(document.get("profilFunction").toString())
                        societyEditText.setText(document.get("society").toString())
                        birthEditText.setText(document.get("birthdate").toString())
                        enterEditText.setText(document.get("enterDate").toString())
                        experimentEditText.setText(document.get("experimentDate").toString())
                        functionEditText.setText(document.get("function").toString())
                        diplomEditText.setText(document.get("diplom").toString())
                        obtentionEditText.setText(document.get("obtentionDate").toString())
                        documentUpdateId = document.id
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents.", exception)
            }
    }

    companion object {
        private const val TAG = "ProfilModification"
    }
}