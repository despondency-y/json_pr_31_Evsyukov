package com.bignerdranch.android.json_pr_31_evsyukov

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson


data class Product (
    val name: String,
    val price: Double,
    val tags: List<String>
)


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val etPrice = findViewById<EditText>(R.id.etPrice)
        val etTags = findViewById<EditText>(R.id.etTags)
        val btnProcess = findViewById<Button>(R.id.btnProcess)
        val tvResult = findViewById<TextView>(R.id.tvResult)


        val gson = Gson()

        val manualProduct = Product(
            name = "Программирование на Kotlin",
            price = 1500.0,
            tags = listOf("учебник", "программирование", "android")
        )
        val manualJsonString = gson.toJson(manualProduct)
        Log.d("ProductJSON", "Ручная сериализация: $manualJsonString")

        btnProcess.setOnClickListener {
            // п4
            val nameInput = etName.text.toString().trim()
            val priceInput = etPrice.text.toString().toDoubleOrNull() ?: 0.0

            // режем строку и получаем массив строк
            val rawTags = etTags.text.toString().split(",")

            // создаем пустой список где складывать теги
            val tagsInput = mutableListOf<String>()

            for (tag in rawTags) {
                val cleanTag = tag.trim()
                if (cleanTag.isNotEmpty()) {
                    tagsInput.add(cleanTag)
                }
            }
            val userProduct = Product(nameInput, priceInput, tagsInput)

            // п5
            val jsonString = gson.toJson(userProduct)
            Log.d("ProductJSON", "JSON из EditText: $jsonString")

            // п6
            val deserializedProduct = gson.fromJson(jsonString, Product::class.java)

            // результат в TextView и логи
            val resultText = """
                Десериализованный объект:
                Название: ${deserializedProduct.name}
                Цена: ${deserializedProduct.price} руб.
                Теги: ${deserializedProduct.tags.joinToString(", ")}
            """.trimIndent()

            tvResult.text = resultText
            Log.d("ProductJSON", "Вывод десериализации: $resultText")
        }
    }
}