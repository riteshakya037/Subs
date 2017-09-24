package io.subs.android.imageloader;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import io.subs.data.DatabaseNames;

/**
 * @author Ritesh Shakya
 */

public class GlideLoader implements IImageLoader {
    private final Application context;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public GlideLoader(Application context) {
        this.context = context;
        Glide.get(context).setMemoryCategory(MemoryCategory.LOW);
    }

    @Override public void loadImage(String url, ImageView holder) {
        clear(holder);

        Glide.with(context).load(url).centerCrop().crossFade().into(holder);
    }

    @Override public void loadImage(String url, int resourceIdPlaceHolder, ImageView holder,
            final ProgressBar progressBar) {
        clear(holder);
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(resourceIdPlaceHolder)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override public boolean onException(Exception e, String model,
                            Target<GlideDrawable> target, boolean isFirstResource) {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        return false;
                    }

                    @Override public boolean onResourceReady(GlideDrawable resource, String model,
                            Target<GlideDrawable> target, boolean isFromMemoryCache,
                            boolean isFirstResource) {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        return false;
                    }
                })
                .into(holder);
    }

    @Override public void loadFirebaseImage(String firebaseRelativeUrl, ImageView holder) {
        if (TextUtils.isEmpty(firebaseRelativeUrl)) {
            firebaseRelativeUrl = DatabaseNames.PATH_DEFAULT_IMAGE;
        }
        clear(holder);
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageReference.child(firebaseRelativeUrl))
                .into(holder);
    }

    @Override public void loadImage(int resourceId, ImageView holder) {
        clear(holder);

        Glide.with(context).load(resourceId).crossFade().into(holder);
    }

    private void clear(ImageView holder) {
        Glide.clear(holder);
        // remove the placeholder (optional); read comments below
        holder.setImageDrawable(null);
    }
}
