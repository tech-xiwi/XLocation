package tech.xiwi.location.baidu;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import tech.xiwi.location.LocationException;
import tech.xiwi.location.XBaseLocation;
import tech.xiwi.location.XLocationType;

/**
 * Created by xiwi on 2017/12/19.
 */
public class XBaiDuLocation extends XBaseLocation {
    private static final String TAG = "XBaiDuLocation";
    private static XBaiDuLocation sInstance;

    public LocationClient mLocationClient = null;

    private XBaiDuLocationListener mXBaiDuLocationListener;

    private XBaiDuLocation() {
    }

    public static XBaiDuLocation getInstance() {
        if (sInstance == null) {
            synchronized (XBaiDuLocation.class) {
                if (sInstance == null) {
                    sInstance = new XBaiDuLocation();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void init(Context context, XLocationType type) {
        super.init(context, XLocationType.BaiDu);
        if (context == null) {
            throw new LocationException("context is null...");
        }
        if (logger != null)
            logger.d(TAG, "init() called with: context = [" + context + "], type = [" + type + "]");

        mLocationClient = new LocationClient(context.getApplicationContext());
        mXBaiDuLocationListener = new XBaiDuLocationListener();
        //声明LocationClient类
        mLocationClient.registerLocationListener(mXBaiDuLocationListener);
        //注册监听函数
    }

    @Override
    public void startLoc(long intervalTime) {
        super.startLoc(intervalTime);
        if (logger != null)
            logger.d(TAG, "startLoc() called with: intervalTime = [" + intervalTime + "]");
        LocationClientOption clientOption = createLocationClientOption(intervalTime);
        mLocationClient.setLocOption(clientOption);
        mLocationClient.start();
    }

    private LocationClientOption createLocationClientOption(long intervalTime) {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("gcj02");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan((int) intervalTime);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        return option;
    }

    @Override
    public void stopLoc() {
        super.stopLoc();
        if (logger != null)
            logger.d(TAG, "stopLoc() called");
        if (mLocationClient != null || mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    @Override
    public void release() {
        super.release();
        if (logger != null)
            logger.d(TAG, "release() called");
        if (mLocationClient != null) {

            mLocationClient.unRegisterLocationListener(mXBaiDuLocationListener);
            mXBaiDuLocationListener = null;
            mLocationClient = null;
        }
    }
}
