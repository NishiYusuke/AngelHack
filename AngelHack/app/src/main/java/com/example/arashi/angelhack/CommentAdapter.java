package com.example.arashi.angelhack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kosa-a on 2017/06/08.
 */

public class CommentAdapter extends BaseAdapter
{

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public static ArrayList<String> nameArray = new ArrayList<String>();

    public static ArrayList<String> commentArray = new ArrayList<String>();

    public static ArrayList<Integer> imageArray = new ArrayList<Integer>();
    private static class ViewHolder {
        public ImageView userimage;
        public TextView username;
        public TextView comment;
    }

    public CommentAdapter(Context context) {
//        nameArray.add("tenmoku");
//        commentArray.add("tenmoku");
//        imageArray.add(R.drawable.tenmoku);
//
//        nameArray.add("tenmoku");
//        commentArray.add("tenmoku");
//        imageArray.add(R.drawable.tenmoku);

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageArray.size();
    }

    @Override
    public Object getItem(int position) {
        return imageArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //ここら辺の詳細は
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_comment, null);
            holder = new ViewHolder();
            holder.userimage = (ImageView)convertView.findViewById(R.id.comment_userimage);
            holder.username = (TextView)convertView.findViewById(R.id.comment_username);
            holder.comment = (TextView)convertView.findViewById(R.id.comment_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.userimage.setImageResource(imageArray.get(position));
        holder.username.setText(nameArray.get(position));
        holder.comment.setText(commentArray.get(position));


        return convertView;
    }

}

