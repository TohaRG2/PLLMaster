package com.biomorf.pllmaster

import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.util.Log
import com.biomorf.pllmaster.DebugTag.TAG
import java.util.*


class SolveCube (var cube: IntArray, var solve: String)   // куб, решение
class Pair4Melting (var allComplete: Boolean, var elementsNotOnPlace: SortedMap<Int, Int>)

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun saveBoolean2SP(value: Boolean, key: String, context: Context) {
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sp.edit()
    editor.putBoolean(key, value)
    editor.apply() // подтверждаем изменения
}

fun saveInt2SP(value: Int, key: String, context: Context) {
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sp.edit()
    editor.putInt(key, value)
    editor.apply() // подтверждаем изменения
}

fun saveString2SP(value : String, key: String, context: Context) {
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sp.edit()
    editor.putString(key, value)
    editor.apply() // подтверждаем изменения
}

fun spannedString(desc:String, imgGetter: Html.ImageGetter, tagHandler: Html.TagHandler? = null): Spanned {
    // Немного преобразуем текст для корректного отображения.
    val desc1 = desc.replace("%%", "%25")

    // Android 7.0 ака N (Nougat) = API 24, начиная с версии Андроид 7.0 вместо HTML.fromHtml (String)
    // лучше использовать HTML.fromHtml (String, int), где int различные флаги, влияющие на отображение html
    // аналогично для метода HTML.fromHtml (String, ImageGetter, TagHandler) -> HTML.fromHtml (String, int, ImageGetter, TagHandler)
    // поэтому используем @SuppressWarnings("deprecation") перед объявлением метода и вот такую конструкцию
    // для преобразования String в Spanned. В принципе использование старой конструкции равноценно использованию
    // новой с флагом Html.FROM_HTML_MODE_LEGACY... подробнее о флагах-модификаторах на developer.android.com
    // В методе Html.fromHtml(String, imgGetter, tagHandler) - tagHandler - это метод, который вызывется, если
    // в строке встречается тэг, который не распознан, т.е. тут можно обрабатывать свои тэги
    // пока не используется (null), но все воозможно :)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(desc1, Html.FROM_HTML_MODE_LEGACY, imgGetter, tagHandler)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(desc1, imgGetter, tagHandler)
    }
}

fun getThemeFromSharedPreference(context: Context) : Int {
    Log.v(TAG, "SetActivityTheme")
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    val theme = sp.getString("theme", "AppTheme")
    return when (theme)  {
        "AppThemeLight" -> R.style.AppThemeLight
        "AppThemeDayNight" -> R.style.AppThemeDayNight
        else -> R.style.AppTheme
    }

}
