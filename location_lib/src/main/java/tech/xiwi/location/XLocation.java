package tech.xiwi.location;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiwi on 2017/12/19.
 */
public interface XLocation {

    List<XCallback> callbacks = new ArrayList<>();

    void init(Context context, XLocationType type);

    void setLogger(XLogger logger);

    XLogger getXLogger();

    void startLoc(long intervalTime);

    void stopLoc();

    void addCallback(XCallback callback);

    void removeCallback(XCallback callback);

    List<XCallback> getCallbacks();

    void release();

    public static interface XCallback {
        void onLocation(LocationInfo preLoc, LocationInfo currentLoc);

        void onError(String msg, LocationException e);
    }
}
