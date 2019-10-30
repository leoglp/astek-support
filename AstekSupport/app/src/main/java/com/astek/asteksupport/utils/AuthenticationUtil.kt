package com.astek.asteksupport.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import com.astek.asteksupport.InterviewContextActivity
import com.astek.asteksupport.MainActivity
import com.astek.asteksupport.ManagerActivity
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.ShowUtil.Companion.showMessage
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthenticationUtil {

    companion object {

        var isManager : Boolean = false
        var documentId: String = ""


        private val fbAuth = FirebaseAuth.getInstance()

        fun signIn(activity: Activity, view: View, email: String, password: String){
            showMessage(view,activity.getString(R.string.authentication))

            fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
                if(task.isSuccessful) {
                    val db = FirebaseFirestore.getInstance()
                    var intent : Intent?

                    db.collection("users")
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {

                                if(document.get("mail") == fbAuth.currentUser?.email) {
                                    if(document.get("profilFunction") == "manager"){
                                        isManager = true
                                        intent = Intent(activity, ManagerActivity::class.java)
                                    } else {
                                        documentId = document.id
                                        isManager = false
                                        intent = Intent(activity, InterviewContextActivity::class.java)
                                    }
                                    activity.startActivity(intent)
                                }
                            }
                        }
                        .addOnFailureListener { exception ->
                            android.util.Log.d("TITI", "Error getting documents.", exception)
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