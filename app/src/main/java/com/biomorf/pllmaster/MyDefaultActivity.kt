package com.biomorf.pllmaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatActivity


/**
 * Start project by Tohaman on 09.05.2019.
 * Задаем дефолтные параметры для всех активностей в программе
 */

abstract class MyDefaultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //задаем тему
        setTheme(getThemeFromSharedPreference(this))

        //Включаем поддержку векторной графики на устройствах ниже Лилипопа (5.0)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        super.onCreate(savedInstanceState)
    }
}