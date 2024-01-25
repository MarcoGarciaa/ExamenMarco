package com.example.examenmarco

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var articulo: Articulo
    private lateinit var mochila: Mochila

    private lateinit var nombre: EditText
    private lateinit var tipoArticulo: EditText
    private lateinit var Peso: EditText
    private lateinit var precio: EditText
    private lateinit var text: TextView



    override fun onCreate(savedInstanceState: Bundle?) {

        nombre = findViewById(R.id.nombre)
        tipoArticulo = findViewById(R.id.tipoArticulo)
        Peso = findViewById(R.id.Peso)
        precio = findViewById(R.id.precio)
        text = findViewById(R.id.text)










        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





    }
}