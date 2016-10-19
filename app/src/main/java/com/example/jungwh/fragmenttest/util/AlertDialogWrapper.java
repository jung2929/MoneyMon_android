package com.example.jungwh.fragmenttest.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by jungwh on 2016-10-20.
 */

public class AlertDialogWrapper {
    public enum DialogButton { OK, YesNo }
    private AlertDialogCallbacks callbackListener;

    public void showAlertDialog(Context context, String title, String message, DialogButton alertDialogButtons) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);

        switch (alertDialogButtons) {
            case OK:
                builder.setCancelable(false);
                break;
            case YesNo:
                builder.setCancelable(true);
                builder.setNegativeButton("취소",null);
                break;
        }

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callbackListener != null) {
                    callbackListener.onConfirmClicked();
                }

                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void setOnClickListener(AlertDialogCallbacks callback) {
        callbackListener = callback;
    }

    public interface AlertDialogCallbacks {
        void onConfirmClicked();
    }
}