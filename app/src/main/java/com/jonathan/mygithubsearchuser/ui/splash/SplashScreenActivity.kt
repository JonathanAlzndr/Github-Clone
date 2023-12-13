package com.jonathan.mygithubsearchuser.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.jonathan.mygithubsearchuser.databinding.ActivitySplashScreenBinding
import com.jonathan.mygithubsearchuser.ui.main.MainActivity

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val move = Intent(this, MainActivity::class.java)
            startActivity(move)
            finish()
        }, 3000)
    }
}