package io.subs.android.imageloader;

import android.widget.ImageView;

public interface IImageLoader {

    void loadFirebaseImage(String firebaseRelativeUrl, ImageView holder);
}
