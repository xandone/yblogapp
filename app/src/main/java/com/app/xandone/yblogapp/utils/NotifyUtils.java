package com.app.xandone.yblogapp.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.app.xandone.yblogapp.R;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

/**
 * @author: xiao
 * created on: 2022/6/29 09:37
 * description:
 */
public class NotifyUtils {
    /**
     * 用户现在可以按渠道关闭通知，而不是关闭应用的所有通知。
     * https://developer.android.google.cn/training/notify-user/build-notification
     */
    private static final String MESSAGE_CHANNEL_ID = "push_msg";

    public static void t1(Context ctx, int id, String title, String content, Intent intent) {
        NotificationManager notificationManager = ContextCompat.getSystemService(ctx, NotificationManager.class);
        if (notificationManager == null) {
            return;
        }
        String channelId = "";
        // 适配 Android 8.0 通知渠道新特性
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(MESSAGE_CHANNEL_ID, "消息", NotificationManager.IMPORTANCE_LOW);
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});
            channel.setSound(null, null);
            notificationManager.createNotificationChannel(channel);
            channelId = channel.getId();
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ctx, channelId)
                // 设置通知时间
                .setWhen(System.currentTimeMillis())
                // 设置通知标题
                .setContentTitle(title)
                .setContentText(content)
                // 设置通知小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                // 设置通知大图标
//                .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher))
                // 设置通知静音
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                // 设置震动频率
                .setVibrate(new long[]{0})
                // 设置声音文件
                .setSound(null)
                // 设置通知的优先级
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));

        notificationManager.notify(id, notificationBuilder.build());
    }
}
