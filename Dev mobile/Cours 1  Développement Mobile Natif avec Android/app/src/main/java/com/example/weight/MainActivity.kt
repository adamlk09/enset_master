package com.example.weight

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var poidsInput: EditText
    private lateinit var tailleInput: EditText
    private lateinit var resultatText: TextView
    private lateinit var imageCategorie: ImageView
    private lateinit var calculerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        poidsInput = findViewById(R.id.poids)
        tailleInput = findViewById(R.id.taille)
        resultatText = findViewById(R.id.resultat)
        imageCategorie = findViewById(R.id.imageCategorie)
        calculerBtn = findViewById(R.id.calculerBtn)

        calculerBtn.setOnClickListener {
            val poids = poidsInput.text.toString().toFloatOrNull()
            val tailleCm = tailleInput.text.toString().toFloatOrNull()

            if (poids != null && tailleCm != null && tailleCm > 0) {
                val tailleM = tailleCm / 100
                val imc = poids / (tailleM * tailleM)
                val categorie = getCategorie(imc)

                resultatText.text = "Votre IMC est : %.2f\n($categorie)".format(imc)
                imageCategorie.setImageResource(getImageResource(categorie))
                imageCategorie.visibility = ImageView.VISIBLE
            } else {
                Toast.makeText(this, "Veuillez entrer des valeurs valides", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCategorie(imc: Float): String {
        return when {
            imc < 18.5 -> "Maigreur"
            imc < 25 -> "Normal"
            imc < 30 -> "Surpoids"
            imc < 40 -> "Obésité modérée"
            else -> "Obésité sévère"
        }
    }

    private fun getImageResource(categorie: String): Int {
        return when (categorie) {
            "Maigreur" -> R.drawable.maigreur
            "Normal" -> R.drawable.normal
            "Surpoids" -> R.drawable.surpoids
            "Obésité modérée" -> R.drawable.obesite_moderee
            "Obésité sévère" -> R.drawable.obesite_severe
            else -> R.drawable.ic_launcher_foreground
        }
    }
}
