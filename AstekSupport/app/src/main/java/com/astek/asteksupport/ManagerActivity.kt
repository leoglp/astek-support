package com.astek.asteksupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.DataBaseUtil.Companion.retrieveMailAddress
import com.astek.asteksupport.utils.UIUtil
import kotlinx.android.synthetic.main.activity_bilan_mission.*
import kotlinx.android.synthetic.main.activity_manager.*
import kotlinx.android.synthetic.main.page_layout.*

class ManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        validateButton.setOnClickListener{
            if(nameEditText.text.toString().isEmpty()
                || surnameEditText.text.toString().isEmpty()) {
                UIUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                retrieveMailAddress(nameEditText.text.toString().toLowerCase(),
                    surnameEditText.text.toString().toLowerCase(),
                    it,
                    this)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        logout.setOnClickListener{
            UIUtil.backToHome(this)
        }
    }
}