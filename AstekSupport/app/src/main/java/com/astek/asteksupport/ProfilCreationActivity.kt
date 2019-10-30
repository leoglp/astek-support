package com.astek.asteksupport

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.createUser
import com.astek.asteksupport.utils.ShowUtil
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profil_creation.*

class ProfilCreationActivity : AppCompatActivity() {

    private var profilFunction = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_creation)

        profilCreationButton.setOnClickListener{
            if(nameEditText.text.toString().isEmpty()
                || surnameEditText.text.toString().isEmpty()
                || profilFunction.isEmpty()
                || societyEditText.text.toString().isEmpty()
                || birthEditText.text.toString().isEmpty()
                || enterEditText.text.toString().isEmpty()
                || experimentEditText.text.toString().isEmpty()
                || functionEditText.text.toString().isEmpty()
                || diplomEditText.text.toString().isEmpty()
                || obtentionEditText.text.toString().isEmpty()
                || mailCreationEditText.text.toString().isEmpty()) {

                ShowUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                addInfoInProfil()
                createUser(
                    this,
                    it,
                    mailCreationEditText.text.toString(),
                    passwordCreationEditText.text.toString()
                )
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


    private fun addInfoInProfil(){
        // Create a new user with profil informations
        val profilUser = hashMapOf(
            "name" to nameEditText.text.toString(),
            "surname" to surnameEditText.text.toString(),
            "profilFunction" to profilFunction,
            "society" to societyEditText.text.toString(),
            "birthdate" to birthEditText.text.toString(),
            "enterDate" to enterEditText.text.toString(),
            "experimentDate" to experimentEditText.text.toString(),
            "function" to functionEditText.text.toString(),
            "diplom" to diplomEditText.text.toString(),
            "obtentionDate" to obtentionEditText.text.toString(),
            "mail" to mailCreationEditText.text.toString()
        )


        // Add a new document with a generated ID
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .add(profilUser)
            .addOnSuccessListener { documentReference ->
                Log.d("TITI", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.d("TITI", "Error adding document", e)
            }

    }


    private fun checkValueOfRadioButton(){
        profilFunction = if(managerRadioButton.isChecked) {
            "manager"
        } else {
            "salarie"
        }
    }
}