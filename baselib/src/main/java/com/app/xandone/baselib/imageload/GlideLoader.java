package com.app.xandone.baselib.imageload;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import androidx.annotation.Nullable;

/**
 * @author: Admin
 * created on: 2020/8/11 16:34
 * description:
 */
public class GlideLoader extends AbstractImageLoader implements IEngine<RequestManager> {
    private GlideLoader() {
    }

    public static GlideLoader getInstance() {
        return Builder.GLIDELOADER;
    }

    @Override
    public void display(Context context, Object file, ImageView view) {
        Glide.with(context).load(file).into(view);
    }

    @Override
    public void loadSource(Context context, Object file, final SourceCallback callback) {
        if (callback != null) {
            callback.onStart();
        }
        Glide.with(context).download(file).listener(new RequestListener<File>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                if (callback != null) {
                    callback.onDelivered(false, null);
                }
                return false;
            }

            @Override
            public boolean onResourceReady(final File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                if (callback != null) {
                    callback.onDelivered(true, resource);
                }
                return false;
            }
        }).preload();
    }

    @Override
    public RequestManager getEngine(Context context) {
        return Glide.with(context);
    }

    private static class Builder {
        private static final GlideLoader GLIDELOADER = new GlideLoader();
    }
}
