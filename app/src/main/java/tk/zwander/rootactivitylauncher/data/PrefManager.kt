package tk.zwander.rootactivitylauncher.data

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageItemInfo
import androidx.core.content.edit
import androidx.core.content.pm.PackageInfoCompat
import androidx.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import tk.zwander.rootactivitylauncher.util.constructComponentKey

class PrefManager private constructor(context: Context) : ContextWrapper(context) {
    companion object {
        private var instance: PrefManager? = null

        fun getInstance(context: Context): PrefManager {
            return instance ?: PrefManager(context.applicationContext).also {
                instance = it
            }
        }

        const val KEY_EXTRAS = "ACTIVITY_EXTRAS"
        const val KEY_ACTIONS = "COMPONENT_ACTIONS"
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(this)
    private val gson = GsonBuilder()
        .create()

    var extras: HashMap<String, List<ExtraInfo>>
        get() = gson.fromJson(
            prefs.getString(KEY_EXTRAS, ""),
            object : TypeToken<HashMap<String, List<ExtraInfo>>>() {}.type
        ) ?: HashMap()
        set(value) {
            prefs.edit {
                putString(
                    KEY_EXTRAS,
                    gson.toJson(value)
                )
            }
        }
    var actions: HashMap<String, String?>
        get() = gson.fromJson(
            prefs.getString(KEY_ACTIONS, ""),
            object : TypeToken<HashMap<String, String?>>() {}.type
        ) ?: HashMap()
        set(value) {
            prefs.edit {
                putString(
                    KEY_ACTIONS,
                    gson.toJson(value)
                )
            }
        }
}