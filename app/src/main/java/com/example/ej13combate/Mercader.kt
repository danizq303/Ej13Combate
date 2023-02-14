package com.example.ej13combate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ej13combate.databinding.ActivityMercaderBinding
import com.google.gson.Gson


class Mercader : AppCompatActivity() {
    private lateinit var binding: ActivityMercaderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMercaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setImageResource(R.drawable.mercader)


        binding.button.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.objeto)
            binding.button.visibility = View.INVISIBLE
            binding.button2.visibility = View.INVISIBLE
            binding.button3.visibility = View.VISIBLE
            binding.button4.visibility = View.VISIBLE
            binding.button5.visibility = View.VISIBLE
            binding.editTextNumber.visibility = View.VISIBLE
            binding.textView2.visibility = View.VISIBLE
        }

        binding.button2.setOnClickListener {
            Intent (this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.button3.setOnClickListener {
            binding.imageView.setImageResource(R.drawable.mercader)
            binding.button.visibility = View.VISIBLE
            binding.button2.visibility = View.VISIBLE
            binding.button3.visibility = View.INVISIBLE
            binding.button4.visibility = View.INVISIBLE
            binding.button5.visibility = View.INVISIBLE
            binding.editTextNumber.visibility = View.INVISIBLE
            binding.textView2.visibility = View.INVISIBLE
        }

        binding.button4.setOnClickListener {
            val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

            val gson = Gson()
            var json: String? = sharedPreference.getString("Personaje", "")
            val personaje = gson.fromJson(json, Personaje::class.java)

            val editor = sharedPreference.edit()

            val numeroObjetos = personaje.getMochila().getPesoMochila() / 5
            if (numeroObjetos < binding.editTextNumber.text.toString().toInt()) {
                Toast.makeText(this, "No tienes suficientes objetos", Toast.LENGTH_LONG).show()
            } else {
                for (i in 0..binding.editTextNumber.text.toString().toInt()) {
                    personaje.vender2(Articulo("MObjeto", 125))
                }
                Toast.makeText(this, "Venta Realizada", Toast.LENGTH_LONG).show()
            }

            json = gson.toJson(personaje)
            editor.putString("Personaje", json)
            editor.apply()

            Intent (this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.button5.setOnClickListener {
            val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

            val gson = Gson()
            var json: String? = sharedPreference.getString("Personaje", "")
            val personaje = gson.fromJson(json, Personaje::class.java)

            val editor = sharedPreference.edit()

            val numeroObjetos = personaje.getMochila().getPesoMochila() / 5
            if (numeroObjetos < binding.editTextNumber.text.toString().toInt()) {
                Toast.makeText(this, "No tienes suficiente dinero", Toast.LENGTH_LONG).show()
            } else {
                for (i in 0..binding.editTextNumber.text.toString().toInt()) {
                    personaje.comprar(Articulo("MObjeto", 125))
                }
                Toast.makeText(this, "Compra Realizada", Toast.LENGTH_LONG).show()
            }

            json = gson.toJson(personaje)
            editor.putString("Personaje", json)
            editor.apply()

            Intent (this, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}