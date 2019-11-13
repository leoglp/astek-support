package com.astek.asteksupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.reinitialisation
import com.astek.asteksupport.utils.UIUtil
import kotlinx.android.synthetic.main.activity_password_reinitialisation.*

class PasswordReinitialisationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reinitialisation)

        reinitialisationButton.setOnClickListener{
            if(mailEditText.text.toString().isEmpty()) {
                UIUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                reinitialisation(mailEditText.text.toString(),
                    it,
                    this)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        UIUtil.backToHome(this)
    }
}