package de.syntax_institut.pokemonapibeispielapp.util

import android.content.Context
import android.graphics.Color
import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

// Funktion um gifs zu laden, auf der github page von coil zu finden
fun getImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
}

fun getTypeColor(type: String): Int {
    return when(type) {
        "grass" -> Color.argb(255, 41, 148, 61)
        "poison" -> Color.argb(255, 88, 15, 89)
        "fire" -> Color.argb(255, 237, 73, 62)
        "flying" -> Color.argb(255, 237, 230, 236)
        "water" -> Color.argb(255, 82, 138, 235)
        "electric" -> Color.argb(255, 204, 194, 100)
        "bug" -> Color.argb(255, 116, 184, 128)
        "normal" -> Color.argb(255, 156, 148, 154)
        "ghost" -> Color.argb(255, 82, 29, 73)
        "fairy" -> Color.argb(255, 255, 120, 232)
        "psychic" -> Color.argb(255, 178, 120, 179)
        "ground" -> Color.argb(255, 128, 96, 54)
        "fighting" -> Color.argb(255, 227, 156, 109)
        "steel" -> Color.argb(255, 92, 96, 97)
        "ice" -> Color.argb(255, 98, 211, 240)
        "dragon" -> Color.argb(255, 127, 42, 212)
        "dark" -> Color.argb(255, 46, 43, 39)
        "rock" -> Color.argb(255, 89, 78, 52)
        else -> Color.argb(255, 255, 255, 255)
    }
}

// Beispiele für extension functions
fun Int.toCM(): String {
    return "${this * 10} CM"
}

fun Int.toKG(): String {
    return "${(this / 10)} KG"
}

val listOfTypes = listOf(
    "grass",
    "poison",
    "fire",
    "flying",
    "water",
    "electric",
    "bug",
    "normal",
    "ghost",
    "fairy",
    "psychic",
    "ground",
    "fighting",
    "steel",
    "ice",
    "dragon",
    "dark",
    "rock"
)