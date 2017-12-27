package tech.xiwi.location.util;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * Created by android_ls on 16/6/25.
 */
public class ZeusAndroidInfo {

    private static final String TAG = ZeusAndroidInfo.class.getSimpleName();

    /**
     * 获取用户账号信息
     * <p>
     * 所需权限：android.permission.GET_ACCOUNTS
     *
     * @param context Context
     */
    public void getAccountInfo(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccounts();
        for (Account account : accounts) {
            // 账号名字
            String accountName = account.name;
            // 账号类型
            String accountType = account.type;

            Log.i(TAG, "accountName = " + accountName);
            Log.i(TAG, "accountType = " + accountType);
        }
    }

    /**
     * 获取用户使用WiFi时的网络连接信息
     *
     * @param context Context
     */
    public void getWifiNetInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // wifi状态，返回2或者3表示wifi可用
        int status = wifiManager.getWifiState();

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // mac地址
        String macAddress = wifiInfo.getMacAddress();
        // 返回基本服务集标识符(BSSID)当前的访问点。目前如果没有网络连接，该标识符返回0
        String bissId = wifiInfo.getBSSID();
        // 获取Ip地址
        int ipAddress = wifiInfo.getIpAddress();
        // 每个配置的网络都有一个唯一的标识，用于识别网络上执行操作时的请求者。
        // 这个方法返回当前连接网络的ID
        int networkId = wifiInfo.getNetworkId();
        // 返回当前的接收信号强度
        int rssi = wifiInfo.getRssi();
        // 当前的服务集标识符
        String ssId = wifiInfo.getSSID();
        // 当前的链接速度（网速），单位Mbps
        int linkSpeed = wifiInfo.getLinkSpeed();

