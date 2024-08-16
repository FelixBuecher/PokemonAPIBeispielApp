package de.syntax_institut.pokemonapibeispielapp.util

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

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