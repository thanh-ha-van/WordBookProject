package thanh.ha.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import thanh.ha.ui.dialogs.DialogMessage;

public class HandlePermission {
    private static final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,};
    private static final int REQUEST_ID_PERMISSION = 123;
    private Activity mActivity;
    private DialogMessage dialogMessage;
    private CallbackRequestPermission callbackRequestPermission;

    public HandlePermission(Activity context, CallbackRequestPermission callbackRequestPermision) {
        mActivity = context;
        dialogMessage = new DialogMessage(context);
        this.callbackRequestPermission = callbackRequestPermision;
        if (!askPermission()) {
            callbackRequestPermision.onRequestPermissionSuccess();
        }
    }

    private boolean askPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> requestPermission = new ArrayList<>();
            for (int i = 0; i < PERMISSIONS.length; i++) {
                int permission = ActivityCompat.checkSelfPermission(mActivity, PERMISSIONS[i]);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    requestPermission.add(PERMISSIONS[i]);
                }
            }
            if (requestPermission.size() > 0) {
                String[] permissions = new String[requestPermission.size()];
                permissions = requestPermission.toArray(permissions);
                mActivity.requestPermissions(permissions, REQUEST_ID_PERMISSION);
                return true;
            }
        }
        return false;
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && Build.VERSION.SDK_INT >= 23) {
            int permissionEnable = 0;
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = mActivity.shouldShowRequestPermissionRationale(permissions[i]);
                    if (showRationale) {
                        callbackRequestPermission.onRequestPermissionFail();
                    } else {
                        showDialogSetting();
                    }
                } else {
                    permissionEnable++;
                }
            }
            if (permissionEnable == permissions.length) {
                callbackRequestPermission.onRequestPermissionSuccess();
            }
        }
    }

    private void showDialogSetting() {
        dialogMessage.setMessage("You have to enable permission for using this feature.");
        dialogMessage.setOnClickButton(new DialogMessage.OnClickButton() {
            @Override
            public void onClickNegative() {
                mActivity.finish();
                callbackRequestPermission.onRequestPermissionFail();
            }

            @Override
            public void onClickPositive() {
                startInstalledAppDetailsActivity();
            }
        });
        dialogMessage.showDialog();
    }

    private void startInstalledAppDetailsActivity() {
        final Intent settingActivity = new Intent();
        settingActivity.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        settingActivity.addCategory(Intent.CATEGORY_DEFAULT);
        settingActivity.setData(Uri.parse("package:" + mActivity.getPackageName()));
        settingActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        settingActivity.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        settingActivity.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        mActivity.startActivityForResult(settingActivity, REQUEST_ID_PERMISSION);
    }

    public interface CallbackRequestPermission {
        void onRequestPermissionSuccess();

        void onRequestPermissionFail();
    }
}
