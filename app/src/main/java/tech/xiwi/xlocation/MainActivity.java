package tech.xiwi.xlocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import tech.xiwi.location.LocationException;
import tech.xiwi.location.LocationInfo;
import tech.xiwi.location.XLocation;
import tech.xiwi.location.baidu.XBaiDuLocation;
import tech.xiwi.location.gaode.XGaoDeLocation;

public class MainActivity extends AppCompatActivity implements XLocation.XCallback {
    private static final String TAG = "MainActivity";
    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 1001;
    XLocation locations[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locations = new XLocation[]{XBaiDuLocation.getInstance(), XGaoDeLocation.getInstance()};
        for (XLocation loc : locations) {
            loc.init(this, null);
            loc.addCallback(this);
        }
    }

    @Override
    public void onLocation(LocationInfo preLoc, LocationInfo currentLoc) {
        Log.d(TAG, "onLocation() called with: preLoc = [" + preLoc + "], \ncurrentLoc = [" + currentLoc + "]");
    }

    @Override
    public void onError(String msg, LocationException e) {
        Log.e(TAG, "onError() called with: msg = [" + msg + "], e = [" + e + "]");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
            return;
        }

        for (XLocation loc : locations) {
            loc.startLoc(5000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (XLocation loc : locations) {
            loc.stopLoc();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (XLocation loc : locations) {
            loc.removeCallback(this);
            loc.release();
            if (loc.getXLogger() != null)
                loc.getXLogger().i("", "--------------------------------------------------");
        }
    }
}
