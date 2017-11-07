package io.subs.android.imageloader;

import android.app.Application;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * @author Ritesh Shakya
 */

public class GlideLoader implements IImageLoader {
    private final Application context;
    private final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public GlideLoader(Application context) {
        this.context = context;
        Glide.get(context).setMemoryCategory(MemoryCategory.LOW);
    }

    @Override public void loadFirebaseImage(String firebaseRelativeUrl, ImageView holder) {
        clear(holder);
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageReference.child(firebaseRelativeUrl))
                .into(holder);
    }

    private void clear(ImageView holder) {
        Glide.clear(holder);
        // remove the placeholder (optional); read comments below
        holder.setImageDrawable(null);
    }
}
