package com.example.globochat


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.*


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val accSettingsPref = findPreference<Preference>(getString(R.string.key_account_settings))


        accSettingsPref?.setOnPreferenceClickListener {
            // do something...

            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_frag) as NavHostFragment
            val navController = navHostFragment.navController
            val action = SettingsFragmentDirections.actionSettingsToAccSettings()
            navController.navigate(action)

            true
        }
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val autoReplyTime = sharedPreferences.getString("key_auto_reply_time", "")
        Log.i("SettingFragment", "Auto Reply Time: $autoReplyTime")

        val autoDownload = sharedPreferences.getBoolean("key_auto_download", false)
        Log.i("SettingFragment", "Auto Download: $autoDownload")

        val statusPref = findPreference<EditTextPreference>("key_status")
        statusPref?.setOnPreferenceChangeListener { preference, newValue ->

            val newStatus = newValue as String
            if (newStatus.contains("bad")) {
                Toast.makeText(context, "Inappropriate Status. Please maintain community guidelines.",
                    Toast.LENGTH_SHORT).show()

                false   // false: reject the new value.
            } else {
                true     // true: accept the new value.
            }
        }

        val notificationPref = findPreference<SwitchPreferenceCompat>("key_new_msg_notif")
        notificationPref?.summaryProvider = Preference.SummaryProvider<SwitchPreferenceCompat> {switchPref ->
            if (switchPref?.isChecked!!)
                "Status: ON"
            else
                "Status: OFF"
        }
    }
}
