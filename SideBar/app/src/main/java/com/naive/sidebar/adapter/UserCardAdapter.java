package com.naive.sidebar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naive.sidebar.R;
import com.naive.sidebar.model.User;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zgyi on 2017/1/19.
 */

public class UserCardAdapter extends BaseAdapter {

    private List<User> mUsers;
    private Context mContext;
    private HashMap<String, Integer> mHeadLetterMap;

    public UserCardAdapter(Context aContext, List<User> aUsers, HashMap<String, Integer> aHeadLetterMap) {
        mUsers = aUsers;
        mContext = aContext;
        mHeadLetterMap = aHeadLetterMap;
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler viewHodler = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_business_card, null);
            viewHodler = new ViewHodler(convertView);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }
        User user = mUsers.get(position);
        if (user.getIsHead()) {
            viewHodler.tvHeader.setVisibility(View.VISIBLE);
            viewHodler.tvHeader.setText(user.getFirstLetter());
        } else {
            viewHodler.tvHeader.setVisibility(View.GONE);
        }
        viewHodler.ivHeadPhoto.setImageResource(user.getHeadPhoto());
        viewHodler.tvUserName.setText(user.getName());
        viewHodler.tvTelNumber.setText(user.getTelNumber() + "");
        viewHodler.tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHodler.tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }


    public class ViewHodler {

        TextView tvHeader;
        ImageView ivHeadPhoto;
        TextView tvUserName;
        TextView tvTelNumber;
        TextView tvCall;
        TextView tvSend;

        public ViewHodler(View view) {
            tvHeader = (TextView) view.findViewById(R.id.tv_card_head);
            ivHeadPhoto = (ImageView) view.findViewById(R.id.iv_head_photo);
            tvUserName = (TextView) view.findViewById(R.id.tv_name);
            tvTelNumber = (TextView) view.findViewById(R.id.tv_tel_number);
            tvCall = (TextView) view.findViewById(R.id.tv_call);
            tvSend = (TextView) view.findViewById(R.id.tv_send);
        }
    }


}
