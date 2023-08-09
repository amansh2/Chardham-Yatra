package com.example.chardhamyatra


import android.content.pm.PackageManager

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.options
import com.example.chardhamyatra.databinding.ActivityImageToTextBinding
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.*


class ImageToText : AppCompatActivity() {
    lateinit var binding: ActivityImageToTextBinding
    private var str = ""
    lateinit var  tospeak:TextToSpeech
    private val cropImageLauncher = registerForActivityResult(CropImageContract()) {
        if (it.isSuccessful) {
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val image = InputImage.fromFilePath(applicationContext, it.uriContent!!)
            recognizer.process(image).addOnSuccessListener { visionText ->

                for (block in visionText.textBlocks) {
                    for (line in block.lines) {
                        str += line.text
                        str += " "
                    }
                    str += "\n"
                }
//            binding.scannedText.text = str
                translate(str)

            }.addOnFailureListener { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, it.error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun translate(str: String) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
            .build()
        val englishHindiTranslator = Translation.getClient(options)
        lifecycle.addObserver(englishHindiTranslator)

        var conditions = DownloadConditions.Builder()
            .build()
        lifecycleScope.launch(Dispatchers.IO) {
            englishHindiTranslator.downloadModelIfNeeded(conditions).await()
            lifecycleScope.launch(Dispatchers.Main) {
                try {
                    englishHindiTranslator.translate(str).addOnSuccessListener {
                        binding.scannedText.text = it
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ImageToText, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageToTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ), 101
                )
            }
        }
        binding.camera.setOnClickListener {
            cropImageLauncher.launch(
                options {
                    setGuidelines(com.canhub.cropper.CropImageView.Guidelines.ON)
                }
            )
        }
        textToSpeech(str)
        binding.speak.setOnClickListener{
            tospeak.speak(str,TextToSpeech.QUEUE_FLUSH,null)
        }

    }

    private fun textToSpeech(str: String) {
        tospeak=TextToSpeech(applicationContext,TextToSpeech.OnInitListener {
            if(it!=TextToSpeech.ERROR){
                tospeak.setLanguage(Locale.ENGLISH)
            }
        })

    }
}


