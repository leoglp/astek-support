package com.astek.asteksupport

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil.Companion.signIn
import com.astek.asteksupport.utils.ShowUtil
import com.astek.asteksupport.utils.ShowUtil.Companion.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectButton.setOnClickListener{
            hideKeyboard(this,it)
            if(mailEditText.text.toString().isEmpty() || passwordEditText.text.toString().isEmpty()) {
                ShowUtil.showMessage(it, this.getString(R.string.err_no_input))
            } else {
                signIn(this,it,mailEditText.text.toString(), passwordEditText.text.toString())
            }
        }

        subscribeText.setOnClickListener{
            val intent = Intent(this, ProfilCreationActivity::class.java)
            startActivity(intent)
        }
    }
}
