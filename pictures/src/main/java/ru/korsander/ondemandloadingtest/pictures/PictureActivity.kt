package ru.korsander.ondemandloadingtest.pictures

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat
import com.synnapps.carouselview.CarouselView

class PictureActivity : AppCompatActivity() {

    val images = listOf(
        R.drawable.aventador,
        R.drawable.burj,
        R.drawable.cyberpunkstreets,
        R.drawable.iceland,
        R.drawable.monaco,
        R.drawable.night,
        R.drawable.skycrapper,
        R.drawable.spain
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplitCompat.install(this)
        setContentView(R.layout.activity_picture)

        val carouselView = findViewById<CarouselView>(R.id.carouselView)
        carouselView.pageCount = images.size
        carouselView.setImageListener { position, imageView ->
            imageView?.setImageResource(images[position])
        }
    }
}
