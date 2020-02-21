package ru.korsander.ondemandloadingtest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

class MainActivity : AppCompatActivity() {

    private lateinit var manager: SplitInstallManager

    private val modulePictures by lazy { getString(R.string.module_pictures) }

    private lateinit var progressBar: ProgressBar

    private val listener = SplitInstallStateUpdatedListener { state ->
        when (state.status()) {
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                startIntentSender(state.resolutionIntent()?.intentSender, null, 0, 0, 0)
            }

            SplitInstallSessionStatus.INSTALLED -> {
                launchActivity("ru.korsander.ondemandloadingtest.pictures.PictureActivity")
            }

            SplitInstallSessionStatus.DOWNLOADING -> {
                //  In order to see this, the application has to be uploaded to the Play Store.
                progressBar.visibility = View.VISIBLE
                progressBar.max = state.totalBytesToDownload().toInt()
                progressBar.progress = state.bytesDownloaded().toInt()
            }

            SplitInstallSessionStatus.INSTALLING -> {
                progressBar.visibility = View.GONE
                toastAndLog("Installing feature")
            }

            SplitInstallSessionStatus.FAILED -> {
                toastAndLog("Error: ${state.errorCode()} for module ${state.moduleNames()}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = SplitInstallManagerFactory.create(this)

        findViewById<View>(R.id.buttonPictures).setOnClickListener {
            displayPictures()
        }

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        manager.registerListener(listener)
    }

    override fun onPause() {
        super.onPause()
        manager.unregisterListener(listener)
    }

    private fun displayPictures() {
        if (manager.installedModules.contains(modulePictures)) {
            launchActivity("ru.korsander.ondemandloadingtest.pictures.PictureActivity")
        } else {
            toastAndLog("The pictures module is not installed")

            val request = SplitInstallRequest.newBuilder()
                .addModule(modulePictures)
                .build()

            manager.startInstall(request)
                .addOnCompleteListener { toastAndLog("Module $modulePictures installed") }
                .addOnSuccessListener { toastAndLog("Loading $modulePictures") }
                .addOnFailureListener { toastAndLog("Error Loading $modulePictures") }
        }
    }

    private fun launchActivity(className: String) {
        Intent().setClassName(packageName, className)
            .also {
                startActivity(it)
            }
    }
}

fun MainActivity.toastAndLog(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    Log.d("MainActivity", text)
}
