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
import com.astek.asteksupport.utils.UIUtil.Companion.backToHome
import com.astek.asteksupport.utils.UIUtil.Companion.showMessage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_wish_formation.*
import kotlinx.android.synthetic.main.page_add_layout.*
import kotlinx.android.synthetic.main.page_add_layout.backArrow
import kotlinx.android.synthetic.main.page_add_layout.logout
import kotlinx.android.synthetic.main.page_add_layout.nextArrow
import kotlinx.android.synthetic.main.page_add_layout.pageNumber
import kotlinx.android.synthetic.main.page_layout.*

class WishFormationActivity : AppCompatActivity() {

    private var updateValue = false
    private var documentUpdateId = ""
    private var numberTarget = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wish_formation)

        pageNumber.text = this.getString(R.string.pageNumber, UIUtil.getPage(this, this.javaClass.simpleName).toString(),
            UIUtil.getTotalPage(this))
        

        if(!isManager) {
            targetFormationEditText1.isEnabled = false
            targetFormationEditText1.background = this.getDrawable(R.drawable.round_outline_disabled)
            modalityEditText1.isEnabled = false
            modalityEditText1.background = this.getDrawable(R.drawable.round_outline_disabled)
            targetFormationEditText2.isEnabled = false
            targetFormationEditText2.background = this.getDrawable(R.drawable.round_outline_disabled)
            modalityEditText2.isEnabled = false
            modalityEditText2.background = this.getDrawable(R.drawable.round_outline_disabled)
            targetFormationEditText3.isEnabled = false
            targetFormationEditText3.background = this.getDrawable(R.drawable.round_outline_disabled)
            modalityEditText3.isEnabled = false
            modalityEditText3.background = this.getDrawable(R.drawable.round_outline_disabled)
        }

        nextArrow.setOnClickListener{
            checkField(numberTarget,it)
        }

        backArrow.setOnClickListener{
            createOrUpdate()
            UIUtil.goToPreviousPage(this, this.javaClass.simpleName)
        }

        addTarget.setOnClickListener {
            updateTargetView(true)
        }

        deleteTarget.setOnClickListener {
            updateTargetView(false)
        }

        logout.setOnClickListener{
            backToHome(this)
        }
    }


    override fun onResume() {
        super.onResume()
        retrieveData()
    }

    private fun createValueInDB(){
        var wishFormation : HashMap<String,String>? = null

        when (numberTarget) {
            1 -> wishFormation = hashMapOf(
                "wish1" to wishEditText1.text.toString(),
                "target1" to targetFormationEditText1.text.toString(),
                "motivation1" to motivationEditText1.text.toString(),
                "modality1" to modalityEditText1.text.toString(),

                "numberTarget" to numberTarget.toString())
            2 -> wishFormation = hashMapOf(
                "wish1" to wishEditText1.text.toString(),
                "target1" to targetFormationEditText1.text.toString(),
                "motivation1" to motivationEditText1.text.toString(),
                "modality1" to modalityEditText1.text.toString(),

                "wish2" to wishEditText2.text.toString(),
                "target2" to targetFormationEditText2.text.toString(),
                "motivation2" to motivationEditText2.text.toString(),
                "modality2" to modalityEditText2.text.toString(),

                "numberTarget" to numberTarget.toString())

            3 -> wishFormation = hashMapOf(
                "wish1" to wishEditText1.text.toString(),
                "target1" to targetFormationEditText1.text.toString(),
                "motivation1" to motivationEditText1.text.toString(),
                "modality1" to modalityEditText1.text.toString(),

                "wish2" to wishEditText2.text.toString(),
                "target2" to targetFormationEditText2.text.toString(),
                "motivation2" to motivationEditText2.text.toString(),
                "modality2" to modalityEditText2.text.toString(),

                "wish3" to wishEditText3.text.toString(),
                "target3" to targetFormationEditText3.text.toString(),
                "motivation3" to motivationEditText3.text.toString(),
                "modality3" to modalityEditText3.text.toString(),

                "numberTarget" to numberTarget.toString())
        }


        addValueInDataBase(wishFormation!!, "wishFormation")
    }

    private fun updateValueInDB(){

        var wishFormation : HashMap<String,String>? = null

        when (numberTarget) {
            1 -> wishFormation = hashMapOf(
                "wish1" to wishEditText1.text.toString(),
                "target1" to targetFormationEditText1.text.toString(),
                "motivation1" to motivationEditText1.text.toString(),
                "modality1" to modalityEditText1.text.toString(),

                "numberTarget" to numberTarget.toString())
            2 -> wishFormation = hashMapOf(
                "wish1" to wishEditText1.text.toString(),
                "target1" to targetFormationEditText1.text.toString(),
                "motivation1" to motivationEditText1.text.toString(),
                "modality1" to modalityEditText1.text.toString(),

                "wish2" to wishEditText2.text.toString(),
                "target2" to targetFormationEditText2.text.toString(),
                "motivation2" to motivationEditText2.text.toString(),
                "modality2" to modalityEditText2.text.toString(),

                "numberTarget" to numberTarget.toString())

            3 -> wishFormation = hashMapOf(
                "wish1" to wishEditText1.text.toString(),
                "target1" to targetFormationEditText1.text.toString(),
                "motivation1" to motivationEditText1.text.toString(),
                "modality1" to modalityEditText1.text.toString(),

                "wish2" to wishEditText2.text.toString(),
                "target2" to targetFormationEditText2.text.toString(),
                "motivation2" to motivationEditText2.text.toString(),
                "modality2" to modalityEditText2.text.toString(),

                "wish3" to wishEditText3.text.toString(),
                "target3" to targetFormationEditText3.text.toString(),
                "motivation3" to motivationEditText3.text.toString(),
                "modality3" to modalityEditText3.text.toString(),

                "numberTarget" to numberTarget.toString())
        }

        updateValueInDataBase(wishFormation!!, "wishFormation", documentUpdateId)
    }

    private fun retrieveData(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(AuthenticationUtil.employeeDocumentId)
            .collection("wishFormation")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get("numberTarget") != null) {
                        numberTarget = Integer.valueOf(document.get("numberTarget").toString())
                    }

                    if(numberTarget == 1) {
                        if (document.get("wish1") != null) {
                            wishEditText1.setText(document.get("wish1").toString())
                        }
                        if (document.get("target1") != null) {
                            targetFormationEditText1.setText(document.get("target1").toString())
                        }
                        if (document.get("motivation1") != null) {
                            motivationEditText1.setText(document.get("motivation1").toString())
                        }
                        if (document.get("modality1") != null) {
                            modalityEditText1.setText(document.get("modality1").toString())
                        }
                    }

                    else if (numberTarget == 2){
                        if (document.get("wish1") != null) {
                            wishEditText1.setText(document.get("wish1").toString())
                        }
                        if (document.get("target1") != null) {
                            targetFormationEditText1.setText(document.get("target1").toString())
                        }
                        if (document.get("motivation1") != null) {
                            motivationEditText1.setText(document.get("motivation1").toString())
                        }
                        if (document.get("modality1") != null) {
                            modalityEditText1.setText(document.get("modality1").toString())
                        }

                        if (document.get("wish2") != null) {
                            wishEditText2.setText(document.get("wish2").toString())
                        }
                        if (document.get("target2") != null) {
                            targetFormationEditText2.setText(document.get("target2").toString())
                        }
                        if (document.get("motivation2") != null) {
                            motivationEditText2.setText(document.get("motivation2").toString())
                        }
                        if (document.get("modality2") != null) {
                            modalityEditText2.setText(document.get("modality2").toString())
                        }
                    }

                    else if (numberTarget == 3){
                        if (document.get("wish1") != null) {
                            wishEditText1.setText(document.get("wish1").toString())
                        }
                        if (document.get("target1") != null) {
                            targetFormationEditText1.setText(document.get("target1").toString())
                        }
                        if (document.get("motivation1") != null) {
                            motivationEditText1.setText(document.get("motivation1").toString())
                        }
                        if (document.get("modality1") != null) {
                            modalityEditText1.setText(document.get("modality1").toString())
                        }

                        if (document.get("wish2") != null) {
                            wishEditText2.setText(document.get("wish2").toString())
                        }
                        if (document.get("target2") != null) {
                            targetFormationEditText2.setText(document.get("target2").toString())
                        }
                        if (document.get("motivation2") != null) {
                            motivationEditText2.setText(document.get("motivation2").toString())
                        }
                        if (document.get("modality2") != null) {
                            modalityEditText2.setText(document.get("modality2").toString())
                        }

                        if (document.get("wish3") != null) {
                            wishEditText3.setText(document.get("wish3").toString())
                        }
                        if (document.get("target3") != null) {
                            targetFormationEditText3.setText(document.get("target3").toString())
                        }
                        if (document.get("motivation3") != null) {
                            motivationEditText3.setText(document.get("motivation3").toString())
                        }
                        if (document.get("modality3") != null) {
                            modalityEditText3.setText(document.get("modality3").toString())
                        }
                    }
                    updateValue = true
                    documentUpdateId = document.id
                    updateTargetView()
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
            deleteTarget.visibility = View.VISIBLE

            wish2.visibility = View.VISIBLE
            wishEditText2.visibility = View.VISIBLE
            targetFormation2.visibility = View.VISIBLE
            targetFormationEditText2.visibility = View.VISIBLE
            motivation2.visibility = View.VISIBLE
            motivationEditText2.visibility = View.VISIBLE
            modality2.visibility = View.VISIBLE
            modalityEditText2.visibility = View.VISIBLE
        }
        if(numberTarget == 3) {
            addTarget.visibility = View.GONE

            wish2.visibility = View.VISIBLE
            wishEditText2.visibility = View.VISIBLE
            targetFormation2.visibility = View.VISIBLE
            targetFormationEditText2.visibility = View.VISIBLE
            motivation2.visibility = View.VISIBLE
            motivationEditText2.visibility = View.VISIBLE
            modality2.visibility = View.VISIBLE
            modalityEditText2.visibility = View.VISIBLE

            wish3.visibility = View.VISIBLE
            wishEditText3.visibility = View.VISIBLE
            targetFormation3.visibility = View.VISIBLE
            targetFormationEditText3.visibility = View.VISIBLE
            motivation3.visibility = View.VISIBLE
            motivationEditText3.visibility = View.VISIBLE
            modality3.visibility = View.VISIBLE
            modalityEditText3.visibility = View.VISIBLE
        }
    }

    private fun updateTargetView(isAdded: Boolean) {
        if(isAdded){
            numberTarget++
            if(numberTarget == 2) {
                deleteTarget.visibility = View.VISIBLE

                wish2.visibility = View.VISIBLE
                wishEditText2.visibility = View.VISIBLE
                targetFormation2.visibility = View.VISIBLE
                targetFormationEditText2.visibility = View.VISIBLE
                motivation2.visibility = View.VISIBLE
                motivationEditText2.visibility = View.VISIBLE
                modality2.visibility = View.VISIBLE
                modalityEditText2.visibility = View.VISIBLE
            }
            if(numberTarget == 3) {
                addTarget.visibility = View.GONE

                wish3.visibility = View.VISIBLE
                wishEditText3.visibility = View.VISIBLE
                targetFormation3.visibility = View.VISIBLE
                targetFormationEditText3.visibility = View.VISIBLE
                motivation3.visibility = View.VISIBLE
                motivationEditText3.visibility = View.VISIBLE
                modality3.visibility = View.VISIBLE
                modalityEditText3.visibility = View.VISIBLE
            }
        } else {
            numberTarget--
            if(numberTarget == 1) {
                deleteTarget.visibility = View.GONE

                wish2.visibility = View.GONE
                wishEditText2.visibility = View.GONE
                targetFormation2.visibility = View.GONE
                targetFormationEditText2.visibility = View.GONE
                motivation2.visibility = View.GONE
                motivationEditText2.visibility = View.GONE
                modality2.visibility = View.GONE
                modalityEditText2.visibility = View.GONE
            }
            if(numberTarget == 2) {
                addTarget.visibility = View.VISIBLE

                wish3.visibility = View.GONE
                wishEditText3.visibility = View.GONE
                targetFormation3.visibility = View.GONE
                targetFormationEditText3.visibility = View.GONE
                motivation3.visibility = View.GONE
                motivationEditText3.visibility = View.GONE
                modality3.visibility = View.GONE
                modalityEditText3.visibility = View.GONE
            }
        }
    }


    private fun checkField(numberTarget: Int , it: View){

        if(isManager) {
            when (numberTarget) {
                1 -> if(wishEditText1.text.toString().isEmpty() || targetFormationEditText1.text.toString().isEmpty()
                    || motivationEditText1.text.toString().isEmpty() || modalityEditText1.text.toString().isEmpty()) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }


                2 -> if(wishEditText1.text.toString().isEmpty() || targetFormationEditText1.text.toString().isEmpty()
                    || motivationEditText1.text.toString().isEmpty() || modalityEditText1.text.toString().isEmpty()
                    || wishEditText2.text.toString().isEmpty() || targetFormationEditText2.text.toString().isEmpty()
                    || motivationEditText2.text.toString().isEmpty() || modalityEditText2.text.toString().isEmpty()) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }


                3 -> if(wishEditText1.text.toString().isEmpty() || targetFormationEditText1.text.toString().isEmpty()
                    || motivationEditText1.text.toString().isEmpty() || modalityEditText1.text.toString().isEmpty()
                    || wishEditText2.text.toString().isEmpty() || targetFormationEditText2.text.toString().isEmpty()
                    || motivationEditText2.text.toString().isEmpty() || modalityEditText2.text.toString().isEmpty()
                    || wishEditText3.text.toString().isEmpty() || targetFormationEditText3.text.toString().isEmpty()
                    || motivationEditText3.text.toString().isEmpty() || modalityEditText3.text.toString().isEmpty()) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }
            }
        } else {
            when (numberTarget) {
                1 -> if(wishEditText1.text.toString().isEmpty() || motivationEditText1.text.toString().isEmpty() ) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }


                2 -> if(wishEditText1.text.toString().isEmpty() || motivationEditText1.text.toString().isEmpty()
                    || wishEditText2.text.toString().isEmpty() || motivationEditText2.text.toString().isEmpty()) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }


                3 -> if(wishEditText1.text.toString().isEmpty() || motivationEditText1.text.toString().isEmpty()
                    || wishEditText2.text.toString().isEmpty() || motivationEditText2.text.toString().isEmpty()
                    || wishEditText3.text.toString().isEmpty()  || motivationEditText3.text.toString().isEmpty() ) {
                    showMessage(it, this.getString(R.string.err_no_input))
                } else {
                    createOrUpdate()
                    UIUtil.goToNextPage(this, this.javaClass.simpleName)
                }
            }
        }

    }

    companion object {
        private const val TAG = "BilanFormation"
    }

}