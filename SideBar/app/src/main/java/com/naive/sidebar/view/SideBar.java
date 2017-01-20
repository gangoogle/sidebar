package com.naive.sidebar.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.naive.sidebar.R;
import com.naive.sidebar.UpdateListView;

import java.util.LinkedList;

/**
 * Created by zgyi on 2017/1/19.
 */

public class SideBar extends View {

    private final String[] mSideTextList = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private Paint mTextPaint;
    private int selectIndex = -1;
    private int buttomPadding = 10;
    private Context mContext;
    private MyTextDialog dialog;

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();

    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initData() {
        mTextPaint = new Paint();
        mTextPaint.setTextSize(50);
        mTextPaint.setAntiAlias(true);
        setBackgroundColor(Color.TRANSPARENT);
        dialog = new MyTextDialog(mContext);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取文字高度
        int textHeight = (getHeight() - buttomPadding) / mSideTextList.length;
        for (int i = 0; i < mSideTextList.length; i++) {
            Log.d("yzg", "onDraw_SelectIndex:" + selectIndex);
            if (i == selectIndex) {
                mTextPaint.setColor(Color.parseColor("#EA2000"));
            } else {
                mTextPaint.setColor(Color.parseColor("#2B2B2B"));
            }
            canvas.drawText(mSideTextList[i], (getWidth() - mTextPaint.measureText(mSideTextList[i])) / 2, textHeight
                    * (i + 1), mTextPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int textHeight = (getHeight() - buttomPadding) / mSideTextList.length;
        Log.d("yzg", "X:" + (int) event.getX() + "-Y:" + (int) event.getY() + "textHeight:" + textHeight);
        int touchY = (int) event.getY();
        int index = touchY / textHeight;
        if (touchY % textHeight == 0) {
            index = index - 1;
        }
        Log.d("yzg", "/height" + index);
        String letter = "";
        if (index >= 0 && index < mSideTextList.length) {
            letter = mSideTextList[index];
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundColor(Color.parseColor("#2d7e82"));
                dialog.show(letter);
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
                dialog.dismiss();
                break;
            default:
                dialog.show(letter);

        }
        if (selectIndex != index && index < mSideTextList.length && index >= 0) {
            selectIndex = index;
            if (updateListView != null) {
                updateListView.selectIndex(mSideTextList[selectIndex], selectIndex);
            }

        }
        invalidate();
        return true;
    }

    public void setLetterIndex(String letter) {
        if (!letter.matches("[#A-Z]")) {
            return;
        }
        for (int i = 0; i < mSideTextList.length; i++) {
            if (letter.equals(mSideTextList[i])) {
                if (selectIndex != i) {
                    selectIndex = i;
                    invalidate();
                    break;
                }
            }
        }
    }

    UpdateListView updateListView;

    public void setUpdateListViewInterface(UpdateListView aUpdateListView) {
        updateListView = aUpdateListView;
    }
}
