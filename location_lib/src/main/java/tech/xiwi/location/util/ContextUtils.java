package tech.xiwi.location.util;

import android.content.Context;

/**
 * Created by xiwi on 2017/12/19.
 */

public class ContextUtils {
    private static ContextUtils sInstance;

    private ContextUtils() {
    }

    public static ContextUtils getInstance() {
        if (sInstance == null) {
            synchronized (ContextUtils.class) {
                if (sInstance == null) {
                    sInstance = new ContextUtils();
                }
            }
        }
        return sInstance;
    }

    Context sContext;

    public synchronized void init(Context context) {
        this.sContext = context.getApplicationContext();
    }

    public Context getContext() {
        return sContext;
    }
}
