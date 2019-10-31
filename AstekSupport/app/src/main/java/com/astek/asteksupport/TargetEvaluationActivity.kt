package com.astek.asteksupport

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.isManager
import com.astek.asteksupport.utils.DataBaseUtil.Companion.addValueInDataBase
import com.astek.asteksupport.utils.DataBaseUtil.Companion.updateValueInDataBase
import com.astek.asteksupport.utils.UIUtil
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.page_add_layout.*
import kotlinx.android.synthetic.main.target_layout.*

class TargetEvaluationActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""
    private var numberTarget = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target_evaluation)

        pageNumber.text = this.getString(R.string.pageNumber,"5","5")

        if(!isManager) {
            target1EditText.isEnabled = false
            target1EditText.background = this.getDrawable(R.drawable.round_outline_disabled)
            result1EditText.isEnabled = false
            result1EditText.background = this.getDrawable(R.drawable.round_outline_disabled)
            target2EditText.isEnabled = false
            target2EditText.background = this.getDrawable(R.drawable.round_outline_disabled)
            result2EditText.isEnabled = false
            result2EditText.background = this.getDrawable(R.drawable.round_outline_disabled)
            target3EditText.isEnabled = false
            target3EditText.background = this.getDrawable(R.drawable.round_outline_disabled)
            result3EditText.isEnabled = false
            result3EditText.background = this.getDrawable(R.drawable.round_outline_disabled)
            addTarget.visibility = View.GONE
        }


        nextArrow.setOnClickListener{

            if(isManager) {
                when (numberTarget) {
                    1 -> if(target1EditText.text.toString().isEmpty() || result1EditText.text.toString().isEmpty()) {
                        UIUtil.showMessage(it, this.getString(R.string.err_no_input))
                    } else {
                        createOrUpdate()
                        UIUtil.goToPage("6", this)
                    }
                    2 -> if(target1EditText.text.toString().isEmpty() || result1EditText.text.toString().isEmpty()
                        || target2EditText.text.toString().isEmpty() || result2EditText.text.toString().isEmpty()) {
                        UIUtil.showMessage(it, this.getString(R.string.err_no_input))
                    } else {
                        createOrUpdate()
                        UIUtil.goToPage("6", this)
                    }
                    3 -> if(target1EditText.text.toString().isEmpty() || result1EditText.text.toString().isEmpty()
                        || target2EditText.text.toString().isEmpty() || result2EditText.text.toString().isEmpty()
                        || target3EditText.text.toString().isEmpty() || result3EditText.text.toString().isEmpty()) {
                        UIUtil.showMessage(it, this.getString(R.string.err_no_input))
                    } else {
                        createOrUpdate()
                        UIUtil.goToPage("6", this)
                    }
                }
            } else {
                UIUtil.goToPage("6", this)
            }

        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPage("4", this)
        }

        logout.setOnClickListener{
            UIUtil.backToHome(this)
        }


        addTarget.setOnClickListener {
            updateTargetView(true)
        }

        deleteTarget.setOnClickListener {
            updateTargetView(false)
        }


    }

    override fun onResume() {
        super.onResume()
        retrieveData()
    }

    private fun createValueInDB(){

        var targetEvaluation : HashMap<String,String>? = null

        when (numberTarget) {
            1 -> targetEvaluation = hashMapOf(
                "target1" to target1EditText.text.toString(),
                "result1" to result1EditText.text.toString(),
                "numberTarget" to numberTarget.toString())
            2 -> targetEvaluation = hashMapOf(
                "target1" to target1EditText.text.toString(),
                "result1" to result1EditText.text.toString(),
                "target2" to target2EditText.text.toString(),
                "result2" to result2EditText.text.toString(),
                "numberTarget" to numberTarget.toString())
            3 -> targetEvaluation = hashMapOf(
                "target1" to target1EditText.text.toString(),
                "result1" to result1EditText.text.toString(),
                "target2" to target2EditText.text.toString(),
                "result2" to result2EditText.text.toString(),
                "target3" to target3EditText.text.toString(),
                "numberTarget" to numberTarget.toString(),
                "result3" to result3EditText.text.toString())
        }


        addValueInDataBase(targetEvaluation!!, "targetEvaluation")
    }

    private fun updateValueInDB(){

        var targetEvaluation : HashMap<String,String>? = null

        when (numberTarget) {
            1 -> targetEvaluation = hashMapOf(
                "target1" to target1EditText.text.toString(),
                "result1" to result1EditText.text.toString(),
                "numberTarget" to numberTarget.toString())
            2 -> targetEvaluation = hashMapOf(
                "target1" to target1EditText.text.toString(),
                "result1" to result1EditText.text.toString(),
                "target2" to target2EditText.text.toString(),
                "result2" to result2EditText.text.toString(),
                "numberTarget" to numberTarget.toString())
            3 -> targetEvaluation = hashMapOf(
                "target1" to target1EditText.text.toString(),
                "result1" to result1EditText.text.toString(),
                "target2" to target2EditText.text.toString(),
                "result2" to result2EditText.text.toString(),
                "target3" to target3EditText.text.toString(),
                "result3" to result3EditText.text.toString(),
                "numberTarget" to numberTarget.toString())
        }

        updateValueInDataBase(targetEvaluation!!, "targetEvaluation", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("targetEvaluation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("numberTarget") != null) {
                        numberTarget = Integer.valueOf(document.get("numberTarget").toString())
                    }

                    if(numberTarget == 1) {
                        if (document.get("target1") != null) {
                            target1EditText.setText(document.get("target1").toString())
                        }
                        if (document.get("result1") != null) {
                            result1EditText.setText(document.get("result1").toString())
                        }
                    } else if (numberTarget == 2){
                        if (document.get("target1") != null) {
                            target1EditText.setText(document.get("target1").toString())
                        }
                        if (document.get("result1") != null) {
                            result1EditText.setText(document.get("result1").toString())
                        }
                        if (document.get("target2") != null) {
                            target2EditText.setText(document.get("target2").toString())
                        }
                        if (document.get("result2") != null) {
                            result2EditText.setText(document.get("result2").toString())
                        }
                    } else if (numberTarget == 3){
                        if (document.get("target1") != null) {
                            target1EditText.setText(document.get("target1").toString())
                        }
                        if (document.get("result1") != null) {
                            result1EditText.setText(document.get("result1").toString())
                        }
                        if (document.get("target2") != null) {
                            target2EditText.setText(document.get("target2").toString())
                        }
                        if (document.get("result2") != null) {
                            result2EditText.setText(document.get("result2").toString())
                        }
                        if (document.get("target3") != null) {
                            target3EditText.setText(document.get("target3").toString())
                        }
                        if (document.get("result3") != null) {
                            result3EditText.setText(document.get("result3").toString())
                        }
                    }
                    updateTargetView()
                    updateValue = true
                    documentUpdateId = document.id
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents.", exception)
            }
    }

    private fun createOrUpdate(){
        if(updateValue){
            updateValueInDB()
        } else {
            createValueInDB()
        }
    }



    private fun updateTargetView() {

        if(numberTarget == 2) {
            if(!isManager) {
                addTarget.visibility = View.GONE
                deleteTarget.visibility = View.GONE
            } else {
                addTarget.visibility = View.VISIBLE
                deleteTarget.visibility = View.VISIBLE
            }
            target2.text = this.getString(R.string.targetDynamic,"2")
            target2.visibility = View.VISIBLE
            target2EditText.visibility = View.VISIBLE
            result2.visibility = View.VISIBLE
            result2EditText.visibility = View.VISIBLE
        }
        if(numberTarget == 3) {
            if(!isManager) {
                addTarget.visibility = View.GONE
            } else {
                addTarget.visibility = View.VISIBLE
            }
            deleteTarget.visibility = View.GONE
            target2.visibility = View.VISIBLE
            target2EditText.visibility = View.VISIBLE
            result2.visibility = View.VISIBLE
            result2EditText.visibility = View.VISIBLE
            target3.visibility = View.VISIBLE
            target3EditText.visibility = View.VISIBLE
            result3.visibility = View.VISIBLE
            result3EditText.visibility = View.VISIBLE
            target2.text = this.getString(R.string.targetDynamic,"2")
            target3.text = this.getString(R.string.targetDynamic,"3")
        }
    }

    private fun updateTargetView(isAdded: Boolean) {
        if(isAdded){
            numberTarget++
            if(numberTarget == 2) {
                deleteTarget.visibility = View.VISIBLE

                target2.visibility = View.VISIBLE
                target2EditText.visibility = View.VISIBLE
                result2.visibility = View.VISIBLE
                result2EditText.visibility = View.VISIBLE
                target2.text = this.getString(R.string.targetDynamic,"2")
            }
            if(numberTarget == 3) {
                target3.text = this.getString(R.string.targetDynamic,"3")
                addTarget.visibility = View.GONE
                target3.visibility = View.VISIBLE
                target3EditText.visibility = View.VISIBLE
                result3.visibility = View.VISIBLE
                result3EditText.visibility = View.VISIBLE
            }
        } else {
            numberTarget--
            if(numberTarget == 1) {
                deleteTarget.visibility = View.GONE
                target2.visibility = View.GONE
                target2EditText.visibility = View.GONE
                result2.visibility = View.GONE
                result2EditText.visibility = View.GONE
            }
            if(numberTarget == 2) {
                addTarget.visibility = View.VISIBLE
                target3.visibility = View.GONE
                target3EditText.visibility = View.GONE
                result3.visibility = View.GONE
                result3EditText.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val TAG = "TargetEvaluation"
    }

}