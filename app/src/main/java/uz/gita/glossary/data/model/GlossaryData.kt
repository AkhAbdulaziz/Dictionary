package uz.gita.glossary.data.model

import java.io.Serializable

data class GlossaryData(
    val id: Int,
    val word: String,
    val wordType: String,
    val definition: String,
    var isFavourite: Int    // 0 -> not favourite, 1 -> favourite
) : Serializable