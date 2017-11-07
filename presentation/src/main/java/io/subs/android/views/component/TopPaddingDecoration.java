package io.subs.android.views.component;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Ritesh Shakya
 */

public class TopPaddingDecoration extends RecyclerView.ItemDecoration {
    private final int topPadding;

    @SuppressWarnings("SameParameterValue") public TopPaddingDecoration(int topPadding) {
        this.topPadding = topPadding;
    }

    @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position == 0) {
            outRect.set(0, topPadding, 0, 0);
        }
    }
}
