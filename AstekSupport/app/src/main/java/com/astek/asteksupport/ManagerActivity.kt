package com.astek.asteksupport

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.ShowUtil
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_manager.*

class ManagerActivity : AppCompatActivity() {

    var emailAdress = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        validateButton.setOnClickListener{
            if(nameEditText.text.toString().isEmpty()
                || surnameEditText.text.toString().isEmpty()) {
                ShowUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                retrieveMailAdress(it)
            }
        }
    }

    private fun retrieveMailAdress(view: View){
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document.get("name") == nameEditText.text.toString()
                        && document.get("surname") == surnameEditText.text.toString()) {
                        emailAdress = document.get("mail").toString()
                        AuthenticationUtil.documentId = document.id
                    }
                }

                if(emailAdress != "") {
                    val intent = Intent(this, InterviewContextActivity::class.java)
                    startActivity(intent)
                } else {
                    ShowUtil.showMessage(view, this.getString(R.string.err_no_person))
                }
            }
            .addOnFailureListener { exception ->
                android.util.Log.d("TITI", "Error getting documents.", exception)
            }
    }
}