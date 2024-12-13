package com.example.testing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val medicineName = intent?.getStringExtra("medicineName")
        val dosage = intent?.getStringExtra("dosage")
        val type = intent?.getStringExtra("type")

        Toast.makeText(
            context,
            "Time to take your medicine: $medicineName ($dosage $type)",
            Toast.LENGTH_LONG
        ).show()
    }
}