package tech.xiwi.location;

import android.util.Log;

/**
 * Created by xiwi on 2017/12/19.
 */
public class DefaultLogger implements XLogger {

    private boolean openLogger = true;

    @Override
    public void setOpen(boolean open) {
        openLogger = open;
    }

    @Override
    public int i(String tag, String msg) {
        if (openLogger) {
            Log.i(TAG + " : " + tag, msg);
        }
        return 0;
    }

    @Override
    public int d(String tag, String msg) {
        if (openLogger) {
            Log.d(TAG + " : " + tag, msg);
        }
        return 0;
    }

    @Override
    public int w(String tag, String msg) {
        if (openLogger) {
            Log.w(TAG + " : " + tag, msg);
        }
        return 0;
    }

    @Override
    public int e(String tag, String msg) {
        if (openLogger) {
            Log.e(TAG + " : " + tag, msg);
        }
        return 0;
    }
}
