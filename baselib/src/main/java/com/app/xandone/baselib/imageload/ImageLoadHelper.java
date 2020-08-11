package com.app.xandone.baselib.imageload;

import android.content.Context;
import android.widget.ImageView;

/**
 * author: Admin
 * created on: 2020/8/11 16:35
 * description:
 */
public class ImageLoadHelper implements ImageLoaderInf<ImageView> {
    private AbstracImageLoader imageLoader;

    public static final int ENGINE_GLIDE = 1;

    private ImageLoadHelper() {
    }

    public static ImageLoadHelper getInstance() {
        return Builder.INSTANCE;
    }

    public void initEngine() {
        initEngine(ENGINE_GLIDE);
    }

    public void initEngine(int engine) {
        switch (engine) {
            case ENGINE_GLIDE:
                imageLoader = GlideLoader.getInstance();
                break;
            default:
        }
    }

    @Override
    public void display(Context context, Object file, ImageView view) {
        if (imageLoader != null) {
            imageLoader.display(context, file, view);
        }
    }

    private static class Builder {
        private static final ImageLoadHelper INSTANCE = new ImageLoadHelper();
    }
}
