package com.rutvikpatel.mad_project

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rutvikpatel.mad_project.databinding.ActivityMainBinding
import java.util.Locale

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.copytext.setOnClickListener{
            val handler = Handler()
            val textToCopy = binding.textbox.text.toString()
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Text", textToCopy)
            clipboardManager.setPrimaryClip(clipData)
            binding.copytext.setText("Text Copied")
            handler.postDelayed(Runnable {
                binding.copytext.setText("Copy Text")
            }, 1000)
            Toast.makeText(this,"Text Copied Successfully...!",Toast.LENGTH_LONG).show()
        }
    binding.uppercase.setOnClickListener{
        var text = binding.textbox.text.toString().uppercase(Locale.ROOT)
        binding.textbox.setText(text)
        binding.textbox.setSelection(binding.textbox.text.length)
    }
        binding.lowercase.setOnClickListener{
            var text = binding.textbox.text.toString().lowercase(Locale.ROOT)
            binding.textbox.setText(text)
            binding.textbox.setSelection(binding.textbox.text.length)
            binding.textbox.setSelection(binding.textbox.text.length)
        }
        binding.clear.setOnClickListener{
            binding.textbox.setText("")
        }
        binding.camelcase.setOnClickListener{
            var input = binding.textbox.text.toString()
            var value = camel(input)
            binding.textbox.setText(value)
            binding.textbox.setSelection(binding.textbox.text.length)
        }
        binding.removespace.setOnClickListener{
            var input = binding.textbox.text.toString()
            var result = input.replace("\\s+".toRegex(), " ").trim()
            binding.textbox.setText(result)
            binding.textbox.setSelection(binding.textbox.text.length)
        }
        binding.findword.setOnClickListener{
            Intent(this,findtext::class.java).also { startActivity(it) }
        }
    }
    fun camel(input:String):String{
        val words = input.split(" ", "_")
        val upperCamelCaseWords = words.map { it.capitalize() }
        return upperCamelCaseWords.joinToString("")
    }
}