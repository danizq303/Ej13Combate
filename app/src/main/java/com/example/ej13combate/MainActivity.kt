package com.example.ej13combate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ej13combate.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstaceState: Bundle?) {
        super.onCreate(savedInstaceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setBackgroundResource(R.drawable.paisaje)

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.remove("Personaje")

        binding.imageButton.setOnClickListener {
            val gson = Gson()
            var json: String? = sharedPreference.getString("Personaje", "")
            json = if (json.equals("")) {
                val personaje = Personaje("P1",100,"Joven","Elfo","C")
                gson.toJson(personaje)
            } else {
                val personaje = gson.fromJson(json, Personaje::class.java)
                gson.toJson(personaje)
            }
            editor.putString("Personaje", json)
            editor.apply()

            when ((1..4).random()) {
                1 -> Intent (this, Objeto::class.java).also {
                    startActivity(it)
                }
                2 -> Intent (this, Ciudad::class.java).also {
                    startActivity(it)
                }
                3 -> Intent (this, Mercader::class.java).also {
                    startActivity(it)
                }
                4 -> Intent (this, Enemigo::class.java).also {
                    startActivity(it)
                }
            }
        }
    }


}