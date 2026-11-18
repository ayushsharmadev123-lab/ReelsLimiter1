package com.custom.reelslimiter

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class ReelsAccessibilityService : AccessibilityService() {
    private var lastScrollTime = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        val pkg = event.packageName?.toString() ?: return

        if ((pkg == "com.google.android.youtube" || pkg == "com.instagram.android") &&
            event.eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {

            val now = System.currentTimeMillis()
            if (now - lastScrollTime > 1200) {
                lastScrollTime = now
                val prefs = getSharedPreferences("ReelsPrefs", Context.MODE_PRIVATE)
                val limit = prefs.getInt("limit", 5)
                val current = prefs.getInt("count", 0) + 1

                if (current >= limit) {
                    prefs.edit().putInt("count", 0).apply()
                    Toast.makeText(this, "Limit ($limit) poori ho gayi! Auto-Back...", Toast.LENGTH_SHORT).show()
                    performGlobalAction(GLOBAL_ACTION_BACK)
                } else {
                    prefs.edit().putInt("count", current).apply()
                }
            }
        }
    }

    override fun onInterrupt() {}
}
