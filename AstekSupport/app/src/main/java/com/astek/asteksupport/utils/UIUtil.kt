package com.astek.asteksupport.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.astek.asteksupport.*
import com.astek.asteksupport.evolution.LongEvolutionActivity
import com.astek.asteksupport.evolution.MediumEvolutionActivity
import com.astek.asteksupport.evolution.OtherEvolutionActivity
import com.astek.asteksupport.evolution.ShortEvolutionActivity
import com.astek.asteksupport.skill.*
import com.astek.asteksupport.skill.behavioural.*
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updatePageValue
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class UIUtil {

    companion object {

        fun showMessage(view:View, message: String){
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }

        fun showMessageIndefinite(view:View, message: String){
            Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()
        }

        fun showMessageShort(view:View, message: String){
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).setAction("Action", null).show()
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
                "6" -> intent = Intent(activity,PerformanceEvaluationActivity::class.java)
                "7" -> intent = Intent(activity,TechnicalSkillActivity::class.java)
                "8" -> intent = Intent(activity,ProfessionSkillActivity::class.java)
                "9" -> intent = Intent(activity,FunctionalSkillActivity::class.java)
                "10" -> intent = Intent(activity,ManagerialSkillActivity::class.java)
                "11" -> intent = Intent(activity,AutonomySkillActivity::class.java)
                "12" -> intent = Intent(activity,AdaptabilitySkillActivity::class.java)
                "13" -> intent = Intent(activity,TeamWorkSkillActivity::class.java)
                "14" -> intent = Intent(activity,CreativitySkillActivity::class.java)
                "15" -> intent = Intent(activity,CommunicationSkillActivity::class.java)
                "16" -> intent = Intent(activity,ImplicationSkillActivity::class.java)
                "17" -> intent = Intent(activity,RespectSkillActivity::class.java)
                "18" -> intent = Intent(activity,RigourSkillActivity::class.java)
                "19" -> intent = Intent(activity,EnglishSkillActivity::class.java)
                "20" -> intent = Intent(activity,OthersSkillActivity::class.java)
                "21" -> intent = Intent(activity,SkillEvaluationActivity::class.java)
                "22" -> intent = Intent(activity,FutureTargetEvaluationActivity::class.java)
                "23" -> intent = Intent(activity,ShortEvolutionActivity::class.java)
                "24" -> intent = Intent(activity,MediumEvolutionActivity::class.java)
                "25" -> intent = Intent(activity,LongEvolutionActivity::class.java)
                "26" -> intent = Intent(activity,OtherEvolutionActivity::class.java)
                "27" -> intent = Intent(activity,BilanFormationActivity::class.java)
                "28" -> intent = Intent(activity,WishFormationActivity::class.java)
                "29" -> intent = Intent(activity,CPFActivity::class.java)
                "30" -> intent = Intent(activity,SynthesisActivity::class.java)
            }
            activity.startActivity(intent)
        }

        fun backToHome(activity: Activity) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity,MainActivity::class.java)
            activity.startActivity(intent)
        }

        fun getPage(activity: Activity , className: String): Int {
            val classArray = activity.resources.getStringArray(R.array.classArray)
            return (classArray.indexOf(className) + 1)
        }

        fun goToPreviousPage(activity: Activity , className: String) {
            val classArray = activity.resources.getStringArray(R.array.classArray)
            goToPage((classArray.indexOf(className)).toString(),activity)
        }

        fun goToNextPage(activity: Activity , className: String){
            val classArray = activity.resources.getStringArray(R.array.classArray)
            goToPage((classArray.indexOf(className) + 2).toString(),activity)
        }

        fun getTotalPage(activity: Activity): String {
            val classArray = activity.resources.getStringArray(R.array.classArray)
            return classArray.size.toString()
        }

        fun updateProfilInfo(activity: Activity) {
            val intent = Intent(activity,ProfilModificationActivity::class.java)
            activity.startActivity(intent)
        }


    }
}