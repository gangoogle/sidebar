package com.naive.sidebar.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.naive.sidebar.R;

/**
 * Created by zgyi on 2017/1/20.
 */

public class MyTextDialog {

    public Dialog mDialog;
    public Context mContext;
    public TextView mTvLetter;

    public MyTextDialog(Context context) {
        mContext = context;
        mDialog = new Dialog(context,R.style.CustomDialog);
        View view = View.inflate(context, R.layout.view_text_dialog, null);
        mDialog.setContentView(view);
        mTvLetter = (TextView) view.findViewById(R.id.tv_text);
        mDialog.setCancelable(false);
    }

    public void show(String text) {
        if (mDialog != null&& !TextUtils.isEmpty(text)) {
            mTvLetter.setText(text);
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
