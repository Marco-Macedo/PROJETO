package com.example.projeto

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class AddTitle : AppCompatActivity() {

    private lateinit var titleText: EditText
    private lateinit var notesText: EditText
    private lateinit var dateText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_title)

        titleText = findViewById(R.id.titulo)
        notesText = findViewById(R.id.texto)
        dateText = findViewById(R.id.data)


        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(titleText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(EXTRA_REPLY_TITLE, titleText.text).toString()
                replyIntent.putExtra(EXTRA_REPLY_NOTES, notesText.text.toString())
                replyIntent.putExtra(EXTRA_REPLY_DATE, dateText.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
    companion object {
        const val EXTRA_REPLY_TITLE = "com.example.android.title"
        const val EXTRA_REPLY_NOTES = "com.example.android.notes"
        const val EXTRA_REPLY_DATE = "com.example.android.date"
    }
}