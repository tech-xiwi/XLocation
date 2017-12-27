package tech.xiwi.location.gaode;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import tech.xiwi.location.XBaseLocation;
import tech.xiwi.location.XLocationType;

/**
 * Created by xiwi on 2017/12/19.
 */
public class XGaoDeLocation extends XBaseLocation {
    private static final String TAG = "XGaoDeLocation";
    private static XGaoDeLocation sInstance;

    public AMapLocationClient mLocationClient;

    private AMapLocationListener mAMapLocationListener = new XGaoDeLocationListener();

    private XGaoDeLocation() {

    }

    public static XGaoDeLocation getInstance() {
        if (sInstance == null) {
            synchronized (XGaoDeLocation.class) {
                if (sInstance == null) {
                    sInstance = new XGaoDeLocation();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void init(Context context, XLocationType type) {
        super.init(context, XLocationType.GaoDe);
        if (logger != null)
            logger.d(TAG, "init() called with: context = [" + context + "], type = [" + type + "]");
        mLocationClient = new AMapLocationClient(context.getApplicationContext());

    }

    @Override
    public void startLoc(long intervalTime) {
        super.startLoc(intervalTime);
        if (logger != null)
            logger.d(TAG, "startLoc() called with: intervalTime = [" + intervalTime + "]");

        if (mLocationClient != null) {
            mLocationClient.setLocationOption(createAMapLocationClientOption(intervalTime));
            mLocationClient.startLocation();
        }
    }

    private AMapLocationClientOption createAMapLocationClientOption(long intervalTime) {
        //初始化定位参数
        AMapLocationClientOption clientOption = new AMapLocationClientOption();
        //设置定位监听
        if (mLocationClient != null) {
            mLocationClient.setLocationListener(mAMapLocationListener);
        }
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        clientOption.setInterval(intervalTime);
        return clientOption;
    }

    @Override
    public void stopLoc() {
        super.stopLoc();
        if (logger != null)
            logger.d(TAG, "stopLoc() called");
        if (mLocationClient != null && mLocationClient.isStarted())
            mLocationClient.stopLocation();
    }

    @Override
    public void release() {
        super.release();
        if (logger != null)
            logger.d(TAG, "release() called");
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mAMapLocationListener);
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }
}
