package ca.panthera.floatingopennotificationdrawer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;


public class FloatingWidgetShowService extends Service {

    private boolean isServiceStarted;
    WindowManager windowManager;
    View floatingView, collapsedView;
    WindowManager.LayoutParams params;
    private GestureDetector tapDetector;

    private static class TapGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    }

    public FloatingWidgetShowService() {
        isServiceStarted = false;
    }

    /**
     * Creates a persistent notification
     * @return notification
     */
    private Notification createNotification() {

        String notificationChannelId = "ENDLESS SERVICE CHANNEL";

        // depending on the Android API that we're dealing with we will have
        // to use a specific method to create the notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(
                    notificationChannelId,
                    "Floating Open Notification Drawer notifications channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Floating Open Notification Drawer channel");
            notificationManager.createNotificationChannel(channel);

        }

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(
                    this, notificationChannelId);
        }  else {
            builder = new Notification.Builder(this);
        }

        return builder
                .setContentTitle("Floating Open Notification Drawer")
                .setContentText("This is your favorite endless service working")
                // .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Ticker text")
                .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification notification = createNotification();
        startForeground(1, notification);

//        if (intent != null) {
//            String action = intent.getAction();
//
//            // check if specific action launched
//            if (action != null) {
//                if (Actions.START.name().equals(action)) {
//                    startService();
//                } else if (Actions.STOP.name().equals(action)) {
//                    stopService();
//                }
//            }
//        }

        // keep running until explicitly stopped
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_widget_layout, null);
//        tapDetector = new GestureDetector(this, new TapGestureDetector());
//        tapDetector.setIsLongpressEnabled(true);
//
//        params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);
//
//        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//
//        windowManager.addView(floatingView, params);
//
//        collapsedView = floatingView.findViewById(R.id.Layout_Collapsed);
//
//        floatingView.findViewById(R.id.Widget_Close_Icon).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stopSelf();
//            }
//        });
//
//        floatingView.findViewById(R.id.MainParentRelativeLayout).setOnTouchListener(new View.OnTouchListener() {
//            int X_Axis, Y_Axis;
//            float TouchX, TouchY;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//
//                if (tapDetector.onTouchEvent(event)) {
//
//                    // Expand status bar
//                    try {
//                        Object sbservice = getSystemService("statusbar");
//                        Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
//                        Method expandMethod;
//                        if (Build.VERSION.SDK_INT >= 17) {
//                            expandMethod = statusBarManager.getMethod("expandNotificationsPanel");
//                        } else {
//                            expandMethod = statusBarManager.getMethod("expand");
//                        }
//                        expandMethod.invoke(sbservice);
//                    } catch (Exception e) {
//                    }
//
//                    return true;
//                } else {
//
//                    // follow motion
//                    switch (event.getAction()) {
//
//                        case MotionEvent.ACTION_DOWN:
//                            X_Axis = params.x;
//                            Y_Axis = params.y;
//                            TouchX = event.getRawX();
//                            TouchY = event.getRawY();
//                            return true;
//
//                        case MotionEvent.ACTION_MOVE:
//
//                            params.x = X_Axis + (int) (event.getRawX() - TouchX);
//                            params.y = Y_Axis + (int) (event.getRawY() - TouchY);
//                            windowManager.updateViewLayout(floatingView, params);
//                            return true;
//                    }
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (floatingView != null) windowManager.removeView(floatingView);
    }

    private void startService() {
        if (isServiceStarted) {
            return;
        }
        isServiceStarted = true;
    }

    private void stopService() {
        isServiceStarted = false;
    }

}