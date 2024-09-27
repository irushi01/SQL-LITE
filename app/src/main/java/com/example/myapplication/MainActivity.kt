package com.example.myapplication

import android.database.Cursor
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {
    lateinit var dbHelper: StudentDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = StudentDatabaseHelper(this)

        val studentIdEditText = findViewById<EditText>(R.id.studentIdEditText)
        val studentNameEditText = findViewById<EditText>(R.id.studentNameEditText)
        val studentMarksEditText = findViewById<EditText>(R.id.studentMarksEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        saveButton.setOnClickListener {
            val id = studentIdEditText.text.toString().toIntOrNull()
            val name = studentNameEditText.text.toString()
            val marks = studentMarksEditText.text.toString().toIntOrNull()

            if (id != null && name.isNotEmpty() && marks != null) {

                val success = dbHelper.addStudent(id, name, marks)
                if (success) {
                    Toast.makeText(this, "Student saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error saving student", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        searchButton.setOnClickListener {
            val id = studentIdEditText.text.toString().toIntOrNull()
            if (id != null) {
                val cursor: Cursor? = dbHelper.getStudentById(id)
                if (cursor != null && cursor.moveToFirst()) {
                    val studentName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                    val studentMarks = cursor.getInt(cursor.getColumnIndexOrThrow("marks"))
                    resultTextView.text = "ID: $id\nName: $studentName\nMarks: $studentMarks"
                    cursor.close()
                } else {
                    resultTextView.text = "Student not found"
                }
            } else {
                Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
