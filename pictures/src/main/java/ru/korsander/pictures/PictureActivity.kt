package ru.korsander.pictures

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        setContentView(R.layout.activity_picture)

        val carouselView = findViewById<CarouselView>(R.id.carouselView)
        carouselView.pageCount = images.size
        carouselView.setImageListener { position, imageView ->
            imageView?.setImageResource(images[position])
        }
    }
}
