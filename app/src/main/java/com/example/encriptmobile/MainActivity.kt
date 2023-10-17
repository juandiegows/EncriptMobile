package com.example.encriptmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import com.example.encriptmobile.databinding.ActivityMainBinding
import java.security.MessageDigest
import java.security.spec.MGF1ParameterSpec

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

    //fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.spinner.adapter =  ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, algoritmos)
        binding.txtText.addTextChangedListener {
            Encript(binding.spinner.selectedItem.toString())
        }
    }
    private fun Encript(algorithm : String) {
        MessageDigest.getInstance(algorithm).apply {
            var text =
                this.digest(binding.txtText.text.toString().encodeToByteArray()).joinToString("") {
                    "%02x".format(it)
                }
            binding.txtEncxript.text = text
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