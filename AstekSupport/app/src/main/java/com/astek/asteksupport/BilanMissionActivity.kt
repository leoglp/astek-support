package com.astek.asteksupport

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.astek.asteksupport.utils.AuthenticationUtil
import kotlinx.android.synthetic.main.activity_bilan_mission.*
import kotlinx.android.synthetic.main.activity_bilan_mission.pageNumber
import kotlinx.android.synthetic.main.activity_interview_context.*

class BilanMissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilan_mission)

        pageNumber.text = this.getString(R.string.pageNumber,"2","2")

        backArrowBilan.setOnClickListener{
            onBackPressed()
        }
    }
}