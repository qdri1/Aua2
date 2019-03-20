package fa.kz.aua.util

import android.content.Context

object Prefs {

    private const val LOCAL_CONFIGURATION_FILE = "local.conf"
    private const val KEY_LOCALE = "key_locale"

    @JvmStatic
    fun getResponse(context: Context): String? {
        val prefs = context.getSharedPreferences(LOCAL_CONFIGURATION_FILE, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LOCALE, null)
    }

    @JvmStatic
    fun setResponse(context: Context, response: String) {
        val prefs = context.getSharedPreferences(LOCAL_CONFIGURATION_FILE, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(KEY_LOCALE, response)
        editor.apply()
    }

}