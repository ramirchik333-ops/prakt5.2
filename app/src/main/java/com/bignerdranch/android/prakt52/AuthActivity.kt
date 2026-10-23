package com.bignerdranch.android.prakt52

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val etName = findViewById<EditText>(R.id.etName)
        val btnEnter = findViewById<Button>(R.id.btnEnter)

        // Проверяем — может пользователь уже входил?
        val prefs = getSharedPreferences("user", MODE_PRIVATE)
        val savedName = prefs.getString("name", null)

        if (savedName != null) {
            // Если уже входил — сразу идём на главный экран
            goToMain()
            return
        }

        // Кнопка "Войти"
        btnEnter.setOnClickListener {
            val name = etName.text.toString().trim()

            // Проверка на пустоту (как в задании)
            if (name.isEmpty()) {
                etName.error = "Введите имя!"
                return@setOnClickListener
            }

            // Сохраняем имя в SharedPreferences
            prefs.edit().putString("name", name).apply()
            Toast.makeText(this, "Привет, $name!", Toast.LENGTH_SHORT).show()
            goToMain()
        }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Закрываем экран входа
    }
}