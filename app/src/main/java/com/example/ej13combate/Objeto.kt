package com.example.ej13combate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ej13combate.databinding.ActivityObjetoBinding
import com.google.gson.Gson

class Objeto : AppCompatActivity() {
    private lateinit var binding: ActivityObjetoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjetoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setBackgroundResource(R.drawable.kart)

        binding.button.setOnClickListener {
            val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

            val gson = Gson()
            var json: String? = sharedPreference.getString("Personaje", "")
            val personaje = gson.fromJson(json, Personaje::class.java)

            json = gson.toJson(personaje)
            val editor = sharedPreference.edit()
            personaje.getMochila().addArticulo(Articulo("A1"))
            Toast.makeText(this, personaje.getMochila().getPesoMochila().toString(), Toast.LENGTH_LONG).show()
            editor.putString("Personaje", json)
            editor.apply()

            Intent (this, MainActivity::class.java).also {
                                startActivity(it)
            }
        }

        binding.button2.setOnClickListener {
            Intent (this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}