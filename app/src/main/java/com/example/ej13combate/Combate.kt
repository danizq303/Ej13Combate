package com.example.ej13combate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ej13combate.databinding.ActivityCombateBinding
import com.google.gson.Gson

class Combate : AppCompatActivity() {
    private lateinit var binding: ActivityCombateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCombateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView2.setBackgroundResource(R.drawable.zombie)

        val bool = (0..1).random() == 1

        var personaje = Personaje("P1",100,"Joven","Elfo","C")
        val vidaPOriginal = personaje.vida
        val vidaPersonaje = personaje.vida
        var vida = if (bool) {
            200
        } else {
            100
        }
        val vidaEnemigo = vida

        val ataque = if (bool) {
            30 / personaje.defensa
        } else {
            20 / personaje.defensa
        }

        binding.textView3.text = binding.textView3.text.toString() + vida.toString()
        binding.textView4.text = binding.textView4.text.toString() + personaje.vida.toString()

        binding.button6.setOnClickListener {
//            val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
//            val gson = Gson()
//            var json: String? = sharedPreference.getString("Personaje", "")
//            val personaje = gson.fromJson(json, Personaje::class.java)

            val random = (1..6).random()
            if (random == 4 || random == 5 || random == 6) {
                vida -= personaje.fuerza
                changeHealthImageEnemigo(vida, vidaEnemigo)

                if (vida <= 0) {
                    Toast.makeText(this, "Has ganado", Toast.LENGTH_LONG).show()

                    repeat(3) {
                        personaje.getMochila().addArticulo(Articulo("Cura"))
                    }
                    personaje.monedero[100] = personaje.monedero[100]!! + 1
                    personaje.vida = vidaPOriginal

                    val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                    val gson = Gson()
                    val json = gson.toJson(personaje)
                    val editor = sharedPreference.edit()
                    editor.putString("Personaje", json)
                    editor.apply()
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                    }
                } else {
                    binding.textView3.text = "Vida Enemigo: $vida"
                    Toast.makeText(this, "Has atacado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No puedes atacar", Toast.LENGTH_SHORT).show()
            }

            turnoEnemigo(personaje, ataque, vidaPersonaje)
        }

        binding.button7.setOnClickListener {
            val random = (1..6).random()
            if (random == 5 || random == 6) {
                Toast.makeText(this, "Has huido", Toast.LENGTH_LONG).show()
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }
            } else {
                Toast.makeText(this, "No puedes huir", Toast.LENGTH_SHORT).show()
                turnoEnemigo(personaje, ataque, vidaPersonaje)
            }
        }

        binding.button8.setOnClickListener {
            //Consumir objeto.vida y mostrar Toast de que se ha consumido
            if (vidaPersonaje != personaje.vida && personaje.getMochila()
                    .getContenido()[0].getVida() + personaje.vida > vidaPersonaje
            ) {
                personaje.vida = vidaPersonaje
                binding.textView4.text = "Vida Jugador: ${personaje.vida}"
                changeHealthImage(personaje.vida, vidaPersonaje)

                personaje.getMochila().getContenido().removeAt(0)
            } else {
                if (personaje.getMochila()
                        .getContenido()[0].getVida() + personaje.vida > vidaPersonaje) {
                    Toast.makeText(this, "No puedes curarte mas", Toast.LENGTH_SHORT).show()
                } else {
                    if (personaje.getMochila().getContenido().size > 0) {
                        personaje.vida += personaje.getMochila().getContenido()[0].getVida()
                        binding.textView4.text = "Vida Jugador: ${personaje.vida}"
                        changeHealthImage(personaje.vida, vidaPersonaje)

                        personaje.getMochila().getContenido().removeAt(0)
                        Toast.makeText(this, "Has consumido un objeto", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "No tienes mas objetos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun turnoEnemigo(personaje: Personaje, ataque: Int, vidaPersonaje: Int) {
        personaje.vida -= ataque
        changeHealthImage(personaje.vida, vidaPersonaje)
        if (personaje.vida > 0) {
            binding.textView4.text = "Vida Jugador: ${personaje.vida}"
        } else {
            Toast.makeText(this, "Has muerto", Toast.LENGTH_LONG).show()
            Intent(this, Black::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun changeHealthImage(vida: Int, vidaTotal: Int) {
        val r = vidaTotal / 3
        when (vida) {
            in 1..r -> {
                binding.imageView5.setImageResource(R.drawable.bar2)
            }
            in r..r * 2 -> {
                binding.imageView5.setImageResource(R.drawable.bar3)
            }
            in r * 2..vidaTotal -> {
                binding.imageView5.setImageResource(R.drawable.bar4)
            }
            else -> {
                binding.imageView5.setImageResource(R.drawable.bar1)
            }
        }
    }

    private fun changeHealthImageEnemigo(vida: Int, vidaTotal: Int) {
        val r = vidaTotal / 3
        when (vida) {
            in 1..r -> {
                binding.imageView4.setImageResource(R.drawable.bar2)
            }
            in 33..r * 2 -> {
                binding.imageView4.setImageResource(R.drawable.bar3)
            }
            in 66..vidaTotal -> {
                binding.imageView4.setImageResource(R.drawable.bar4)
            }
            else -> {
                binding.imageView4.setImageResource(R.drawable.bar1)
            }
        }
    }
}