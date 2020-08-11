package com.app.xandone.baselib.imageload;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * @author: Admin
 * created on: 2020/8/11 16:34
 * description:
 */
public class GlideLoader extends AbstracImageLoader implements EngineInf<RequestManager> {
    @Override
    public void display(Context context, Object file, ImageView view) {
        Glide.with(context).load(file).into(view);
    }

    private GlideLoader() {
    }

    public static GlideLoader getInstance() {
        return Builder.GLIDELOADER;
    }

    @Override
    public RequestManager getEngine(Context context) {
        return Glide.with(context);
    }

    private static class Builder {
        private static final GlideLoader GLIDELOADER = new GlideLoader();
    }
}
