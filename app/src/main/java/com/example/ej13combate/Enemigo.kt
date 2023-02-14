package com.example.ej13combate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ej13combate.databinding.ActivityEnemigoBinding

class Enemigo : AppCompatActivity() {
    private lateinit var binding: ActivityEnemigoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnemigoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageView.setBackgroundResource(R.drawable.enemigo)

        binding.button.setOnClickListener {
            Intent (this, Combate::class.java).also {
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