package com.redlake.mcms.util;

import com.testflightapp.lib.TestFlight;

import android.app.Application;

public class MCSwitcher extends Application {
	@Override
    public void onCreate() {
        super.onCreate();
        //Initialize TestFlight with your app token.
        TestFlight.takeOff(this, "f9aa085d-0cc8-4525-b942-a5e2e301f402");
    }
}
