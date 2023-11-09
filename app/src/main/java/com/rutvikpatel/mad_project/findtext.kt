package com.rutvikpatel.mad_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rutvikpatel.mad_project.databinding.ActivityFindtextBinding
import java.util.Stack

class findtext : AppCompatActivity() {
    private lateinit var binding: ActivityFindtextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findtext)
        binding = ActivityFindtextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val items = resources.getStringArray(R.array.dropdown_items)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = items[position]
                if(selectedItem == "Calculate"){
                    binding.textword.visibility = View.GONE
                    binding.textboxString.setText("")
                    binding.copytextSearch.setText("Calculate")
                }
                else if(selectedItem=="Search Word"){
                    binding.textboxString.setText("")
                    binding.textword.visibility = View.VISIBLE
                    binding.copytextSearch.setText("Search Text")
                }
                else if (selectedItem=="Count Word"){
                    binding.textboxString.setText("")
                    binding.textword.visibility = View.GONE
                    binding.copytextSearch.setText("Count Word")
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.copytextSearch.setOnClickListener{
            var buttontext = binding.copytextSearch.text.toString()
            if (buttontext=="Search Text"){
                var input = binding.textboxString.text.toString()
                var word = binding.textword.text.toString()
                val wordfound = findWordInString(input,word)
                binding.textword.clearFocus()
                val result = "Word : "+wordfound[0].first.toString()+"\n"+"Found On Index : "+wordfound[0].second.toString()
                binding.resulttext.setText(result)
            }
            else if(buttontext == "Count Word"){
                var inputString = binding.textboxString.text.toString()
                val words = inputString.split("\\s+".toRegex())

                // Filter out any empty strings
                val nonEmptyWords = words.filter { it.isNotEmpty() }
                binding.resulttext.setText("Total Number of Words : "+nonEmptyWords.size.toString())
            }
            else if (buttontext == "Calculate"){
                var inputString = binding.textboxString.text.toString()
                var result = maths(inputString)
                binding.resulttext.setText("Result is  : "+result.toString())
            }
            Toast.makeText(this,"Data Added", Toast.LENGTH_LONG).show()
        }
        binding.back.setOnClickListener{
            Intent(this,MainActivity::class.java).also { startActivity(it) }
        }
    }
    fun maths(input: String): Double {
        val stack = Stack<Double>()
        var currentNumber = 0.0
        var operation = '+'
        for (i in input.indices) {
            val c = input[i]
            if (Character.isDigit(c)) {
                currentNumber = currentNumber * 10 + (c - '0')
            }
            if (!Character.isDigit(c) && c != ' ' || i == input.length - 1) {
                when (operation) {
                    '+' -> stack.push(currentNumber)
                    '-' -> stack.push(-currentNumber)
                    '*' -> stack.push(stack.pop() * currentNumber)
                    '/' -> stack.push(stack.pop() / currentNumber)
                }
                operation = c
                currentNumber = 0.0
            }
        }
        return stack.sum()
    }
    fun findWordInString(inputString: String, targetWord: String): List<Triple<String, Int, Int>> {
        val foundWords = mutableListOf<Triple<String, Int, Int>>()
        var startIndex = 0
        var count = 0

        while (startIndex < inputString.length) {
            val index = inputString.indexOf(targetWord, startIndex, ignoreCase = true)
            if (index != -1) {
                foundWords.add(Triple(targetWord, index, count))
                count++
                startIndex = index + targetWord.length
            } else {
                break
            }
        }

        return foundWords
    }
}