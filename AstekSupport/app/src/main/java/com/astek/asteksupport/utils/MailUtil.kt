package com.astek.asteksupport.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.astek.asteksupport.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

class MailUtil {

    companion object {

        private const val TAG = "MailUtil"


        fun sendMail(activity: Activity , recipient: String) {
            val emailIntent = Intent(Intent.ACTION_SEND)


            FirebaseFirestore.getInstance().collection("users")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {

                        if(document.get("mail") ==  FirebaseAuth.getInstance().currentUser?.email) {
                            val senderName =  document.get("name").toString()
                            val senderSurname =  document.get("surname").toString()

                            val subject = activity.getString(R.string.mailSubjectEmployee,senderName,senderSurname)
                            val message = activity.getString(R.string.mailTextEmployee,senderName,senderSurname)

                            // set the type to 'email'
                            emailIntent.type = "vnd.android.cursor.dir/email"
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
                            emailIntent.putExtra(Intent.EXTRA_TEXT, message)

                            activity.startActivity(Intent.createChooser(emailIntent, "Send email..."))
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents.", exception)
                }
        }

        fun sendMail2(activity: Activity){
                val filename = "contacts_sid.vcf"
                val filelocation = File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename)
                val path = Uri.fromFile(filelocation)
                val emailIntent = Intent(Intent.ACTION_SEND)
                // set the type to 'email'
                emailIntent.type = "vnd.android.cursor.dir/email"
                val to = arrayOf("asd@gmail.com")
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
                // the attachment
                emailIntent.putExtra(Intent.EXTRA_STREAM, path)
                // the mail subject
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
                activity.startActivity(Intent.createChooser(emailIntent, "Send email..."))

        }
    }

}