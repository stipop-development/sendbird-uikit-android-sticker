package com.sendbird.uikit_messaging_android;

import android.app.Application;
import android.content.Context;

import io.stipop.Stipop;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class GlobalApplication extends BaseApplication {
    static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Stipop.Companion.configure(this, aBoolean -> null);
    }

}