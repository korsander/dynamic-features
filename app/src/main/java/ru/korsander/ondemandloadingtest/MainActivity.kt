package ru.korsander.ondemandloadingtest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

class MainActivity : AppCompatActivity() {

    private lateinit var manager: SplitInstallManager

    private val modulePictures by lazy { getString(R.string.module_pictures) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = SplitInstallManagerFactory.create(this)

        findViewById<View>(R.id.buttonPictures).setOnClickListener {
            displayPictures()
        }
    }

    private fun displayPictures() {
        if (manager.installedModules.contains(modulePictures)) {
            launchActivity("ru.korsander.pictures.PictureActivity")
        } else {
            toastAndLog("The pictures module is not installed")
        }
    }

    private fun launchActivity(className: String) {
        Intent().setClassName(BuildConfig.APPLICATION_ID, className)
            .also {
                startActivity(it)
            }
    }
}

fun MainActivity.toastAndLog(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    Log.d("MainActivity", text)
}
