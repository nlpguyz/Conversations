package eu.siacs.conversations.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.TextUtils;

import eu.siacs.conversations.Config;
import eu.siacs.conversations.R;

public class SettingsFragment extends PreferenceFragment {

	private String page = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);

		// Remove from standard preferences if the flag ONLY_INTERNAL_STORAGE is not true
		if (!Config.ONLY_INTERNAL_STORAGE) {
			PreferenceCategory mCategory = (PreferenceCategory) findPreference("security_options");
			if (mCategory != null) {
				Preference cleanCache = findPreference("clean_cache");
				Preference cleanPrivateStorage = findPreference("clean_private_storage");
				mCategory.removePreference(cleanCache);
				mCategory.removePreference(cleanPrivateStorage);
			}
		}

		if (!TextUtils.isEmpty(page)) {
			openPreferenceScreen(page);
		}

	}

	public void setActivityIntent(final Intent intent) {
		if (intent != null) {
			if (Intent.ACTION_VIEW.equals(intent.getAction())) {
				if (intent.getExtras() != null) {
					this.page = intent.getExtras().getString("page");
				}
			}
		}
	}

	private void openPreferenceScreen(final String screenName) {
		final Preference pref = findPreference(screenName);
		if (pref instanceof PreferenceScreen) {
			final PreferenceScreen preferenceScreen = (PreferenceScreen) pref;
			getActivity().setTitle(preferenceScreen.getTitle());
			preferenceScreen.setDependency("");
			setPreferenceScreen((PreferenceScreen) pref);
		}
	}
}
