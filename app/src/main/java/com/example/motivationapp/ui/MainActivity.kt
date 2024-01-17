package com.example.motivationapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.motivationapp.infra.MotivationConstants
import com.example.motivationapp.R
import com.example.motivationapp.data.Mock
import com.example.motivationapp.infra.SecurityPreferences
import com.example.motivationapp.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var categoryId = MotivationConstants.FILTER.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        handleUserName()
        handleFilterImageClicked(R.id.image_all)
        handleNextPhrase()

        binding.buttonNewPhrase.setOnClickListener(this)
        binding.imageAll.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.imageSunny.setOnClickListener(this)
        binding.textHelloUser.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        handleUserName()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_new_phrase) {
            handleNextPhrase()
        } else if (view.id in listOf(R.id.image_all, R.id.image_happy, R.id.image_sunny)) {
            handleFilterImageClicked(view.id)
        } else if (view.id == R.id.text_hello_user) {
            startActivity(Intent(this, UserActivity :: class.java))
        }
    }

    private fun handleNextPhrase() {
        val textViewPhrase = binding.textPhrase

        when (categoryId) {
            MotivationConstants.FILTER.ALL -> {
                textViewPhrase.text = Mock().getRandomPhrase(Locale.getDefault().language)
            }
            MotivationConstants.FILTER.HAPPY -> {
                textViewPhrase.text = Mock().getHappyPhrase(Locale.getDefault().language)
            }
            MotivationConstants.FILTER.SUNNY -> {
                textViewPhrase.text = Mock().getSunnyPhrase(Locale.getDefault().language)
            }
        }
    }

    private fun handleFilterImageClicked(id: Int) {
        val imageAll = binding.imageAll
        val imageHappy = binding.imageHappy
        val imageSunny = binding.imageSunny

        imageAll.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))
        imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.dark_purple))

        when (id) {
            R.id.image_all -> {
                imageAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.FILTER.ALL
            }
            R.id.image_happy -> {
                imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.FILTER.HAPPY
            }
            R.id.image_sunny -> {
                imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
                categoryId = MotivationConstants.FILTER.SUNNY
            }
        }
    }

    private fun handleUserName() {
        val username = SecurityPreferences(this).getString(MotivationConstants.KEY.USER_NAME)
        binding.textHelloUser.text = "${getString(R.string.hello)}, ${username}!"
    }
}