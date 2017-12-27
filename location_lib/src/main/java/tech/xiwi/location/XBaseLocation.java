package tech.xiwi.location;

import android.content.Context;

import java.util.List;

import tech.xiwi.location.util.ContextUtils;

/**
 * Created by xiwi on 2017/12/19.
 */

public class XBaseLocation implements XLocation {
    private static final String TAG = "XBaseLocation";
    protected XLogger logger = new DefaultLogger();
    protected XLocationType mLocType = null;
    protected LocationInfo mPreLoc = null;
    protected Context mContext = null;

    @Override
    public void init(Context context, XLocationType type) {
        this.mContext = context;
        this.mLocType = type;
        ContextUtils.getInstance().init(context);
    }

    @Override
    public void setLogger(XLogger logger) {
        this.logger = logger;
    }

    @Override
    public XLogger getXLogger() {
        return logger;
    }

    @Override
    public void startLoc(long intervalTime) {

    }

    @Override
    public void stopLoc() {

    }

    @Override
    public void addCallback(XCallback callback) {
        if (callback != null) {
            if (logger != null)
                logger.d(TAG, "addCallback() called with: callback = [" + callback + "]");
            callbacks.add(callback);
        }
    }

    @Override
    public void removeCallback(XCallback callback) {
        if (callback != null) {
            if (logger != null)
                logger.d(TAG, "removeCallback() called with: callback = [" + callback + "]");
            callbacks.remove(callback);
        }
    }

    @Override
    public List<XCallback> getCallbacks() {
        return callbacks;
    }

    @Override
    public void release() {
        callbacks.clear();
        mLocType = null;
        mPreLoc = null;
        mContext = null;
    }
}
