package com.hitg.demowearos

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import androidx.wear.widget.CircularProgressLayout

class ConfirmScreenActivity : WearableActivity(),
    CircularProgressLayout.OnTimerFinishedListener,
    View.OnClickListener {


    private lateinit var circular_progress: CircularProgressLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_layout)

        setAmbientEnabled()

        circular_progress = findViewById(R.id.circular_progress)

        circular_progress.apply {
            onTimerFinishedListener = this@ConfirmScreenActivity
            setOnClickListener(this@ConfirmScreenActivity)
            totalTime = 2000
            startTimer()
        }
    }

    override fun onTimerFinished(layout: CircularProgressLayout?) {
        finish()
    }

    override fun onClick(v: View?) {
        circular_progress.stopTimer()
    }
}