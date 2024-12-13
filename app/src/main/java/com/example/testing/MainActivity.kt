package com.example.testing

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var medicineName: EditText
    private lateinit var dosage: EditText
    private lateinit var timePicker: TimePicker
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        medicineName = findViewById(R.id.medicineName)
        dosage = findViewById(R.id.dosage)
        timePicker = findViewById(R.id.timePicker)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val hour = timePicker.currentHour
            val minute = timePicker.currentMinute
            val medicineName = intent?.getStringExtra("medicine_name")
            val dosage = intent?.getStringExtra("dosage")

            setAlarm(this, hour, minute, medicineName, dosage)
        }
    }

    private fun setAlarm(context: Context, hour: Int, minute: Int, medicineName: String, dosage: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("medicine_name", medicineName)
            putExtra("dosage", dosage)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeZone = TimeZone.getDefault()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            // Ensure the time is in the future
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}

// Create AlarmReceiver class
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Handle the alarm event here (e.g., display notification, play sound)
        val medicineName = intent?.getStringExtra("medicine_name")
        val dosage = intent?.getStringExtra("dosage")

        // Create and show a notification
        // ... (Notification code here)

        // Optionally, play a sound
        // ... (Sound playing code here)
    }
}