package tech.xiwi.location.baidu;

import android.text.TextUtils;

import tech.xiwi.location.LocationInfo;
import tech.xiwi.location.XLocationFilter;

/**
 * Created by xiwi on 2017/12/19.
 */

class XBaiDuLocationFilter implements XLocationFilter {

    private LocationInfo lastLocation;

    @Override
    public boolean isUseful(LocationInfo location) {
        if (location != null && !TextUtils.isEmpty(location.time)) {
            if (lastLocation == null) {
                lastLocation = location;
                return isLocationFlow(location.satelites, location.radius);
            } else {
                if (!location.equals(lastLocation)) {
                    return isLocationFlow(location.satelites, location.radius);
                }
            }
        }
        return false;
    }

    static boolean isLocationFlow(int satelites, float radius) {
        double y = 1.06434141 + 1.0020713 * satelites + (-0.13005057) * radius;
        double p = 1 / (1 + Math.pow(Math.E, -y));
        if (p > 0.5) {
            return false;
        } else {
            return true;
        }
    }
}
