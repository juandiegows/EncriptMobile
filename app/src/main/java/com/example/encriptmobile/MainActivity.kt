package com.example.encriptmobile

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.example.encriptmobile.databinding.ActivityMainBinding
import java.lang.Exception
import java.security.MessageDigest
import java.security.spec.MGF1ParameterSpec
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var algoritmos = listOf(
    "MD5",
    "SHA1",
    "SHA256",
    "SHA512",
    "AES",
    "DES",
    "RSA",
    "DSA",
    "Blowfish",
    "Triple DES",
    "RC4",
    "HMAC",
    "ECC",
    "Diffie-Hellman",
    "PGP",
    "GPG",
    "SSL/TLS",
    "Bcrypt",
    "Argon2",
    "Scrypt"
    )
    val secretKey = "1234567890123456"

    //fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.spinner.adapter =  ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, algoritmos)
        binding.txtText.addTextChangedListener {
            Encript(binding.spinner.selectedItem.toString())
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Encript(binding.spinner.selectedItem.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Encript(binding.spinner.selectedItem.toString())
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun encryptAES(text: String, secretKey: String): String {
        try {
            if (secretKey.length != 16) {
                throw IllegalArgumentException("La clave debe tener 16 bytes")
            }
            val key = SecretKeySpec(secretKey.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key)

            val encryptedBytes = cipher.doFinal(text.toByteArray(Charsets.UTF_8))
            return Base64.getEncoder().encodeToString(encryptedBytes)
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error en AES ${e.message}"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptAES(encryptedText: String, secretKey: String): String {
        try {
            if (secretKey.length != 16) {
                throw IllegalArgumentException("La clave debe tener 16 bytes")
            }
            val key = SecretKeySpec(secretKey.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, key)

            val encryptedBytes = Base64.getDecoder().decode(encryptedText)
            val decryptedBytes = cipher.doFinal(encryptedBytes)
            return String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            return "Error en AES ${e.message}"
        }
    }




    @RequiresApi(Build.VERSION_CODES.O)
    private fun Encript(algorithm : String) {
        try {
            when(algorithm){
                "AES" -> {
                    binding.txtEncxript.text = encryptAES(binding.txtText.text.toString(), secretKey)
                }
                else ->{
                    MessageDigest.getInstance(algorithm).apply {
                        var text =
                            this.digest(binding.txtText.text.toString().encodeToByteArray()).joinToString("") {
                                "%02x".format(it)
                            }
                        binding.txtEncxript.text = text
                    }
                }
            }

        }catch (e: Exception){
            binding.txtEncxript.text = "No se puede con ${binding.spinner.selectedItem.toString()} :("
        }

    }
    private fun EncriptSHA1() {
        MessageDigest.getInstance("SHA1").apply {
            var text =
                this.digest(binding.txtText.text.toString().encodeToByteArray()).joinToString("") {
                    "%02x".format(it)
                }
            binding.txtEncxript.text = text
        }
    }
    private fun EncriptMD5() {
        MessageDigest.getInstance("MD5").apply {
            var text =
                this.digest(binding.txtText.text.toString().encodeToByteArray()).joinToString("") {
                    "%02x".format(it)
                }
            binding.txtEncxript.text = text
        }
    }
}