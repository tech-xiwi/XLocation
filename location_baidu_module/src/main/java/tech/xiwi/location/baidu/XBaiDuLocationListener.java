package tech.xiwi.location.baidu;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

import java.util.Arrays;
import java.util.List;

import tech.xiwi.location.LocationInfo;
import tech.xiwi.location.XConverter;
import tech.xiwi.location.XLocation;
import tech.xiwi.location.XLocationFilter;

/**
 * Created by xiwi on 2017/12/19.
 */

class XBaiDuLocationListener extends BDAbstractLocationListener {
    private static final String TAG = "XBaiDuLocationListener";
    private XLocationFilter filter = new XBaiDuLocationFilter();
    private LocationInfo lastLocation;

    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location == null) {
            XBaiDuLocation.getInstance().getXLogger().e(TAG, "loc is null...");
            return;
        }
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        //以下只列举部分获取经纬度相关（常用）的结果信息
        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
//        double latitude = location.getLatitude();    //获取纬度信息
//        double longitude = location.getLongitude();    //获取经度信息
//        float radius = location.getRadius();    //获取定位精度，默认值为0.0f
//
//        String coorType = location.getCoorType();
//        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
//
//        int errorCode = location.getLocType();
//        //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("latitude:").append(latitude).append("\nlongitude:").append(longitude)
//                .append("\nradius:").append(radius)
//                .append("\ncoorType:").append(coorType)
//                .append("\ntime:").append(location.getTime())
//                .append("\nerrorMsg:").append(getErrorMsg(errorCode));
//
//        XBaiDuLocation.getInstance().getXLogger().d(TAG, stringBuilder.toString());

        double[] gps = XConverter.gcj02_To_Gps84(location.getLatitude(), location.getLongitude());

        LocationInfo locationInfo = new LocationInfo();
        locationInfo.lat = location.getLatitude();
        locationInfo.lon = location.getLongitude();
        locationInfo.radius = location.getRadius();
        locationInfo.time = location.getTime();
        locationInfo.satelites = location.getSatelliteNumber();
        locationInfo.city = location.getCity();
        if (filter.isUseful(locationInfo)) {
            XBaiDuLocation.getInstance().getXLogger().d(TAG, "LOCATION = " + locationInfo);
            XBaiDuLocation.getInstance().getXLogger().d(TAG, "LOCATION gps = " + Arrays.toString(gps));

            final List<XLocation.XCallback> callbacks = XBaiDuLocation.getInstance().getCallbacks();
            for (XLocation.XCallback callback : callbacks) {
                callback.onLocation(lastLocation, locationInfo);
            }
            lastLocation = locationInfo;
        } else {
            XBaiDuLocation.getInstance().getXLogger().w(TAG, "LOCATION = data not change...");
            final List<XLocation.XCallback> callbacks = XBaiDuLocation.getInstance().getCallbacks();
            for (XLocation.XCallback callback : callbacks) {
                callback.onError(getErrorMsg(location.getLocType()), null);
            }
        }
    }

    private String getErrorMsg(int errorCode) {
        switch (errorCode) {
            case 61:
                return "GPS定位结果，GPS定位成功";
            case 62:
                return "无法获取有效定位依据，定位失败，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位";
            case 63:
                return "网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位";
            case 66:
                return "离线定位结果。通过requestOfflineLocation调用时对应的返回结果";
            case 68:
                return "网络连接失败时，查找本地离线定位时对应的返回结果";
            case 161:
                return "网络定位结果，网络定位成功";
            case 162:
                return "请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件";
            case 167:
                return "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位";
            case 505:
                return "AK不存在或者非法，请按照说明文档重新申请AK";
        }
        return "";
    }

}
