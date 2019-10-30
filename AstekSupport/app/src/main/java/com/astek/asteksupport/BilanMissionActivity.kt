package com.astek.asteksupport

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bilan_mission.*

class BilanMissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilan_mission)

        pageNumber.text = this.getString(R.string.pageNumber,"2","2")

        
    }
}