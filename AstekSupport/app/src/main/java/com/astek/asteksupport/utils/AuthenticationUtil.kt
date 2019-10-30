package com.astek.asteksupport.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import com.astek.asteksupport.MainActivity
import com.astek.asteksupport.ManagerActivity
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.DataBaseUtil.Companion.readAndGoToPage
import com.astek.asteksupport.utils.UIUtil.Companion.showMessage
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthenticationUtil {

    companion object {

        private const val TAG = "AuthenticationUtil"

        var isManager : Boolean = false
        var documentId: String = ""


        private val fbAuth = FirebaseAuth.getInstance()

        fun signIn(activity: Activity, view: View, email: String, password: String){
            showMessage(view,activity.getString(R.string.authentication))

            fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
                if(task.isSuccessful) {
                    val db = FirebaseFirestore.getInstance()

                    db.collection("users")
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {

                                if(document.get("mail") == fbAuth.currentUser?.email) {
                                    if(document.get("profilFunction") == "manager"){
                                        isManager = true
                                        val intent = Intent(activity, ManagerActivity::class.java)
                                        activity.startActivity(intent)
                                    } else {
                                        documentId = document.id
                                        isManager = false
                                        readAndGoToPage(activity)
                                    }
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            android.util.Log.d(TAG, "Error getting documents.", exception)
                        }

                } else {
                    showMessage(view,"Error: ${task.exception?.message}")
                }
            }

        }





        fun createUser(activity: Activity, view: View, email: String, password: String){

            showMessage(view,activity.getString(R.string.creation))

            fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    val intent = Intent(activity, MainActivity::class.java)
                    activity.startActivity(intent)
                } else {
                    showMessage(view,"Error: ${task.exception?.message}")
                }
            }
        }


    }
}