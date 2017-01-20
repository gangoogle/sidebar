package com.naive.sidebar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.naive.sidebar.adapter.UserCardAdapter;
import com.naive.sidebar.model.User;
import com.naive.sidebar.view.SideBar;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ListView mListView;
    private UserCardAdapter mAdapter;
    private List<User> mUsers;
    private ProgressDialog mPgDialog;
    private static final int SET_ADAPTER = 0;
    private HashMap<String, Integer> mHeadLetterMap;
    private SideBar sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mListView = (ListView) findViewById(R.id.listview);
        sideBar = (SideBar) findViewById(R.id.sidebar);
        mPgDialog = new ProgressDialog(mContext);
        sideBar.setUpdateListViewInterface(new UpdateListView() {
            @Override
            public void selectIndex(String letter, int index) {
                Log.d("yzg", "list_selectIndex" + index + "-letter:" + letter);
                if (mListView != null && mHeadLetterMap != null) {
                    if (mHeadLetterMap.containsKey(letter)) {
                        mListView.setSelection(mHeadLetterMap.get(letter));
                    }
                }
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    int position = mListView.getFirstVisiblePosition();
//
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mUsers != null && mUsers.size() > 0) {
                    Log.d("y", "onSCroll:" + firstVisibleItem + "letter:" + mUsers.get(firstVisibleItem).getFirstLetter());
                    sideBar.setLetterIndex(mUsers.get(firstVisibleItem).getFirstLetter());
                }
            }


        });
        initData();
    }

    private void initData() {
        mPgDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mUsers = new ArrayList<User>();
                String[] userStrList = mContext.getResources().getStringArray(R.array.userName);
                HanyuPinyinOutputFormat pingyinFormat = new HanyuPinyinOutputFormat();
                pingyinFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
                pingyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
                pingyinFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
                for (int i = 0; i < userStrList.length; i++) {
                    User user = new User();
                    user.setName(userStrList[i]);
                    user.setTelNumber(1888188 + i);
                    user.setHeadPhoto(R.mipmap.longzhe);
                    String letter = "";
                    try {
                        if (!TextUtils.isEmpty(userStrList[i])) {
                            String[] pinyingChars = PinyinHelper.toHanyuPinyinStringArray(userStrList[i].charAt(0),
                                    pingyinFormat);
                            if (pinyingChars != null && pinyingChars.length > 0) {
                                letter = pinyingChars[0].substring(0, 1);
                            } else {
                                letter = userStrList[i].substring(0, 1).toUpperCase();
                            }
                        } else {
                            letter = "#";
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                        letter = userStrList[i].substring(0, 1);
                    } catch (NullPointerException exception) {
                        letter = "#";
                    }
                    if (letter.matches("[A-Z]")) {
                        user.setFirstLetter(letter);
                    } else {
                        user.setFirstLetter("#");
                    }
                    mUsers.add(user);
                }
                Collections.sort(mUsers, new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        if (o1.getFirstLetter().equals("#")) {
                            return 1;
                        }
                        if (o2.getFirstLetter().equals("#")) {
                            return -1;
                        }
                        return o1.getFirstLetter().compareTo(o2.getFirstLetter());


                    }
                });
                mHeadLetterMap = new HashMap<String, Integer>();
                for (int j = 0; j < mUsers.size(); j++) {
                    User jUser = mUsers.get(j);
                    if (!mHeadLetterMap.containsKey(jUser.getFirstLetter())) {
                        mHeadLetterMap.put(jUser.getFirstLetter(), j);
                        jUser.setIsHead(true);
                    }
                }
                handler.sendEmptyMessage(SET_ADAPTER);
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SET_ADAPTER:
                    if (mPgDialog.isShowing()) {
                        mPgDialog.dismiss();
                    }
                    setAdapter();
                    break;

            }
        }
    };

    private void setAdapter() {
        mAdapter = new UserCardAdapter(mContext, mUsers, mHeadLetterMap);
        mListView.setAdapter(mAdapter);
    }


}
