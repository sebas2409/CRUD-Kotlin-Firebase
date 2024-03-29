package com.watermelon.prueba_firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.watermelon.prueba_firestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val database=FirebaseDatabase.getInstance("https://prueba-firebase-5c401-default-rtdb.europe-west1.firebasedatabase.app/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //añadir usuarios
        binding.btnAdd.setOnClickListener {
            val firstName=binding.etNombre.text.toString()
            val lastName=binding.etApellido.text.toString()
            val age=binding.etEdad.text.toString().toInt()


            val user=User(firstName,lastName,age)

            database.getReference("Users").child(firstName).setValue(user).addOnSuccessListener {
                binding.etNombre.text.clear()
                binding.etApellido.text.clear()
                binding.etEdad.text.clear()
            }
        }

        //mostrar usurios
        binding.btnShow.setOnClickListener {
            val nombre=binding.etNombre.text.toString()

            database.getReference("Users").child(nombre).get().addOnSuccessListener {
                if (it.exists()) {
                    val name = it.child("firstName").value
                    val apellido = it.child("lastName").value
                    val edad = it.child("age").value

                    binding.etNombre.text.clear()

                    binding.tvNombre.text = name.toString()
                    binding.tvApellido.text = apellido.toString()
                    binding.tvEdad.text = edad.toString()
                }else{
                    Toast.makeText(this,"Usuario no existente",Toast.LENGTH_LONG).show()
                }
            }
        }

        //actualizar usuarios

        //eliminar usuarios
        binding.btnDelete.setOnClickListener {
            val nombre=binding.etNombre.text.toString()
            database.getReference("Users").child(nombre).removeValue().addOnSuccessListener {
                binding.etNombre.text.clear()
                Toast.makeText(this,"Usario eliminado",Toast.LENGTH_LONG).show()
            }
        }
    }
}