package com.example.encriptmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.example.encriptmobile.databinding.ActivityMainBinding
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    //fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtText.addTextChangedListener {
            EncriptSHA1()
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