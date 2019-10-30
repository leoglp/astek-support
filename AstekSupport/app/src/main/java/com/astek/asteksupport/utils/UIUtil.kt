package com.astek.asteksupport.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.astek.asteksupport.*
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updatePageValue
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class UIUtil {

    companion object {

        fun showMessage(view:View, message: String){
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun goToPage(pageNumber: String , activity: Activity) {
            updatePageValue(pageNumber)
            var intent: Intent? = null
            when(pageNumber){
                "-1" -> intent = Intent(activity,ManagerActivity::class.java)
                "0" -> intent = Intent(activity,MainActivity::class.java)
                "1" -> intent = Intent(activity,InterviewContextActivity::class.java)
                "2" -> intent = Intent(activity,BilanMissionActivity::class.java)
                "3" -> intent = Intent(activity,EmployeeAppreciationActivity::class.java)
                "4" -> intent = Intent(activity,ManagerAppreciationActivity::class.java)
                "5" -> intent = Intent(activity,TargetEvaluationActivity::class.java)
                "6" -> intent = Intent(activity,InterviewContextActivity::class.java)
            }
            activity.startActivity(intent)
        }

        fun backToHome(activity: Activity) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity,MainActivity::class.java)
            activity.startActivity(intent)
        }


    }
}