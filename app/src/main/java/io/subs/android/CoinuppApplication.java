package io.subs.android;

import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * @author Ritesh Shakya
 */

public class CoinuppApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder().setDefaultFontPath("fonts/ProductSansRegular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());
    }
}
