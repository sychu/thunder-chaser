package com.sychu.thunderchaser;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ThunderChaserConfig extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.config);
    }
}
