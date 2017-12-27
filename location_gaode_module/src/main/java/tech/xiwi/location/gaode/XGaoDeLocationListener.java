package tech.xiwi.location.gaode;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import tech.xiwi.location.LocationInfo;
import tech.xiwi.location.XConverter;
import tech.xiwi.location.XLocation;
import tech.xiwi.location.XLocationFilter;

/**
 * Created by xiwi on 2017/12/19.
 */

class XGaoDeLocationListener implements AMapLocationListener {
    private static final String TAG = "XGaoDeLocationListener";
    private XLocationFilter filter = new XGaoDeLocationFilter();
    private LocationInfo lastLocation;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());

//                double latitude = amapLocation.getLatitude();    //获取纬度信息
//                double longitude = amapLocation.getLongitude();    //获取经度信息
//                float radius = amapLocation.getAccuracy();    //获取定位精度，默认值为0.0f
//
//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append("latitude:").append(latitude).append("\nlongitude:").append(longitude)
//                        .append("\nradius:").append(radius)
//                        .append("\ntime:").append(df.format(date))
//                        .append("\nerrorMsg:").append(amapLocation.getErrorInfo())
//                ;
//                XGaoDeLocation.getInstance().getXLogger().d(TAG, stringBuilder.toString());


                double[] gps = XConverter.gcj02_To_Gps84(amapLocation.getLatitude(), amapLocation.getLongitude());

                LocationInfo locationInfo = new LocationInfo();
                locationInfo.lat = amapLocation.getLatitude();
                locationInfo.lon = amapLocation.getLongitude();
                locationInfo.radius = amapLocation.getAccuracy();
                locationInfo.time = df.format(date);
                locationInfo.satelites = amapLocation.getSatellites();
                locationInfo.city = amapLocation.getCity();
                if (filter.isUseful(locationInfo)) {
                    getReqCityUrl(gps[0], gps[1]);
                    XGaoDeLocation.getInstance().getXLogger().d(TAG, "LOCATION = " + locationInfo);
                    XGaoDeLocation.getInstance().getXLogger().d(TAG, "LOCATION gps = " + Arrays.toString(gps));
                    final List<XLocation.XCallback> callbacks = XGaoDeLocation.getInstance().getCallbacks();
                    for (XLocation.XCallback callback : callbacks) {
                        callback.onLocation(lastLocation, locationInfo);
                    }
                    lastLocation = locationInfo;
                } else {
                    XGaoDeLocation.getInstance().getXLogger().w(TAG, "LOCATION = data not change...");
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                XGaoDeLocation.getInstance().getXLogger().e(TAG, "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
                final List<XLocation.XCallback> callbacks = XGaoDeLocation.getInstance().getCallbacks();
                for (XLocation.XCallback callback : callbacks) {
                    callback.onError(amapLocation.getErrorInfo(), null);
                }
            }
        }
    }

    private String getReqCityUrl(double latitude, double longitude) {
        String url = "http://maps.google.cn/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true,language=zh-CN";
        XGaoDeLocation.getInstance().getXLogger().i(TAG, url);
        return url;
    }
}
