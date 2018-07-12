package com.opera.app.customwidget;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface ClipManager {

    @NonNull
    Bitmap createMask(int width, int height);

    @Nullable
    Path getShadowConvexPath();

    void setupClipLayout(int width, int height);
}