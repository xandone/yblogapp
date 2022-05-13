package com.app.xandone.baselib.imageload.glide;

import android.content.Context;
import android.os.Build;

import com.app.xandone.baselib.R;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import androidx.annotation.NonNull;

/**
 * author: Admin
 * created on: 2021/4/22 17:21
 * description:
 */
@GlideModule
public class GlideConfig extends AppGlideModule {
    /**
     * 本地图片缓存文件最大值
     */
    private static final int GLIDE_IMAGE_DISK_CACHE_MAX_SIZE = 500 * 1024 * 1024;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        // 读写外部缓存目录不需要申请存储权限
        File diskCacheFile = new File(context.getExternalCacheDir(), "glide");
        // 如果这个路径是一个文件
        if (diskCacheFile.exists() && diskCacheFile.isFile()) {
            // 执行删除操作
            diskCacheFile.delete();
        }
        // 如果这个路径不存在
        if (!diskCacheFile.exists()) {
            // 创建多级目录
            diskCacheFile.mkdirs();
        }
        builder.setDiskCache(() -> DiskLruCacheWrapper.create(diskCacheFile, GLIDE_IMAGE_DISK_CACHE_MAX_SIZE));

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setDefaultRequestOptions(new RequestOptions()
                    // 设置默认加载中占位图
                    .placeholder(R.drawable.image_loading_bg)
                    // 设置默认加载出错占位图
                    .error(R.drawable.image_error_bg));
        }
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
