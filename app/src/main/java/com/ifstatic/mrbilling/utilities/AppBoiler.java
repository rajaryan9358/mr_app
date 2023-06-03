package com.ifstatic.mrbilling.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.ifstatic.mrbilling.R;

public class AppBoiler {

    public static void setInputLayoutErrorDisable(TextInputLayout layout) {
        layout.setError(null);
        layout.setErrorEnabled(false);
    }

    public static Dialog setProgressDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_progress_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        return dialog;
    }

    public static void showSnackBarForInternet(Context context , View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Snackbar.make(view,"No Internet Found",Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(context.getColor(R.color.red_400))
                    .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                    .show();
        }
        // snackbar.showAction -------> operations like undo
    }
    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    public static void navigateToActivity(Context context, Class nextActivity, Bundle bundle) {
        Intent intent = new Intent(context, nextActivity);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void navigateToActivityWithFinish(Context context, Class nextActivity, Bundle bundle) {
        Intent intent = new Intent(context, nextActivity);
        if (bundle != null)
            intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public static void navigateToActivityForResult(Context context, Class nextActivity, Bundle bundle, ActivityResultLauncher<Intent> mapLocationLauncher) {
        Intent intent = new Intent(context, nextActivity);
        if (bundle != null)
            intent.putExtras(bundle);
        mapLocationLauncher.launch(intent);
    }


}
