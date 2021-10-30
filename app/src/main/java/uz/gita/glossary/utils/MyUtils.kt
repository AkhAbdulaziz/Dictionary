package uz.gita.glossary.utils

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import uz.gita.glossary.app.App

fun color(@ColorRes colorResID: Int): Int =
    ContextCompat.getColor(App.instance, colorResID)