        Log.i(TAG, "status = " + status);
        Log.i(TAG, "macAddress = " + macAddress);
        Log.i(TAG, "bissId = " + bissId);
        Log.i(TAG, "ipAddress = " + ipAddress);
        Log.i(TAG, "networkId = " + networkId);
        Log.i(TAG, "rssi = " + rssi);
        Log.i(TAG, "ssId = " + ssId);
        Log.i(TAG, "linkSpeed = " + linkSpeed);

    }

    public void getDeviceInfo(Context context) {
        // 硬件制造商
        String manufacturer = Build.MANUFACTURER;
        // 系统定制商
        String brand = Build.BRAND;
        // 主板
        String board = Build.BOARD;
        // 机型信息
        String model = Build.MODEL;
        // 显示屏参数
        String display = Build.DISPLAY;
        // Android版本
        String release = Build.VERSION.RELEASE;
        // Android SDK版本
        int sdkInt = Build.VERSION.SDK_INT;

        // 获取用户的语言环境
        Locale locale = context.getResources().getConfiguration().locale;
        String country = locale.getCountry();
        String displayLanguage = locale.getDisplayLanguage();
        String displayName = locale.getDisplayName();
        String displayVariant = locale.getDisplayVariant();

        int mnc = context.getResources().getConfiguration().mnc;
        int mcc = context.getResources().getConfiguration().mcc;
        // 横竖屏
        int orientation = context.getResources().getConfiguration().orientation;
        int densityDpi = context.getResources().getConfiguration().densityDpi;
        float fontScale = context.getResources().getConfiguration().fontScale;

        Log.i(TAG, "manufacturer = " + manufacturer);
        Log.i(TAG, "brand = " + brand);
        Log.i(TAG, "board = " + board);
        Log.i(TAG, "model = " + model);
        Log.i(TAG, "display = " + display);
        Log.i(TAG, "release = " + release);
        Log.i(TAG, "sdkInt = " + sdkInt);
        Log.i(TAG, "country = " + country);
        Log.i(TAG, "displayLanguage = " + displayLanguage);
        Log.i(TAG, "displayName = " + displayName);
        Log.i(TAG, "displayVariant = " + displayVariant);
        Log.i(TAG, "displayName = " + displayName);
        Log.i(TAG, "mnc = " + mnc);
        Log.i(TAG, "mcc = " + mcc);
        Log.i(TAG, "orientation = " + orientation);
        Log.i(TAG, "densityDpi = " + densityDpi);
        Log.i(TAG, "fontScale = " + fontScale);
    }


    public void getTelephonyInfo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        // 手机串号：GSM手机的IMEI 和 CDMA手机的 MEID.
        String deviceId = telephonyManager.getDeviceId();
        // 返回设备的软件版本号，例如GSM手机的IMEI/SV
        String imei = telephonyManager.getDeviceSoftwareVersion();

        /* 手机类型：
         * @see #PHONE_TYPE_NONE // 无信号
         * @see #PHONE_TYPE_GSM  // GSM信号
         * @see #PHONE_TYPE_CDMA // CDMA信号
         * @see #PHONE_TYPE_SIP  // SIP信号
         */
        int phoneType = telephonyManager.getPhoneType();
        String imsi = telephonyManager.getSubscriberId();

        // 运营商名称（仅当用户已在网络注册时有效，在CDMA网络中结果也许不可靠）
        String networkOperatorName = telephonyManager.getNetworkOperatorName();
        // 注册网络的MCC+MNC
        String networkOperator = telephonyManager.getNetworkOperator();
        // 网络国家代码，MCC
        String networkCountryIso = telephonyManager.getNetworkCountryIso();

        /*
         * 当前使用的网络类型：
         * NETWORK_TYPE_UNKNOWN  网络类型未知  0
           NETWORK_TYPE_GPRS     GPRS网络  1
           NETWORK_TYPE_EDGE     EDGE网络  2
           NETWORK_TYPE_UMTS     UMTS网络  3
           NETWORK_TYPE_HSDPA    HSDPA网络  8
           NETWORK_TYPE_HSUPA    HSUPA网络  9
           NETWORK_TYPE_HSPA     HSPA网络  10
           NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
           NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
           NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
           NETWORK_TYPE_1xRTT    1xRTT网络  7
         */
        int networkType = telephonyManager.getNetworkType();

        // 当前网络是否处于漫游
        boolean isNetworkRoaming = telephonyManager.isNetworkRoaming();

        // 获取手机SIM卡的序列号
        String simSerialNumber = telephonyManager.getSimSerialNumber();
        // 是否有ICC卡（sim卡）
        boolean isSim = telephonyManager.hasIccCard();

        /*
         * 获取Sim卡状态，SIM的状态信息：
         SIM_STATE_UNKNOWN          未知状态 0
         SIM_STATE_ABSENT           没插卡 1
         SIM_STATE_PIN_REQUIRED     锁定状态，需要用户的PIN码解锁 2
         SIM_STATE_PUK_REQUIRED     锁定状态，需要用户的PUK码解锁 3
         SIM_STATE_NETWORK_LOCKED   锁定状态，需要网络的PIN码解锁 4
         SIM_STATE_READY            就绪状态 5
         */
        int simState = telephonyManager.getSimState();

        // 获取ISO国家码，相当于提供SIM卡的国家码。
        String simCountryIso = telephonyManager.getSimCountryIso();

        // 通话状态：无活动、响铃、摘机（接通）
        int callState = telephonyManager.getCallState();

        // 获取本机的手机号（有些手机号无法获取，是因为运营商在SIM中没有写入手机号）
        String phoneNumber = telephonyManager.getLine1Number();
        //获取语音邮件号码：
        String voiceMailNumber = telephonyManager.getVoiceMailNumber();

        /*
         * 获取数据连接的活动类型
         *
         * @see #DATA_ACTIVITY_NONE
         * @see #DATA_ACTIVITY_IN // 下行流量
         * @see #DATA_ACTIVITY_OUT // 上行流量
         * @see #DATA_ACTIVITY_INOUT
         * @see #DATA_ACTIVITY_DORMANT // 休眠
         */
        int dataActivty = telephonyManager.getDataActivity();

    }

    public List<Map<String, Object>> getInstalledApps(Context context) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>(packages.size());
        for (int j = 0; j < packages.size(); j++) {
            PackageInfo packageInfo = packages.get(j);
            // 显示非系统软件
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                String packageName = packageInfo.packageName;
                Drawable appIcon = packageInfo.applicationInfo.loadIcon(context.getPackageManager()).getCurrent();
                Log.i(TAG, "appName = " + appName);
                Log.i(TAG, "packageName = " + packageName);
            }
        }
        return listMap;
    }


    public void getInstalledApplications(Context context) {
        PackageManager packManager = context.getPackageManager();
        List<ApplicationInfo> infos = packManager.getInstalledApplications(PackageManager.GET_ACTIVITIES);
        // 获取到安装的App列表
        Log.i(TAG, infos.size() + "");
        for (ApplicationInfo info : infos) {
            Log.i(TAG, "appName--->" + packManager.getApplicationLabel(info));

            try {
                PackageInfo packInfo = packManager.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS);
                String permissons[] = packInfo.requestedPermissions;
                // 获取该app的所有权限
                int length = permissons.length;
                for (int i = 0; i < length; i++) {
                    Log.i(TAG, permissons[i]);
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取正在运行的应用程序
     *
     * @param context
     */
    public void getRunningProcess(Context context) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        List<ApplicationInfo> applications = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取正在运行的应用
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo ra : runningAppProcesses) {
            String processName = ra.processName;
            for (ApplicationInfo applicationInfo : applications) {
                if (processName.equals(applicationInfo.processName)) {
                    String appName = applicationInfo.loadLabel(context.getPackageManager()).toString();
                    String packageName = applicationInfo.packageName;
                    Drawable appIcon = applicationInfo.loadIcon(context.getPackageManager()).getCurrent();
                    Log.i(TAG, "appName = " + appName);
                    Log.i(TAG, "packageName = " + packageName);
                }
            }
        }
    }

    /**
     * 获取用户的当前位置信息
     *
     * @param context Context
     */
    public void getLocationInfo(final Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        final LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 设置查询条件（我想要你当前位置的那些信息）
        String bestProvider = lm.getBestProvider(getCriteria(), true);

        // 获取位置信息
        // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
        Location location = lm.getLastKnownLocation(bestProvider);
        getLoactionInfo(location);

        // 监听状态
        lm.addGpsStatusListener(new GpsStatus.Listener() {
            public void onGpsStatusChanged(int event) {
                switch (event) {
                    // 第一次定位
                    case GpsStatus.GPS_EVENT_FIRST_FIX:
                        Log.i(TAG, "第一次定位");
                        break;
                    // 卫星状态改变
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        Log.i(TAG, "卫星状态改变");

                        // 获取当前状态
                        GpsStatus gpsStatus = lm.getGpsStatus(null);
                        // 获取卫星颗数的默认最大值
                        int maxSatellites = gpsStatus.getMaxSatellites();
                        Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                        int count = 0;
                        while (iters.hasNext() && count <= maxSatellites) {
                            count++;
                        }
                        Log.i(TAG, "搜索到：" + count + "颗卫星");
                        break;
                    // 定位启动
                    case GpsStatus.GPS_EVENT_STARTED:
                        Log.i(TAG, "定位启动");
                        break;
                    // 定位结束
                    case GpsStatus.GPS_EVENT_STOPPED:
                        Log.i(TAG, "定位结束");
                        break;
                }
            }

            ;
        });

        // 默认采用网络连接定位，若GPS可用则使用GPS定位
        String provider = LocationManager.NETWORK_PROVIDER;
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }

        /**
         * 参数1：设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
         * 参数2：位置信息更新周期，单位毫秒
         * 参数3：位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
         * 参数4：位置变化监听器
         *
         * 注：如果参数3不为0，则以参数3为准；参数3为0，则通过时间（参数2）来定时更新；两者为0，则随时刷新
         * 3分钟更新一次或者最小位移变化超过10米更新一次
         */
        lm.requestLocationUpdates(provider, 3 * 60 * 1000, 10, new LocationListener() {

            /**
             * 位置信息变化时触发
             */
            public void onLocationChanged(Location location) {
                getLoactionInfo(location);
            }

            /**
             * GPS状态变化时触发
             */
            public void onStatusChanged(String provider, int status, Bundle extras) {
                switch (status) {
                    // GPS状态为可见时
                    case LocationProvider.AVAILABLE:
                        Log.i(TAG, "当前GPS状态为可见状态");
                        break;
                    // GPS状态为服务区外时
                    case LocationProvider.OUT_OF_SERVICE:
                        Log.i(TAG, "当前GPS状态为服务区外状态");
                        break;
                    // GPS状态为暂停服务时
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Log.i(TAG, "当前GPS状态为暂停服务状态");
                        break;
                }
            }

            /**
             * GPS开启时触发
             */
            public void onProviderEnabled(String provider) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                Location location = lm.getLastKnownLocation(provider);
                getLoactionInfo(location);
            }

            /**
             * GPS禁用时触发
             */
            public void onProviderDisabled(String provider) {

            }
        });
    }

    private void getLoactionInfo(Location location) {
        Log.i(TAG, "时间：" + location.getTime());
        Log.i(TAG, "经度：" + location.getLongitude());
        Log.i(TAG, "纬度：" + location.getLatitude());
        Log.i(TAG, "海拔：" + location.getAltitude());
    }

    /**
     * 返回查询条件
     *
     * @return
     */
    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(true);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(true);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(true);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }


}
