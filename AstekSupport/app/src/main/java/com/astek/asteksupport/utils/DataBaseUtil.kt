package com.astek.asteksupport.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.View
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.employeeDocumentId
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.isManager
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.managerDocumentId
import com.astek.asteksupport.utils.pdf.FirstPagePdfUtil.Companion.writeInLeftRectancle
import com.astek.asteksupport.utils.pdf.FirstPagePdfUtil.Companion.writeInRightRectancle
import com.astek.asteksupport.utils.UIUtil.Companion.goToPage
import com.astek.asteksupport.utils.UIUtil.Companion.showMessage
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

            if(isManager){
                db.collection("users").document(managerDocumentId)
                    .update(pageValue as Map<String, Any>)
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot updated")
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Error adding document", e)
                    }
            } else {
                db.collection("users").document(employeeDocumentId)
                    .update(pageValue as Map<String, Any>)
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot updated")
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Error adding document", e)
                    }
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
                            employeeDocumentId = document.id
                        }
                    }

                    if(emailAddress != "") {
                        goToPage("1", activity)
                    } else {
                        showMessage(view, activity.getString(R.string.err_no_person))
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }


        fun updateValueInDataBase(valueToUpdate: HashMap<String,String> , collectionToUpdate: String , documentUpdateId: String){
            db.collection("users").document(employeeDocumentId)
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
                .document(employeeDocumentId)
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






        /******************************* FIRST PAGE FOR PDF *************************************/
        @SuppressLint("DefaultLocale")
        fun retrieveLeftRectangleValue() {

            var text: String
            db.collection("users").document(employeeDocumentId)
                .get()
                .addOnSuccessListener { result ->

                    val name = result.get("name").toString()
                    val surname = result.get("surname").toString()
                    val society = result.get("society").toString()
                    val birthDate = result.get("birthdate").toString()
                    val enterDate = result.get("enterDate").toString()
                    val experimentDate = result.get("experimentDate").toString()
                    val function = result.get("function").toString()
                    val diplom = result.get("diplom").toString()
                    val obtentionDate = result.get("obtentionDate").toString()

                    text = "NOM Prénom : $name $surname \n \n"
                    text += "Société : $society \n \n"
                    text += "Date de naissance : $birthDate \n \n"
                    text += "Date d'entrée chez Astek : $enterDate \n \n"
                    text += "Nbre d’Années d’expérience total : $experimentDate \n \n"
                    text += "Fonction : $function \n \n"
                    text += "Diplôme / Ecole : $diplom \n \n"
                    text += "Date d'obtention : $obtentionDate \n \n"

                    writeInLeftRectancle(text,85F,255F)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }


        @SuppressLint("DefaultLocale")
        fun retrieveRightRectangleValue() {
            var text: String

            db.collection("users").document(employeeDocumentId)
                .collection("interviewContext")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val bilanDate = document.get("bilanDate").toString()
                        val previousDate = document.get("previousDate").toString()
                        val interviewDate = document.get("interviewDate").toString()
                        val managerName = document.get("managerName").toString()


                        text = "Année du bilan : $bilanDate \n \n"
                        text += "Date de l’entretien N-1 : $previousDate \n \n"
                        text += "Date de l’entretien N : $interviewDate \n \n"
                        text += "Manager : $managerName \n \n"

                        writeInRightRectancle(text,303F,255F)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }
    }
}