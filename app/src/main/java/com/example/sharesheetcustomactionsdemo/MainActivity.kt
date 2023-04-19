package com.example.sharesheetcustomactionsdemo

import android.app.PendingIntent
import android.app.SearchManager
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.service.chooser.ChooserAction
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity() : AppCompatActivity() {

    @RequiresApi(34)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<AppCompatButton>(R.id.openSheet).setOnClickListener {
            customActionsShareSheet()
        }
    }

    private fun shareText(text: String?) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, text)
        sendIntent.type = "text/plain"
        val shareIntent = Intent.createChooser(sendIntent, title)
        startActivity(shareIntent)
    }

    @RequiresApi(34)
    private fun customActionsShareSheet() {
        //Create an Intent for the activity you want to launch
        val customIntent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, "Custom Actions in Sharesheet.")
        }
        val customIntent1 = Intent(applicationContext, MyCustomActivity::class.java)

        // Create a PendingIntents for the activity
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            customIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val pendingIntent1 = PendingIntent.getActivity(
            applicationContext,
            0,
            customIntent1,
            PendingIntent.FLAG_IMMUTABLE
        )

        //Add custom actions
        val customAction = ChooserAction.Builder(
            // Icon
            Icon.createWithResource(
                baseContext,
                com.google.android.material.R.drawable.material_ic_calendar_black_24dp
            ),
            // Label
            "Custom Search",
            // PendingIntent
            pendingIntent
        ).build()

        val customAction1 = ChooserAction.Builder(
            // Icon
            Icon.createWithResource(
                baseContext,
                com.google.android.material.R.drawable.material_ic_edit_black_24dp
            ),
            // Label
            "Custom Action",
            // PendingIntent
            pendingIntent1
        ).build()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Sample text to send.")
        }

        // Show the sharesheet with custom actions
        startActivity(Intent.createChooser(intent, "Share using...").apply {
            // We need to pass an array
            putExtra(Intent.EXTRA_CHOOSER_CUSTOM_ACTIONS, arrayOf(customAction, customAction1))
        })
    }

}