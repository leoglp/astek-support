package com.astek.asteksupport.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import com.astek.asteksupport.InterviewContextActivity
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.documentId
import com.astek.asteksupport.utils.UIUtil.Companion.goToPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DataBaseUtil {

    companion object {

        private const val TAG = "DataBaseUtil"

        @SuppressLint("StaticFieldLeak")
        private val db = FirebaseFirestore.getInstance()

        fun updatePageValue(pageNumber: String) {

            val pageValue = hashMapOf(
                "page" to pageNumber
            )

            db.collection("users").document(documentId)
                .update(pageValue as Map<String, Any>)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot updated")
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "Error adding document", e)
                }
        }

        fun readAndGoToPage(activity: Activity) {
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if(document.get("mail") == FirebaseAuth.getInstance().currentUser?.email) {
                            val pageNumber = document.get("page").toString()
                            goToPage(pageNumber,activity)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }


        @SuppressLint("DefaultLocale")
        fun retrieveMailAddress(nameEditInsensitive: String , surnameEditInsensitive: String ,
                                view: View , activity: Activity) {

            var emailAddress = ""
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val nameInsensitive = document.get("name").toString().toLowerCase()
                        val surnameInsensitive = document.get("surname").toString().toLowerCase()

                        if(nameInsensitive == nameEditInsensitive
                            && surnameInsensitive == surnameEditInsensitive) {
                            emailAddress = document.get("mail").toString()
                            documentId = document.id
                        }
                    }

                    if(emailAddress != "") {
                        val intent = Intent(activity, InterviewContextActivity::class.java)
                        activity.startActivity(intent)
                    } else {
                        UIUtil.showMessage(view, activity.getString(R.string.err_no_person))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }


        fun updateValueInDataBase(valueToUpdate: HashMap<String,String> , collectionToUpdate: String , documentUpdateId: String){
            db.collection("users").document(documentId)
                .collection(collectionToUpdate).document(documentUpdateId)
                .update(valueToUpdate as Map<String, Any>)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot updated")
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "Error adding document", e)
                }
        }

        fun addValueInDataBase(valueToAdd: HashMap<String,String> , collectionToCreate: String){
            db.collection("users")
                .document(documentId)
                .collection(collectionToCreate)
                .add(valueToAdd)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "Error adding document", e)
                }
        }

        fun createProfil(valueToAdd: HashMap<String,String>){
            db.collection("users")
                .add(valueToAdd)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "Error adding document", e)
                }
        }
    }
}