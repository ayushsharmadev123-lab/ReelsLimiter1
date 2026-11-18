package com.custom.reelslimiter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("ReelsPrefs", Context.MODE_PRIVATE)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 80, 60, 60)
        }

        val title = TextView(this).apply {
            text = "Reels & Shorts Limiter"
            textSize = 24f
        }

        val desc = TextView(this).apply {
            text = "\nKitni Reels/Shorts ke baad auto-back karna hai?"
            textSize = 16f
        }

        val input = EditText(this).apply {
            hint = "Example: 5"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            setText(prefs.getInt("limit", 5).toString())
        }

        val saveBtn = Button(this).apply {
            text = "1. Limit Save Karein"
            setOnClickListener {
                val limitVal = input.text.toString().toIntOrNull() ?: 5
                prefs.edit().putInt("limit", limitVal).putInt("count", 0).apply()
                Toast.makeText(this@MainActivity, "Limit $limitVal reels set ho gayi!", Toast.LENGTH_SHORT).show()
            }
        }

        val permBtn = Button(this).apply {
            text = "2. Accessibility Service ON Karein"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                Toast.makeText(this@MainActivity, "Downloaded Apps me jaakar 'Reels Limiter' ON karein", Toast.LENGTH_LONG).show()
            }
        }

        layout.addView(title)
        layout.addView(desc)
        layout.addView(input)
        layout.addView(saveBtn)
        layout.addView(permBtn)
        setContentView(layout)
    }
}
