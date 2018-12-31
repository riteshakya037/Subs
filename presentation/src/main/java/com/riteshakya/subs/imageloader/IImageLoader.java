package com.riteshakya.subs.imageloader;

import android.widget.ImageView;

public interface IImageLoader {

    void loadFirebaseImage(String firebaseRelativeUrl, ImageView holder);
}
