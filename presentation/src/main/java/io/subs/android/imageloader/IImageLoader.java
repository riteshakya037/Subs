package io.subs.android.imageloader;

import android.widget.ImageView;
import android.widget.ProgressBar;

public interface IImageLoader {
    void loadImage(String url, ImageView holder);

    void loadImage(String url, int resourceIdPlaceHolder, ImageView holder,
            ProgressBar progressBar);

    void loadFirebaseImage(String firebaseRelativeUrl, ImageView holder);

    void loadImage(int resourceId, ImageView holder);
}
