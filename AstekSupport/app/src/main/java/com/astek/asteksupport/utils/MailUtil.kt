package com.astek.asteksupport.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import com.astek.asteksupport.R
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.employeeName
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.employeeSurname
import java.io.File
import android.os.StrictMode
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.employeeMail
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.managerName
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.managerSurname


@Suppress("DEPRECATION")
class MailUtil {

    companion object {

        private const val TAG = "MailUtil"


        fun sendMail(activity: Activity , recipient: String) {
            val emailIntent = Intent(Intent.ACTION_SEND)

            val subject = activity.getString(R.string.mailSubjectEmployee,employeeName,employeeSurname)
            val message = activity.getString(R.string.mailTextEmployee,employeeName,employeeSurname)

            // set the type to 'email'
            emailIntent.type = "vnd.android.cursor.dir/email"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, message)

            activity.startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }



        fun sendMailWithPdf(activity: Activity) {
            val emailIntent = Intent(Intent.ACTION_SEND)

            val subject = activity.getString(R.string.mailSubjectEmployee,employeeName,employeeSurname)
            val message = activity.getString(R.string.mailTextManager, managerName, managerSurname)

            // set the type to 'email'
            emailIntent.type = "vnd.android.cursor.dir/email"
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, message)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(employeeMail))

            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())

            val fileName = employeeName + "_" + employeeSurname + ".pdf"
            val directoryPath = Environment.getExternalStorageDirectory().path + "/astek_support_pdf/"
            val fileLocation = File(directoryPath, fileName)
            val path = Uri.fromFile(fileLocation)
            emailIntent .putExtra(Intent.EXTRA_STREAM, path)

            activity.startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }

    }

}