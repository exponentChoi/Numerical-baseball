package com.example.onetwo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.onetwo.R;

import java.util.ArrayList;

//대답어뎁터 기초어뎁터 상속
public class AnsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<AnsListBean> ansArrayList;
    private int layout;

    public AnsAdapter(Context context, int anslist, ArrayList<AnsListBean> ansArrayList) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ansArrayList = ansArrayList;
        this.layout = layout;
    }

    @Override
    public int getCount()
    {
        return ansArrayList.size();
    }

    @Override
    public Object getItem(int postion)
    {
        return ansArrayList.get(postion);
    }

    @Override
    public long getItemId(int postion)
    {
        return postion;
    }

    //인플레이션
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        if(view == null)
        {
            view = inflater.inflate(R.layout.anslist, null);
        }
        //불러들여서 텍스트뷰에 표시
        TextView ansNum = (TextView) view.findViewById(R.id.ansNum);
        TextView ansResult = (TextView) view.findViewById(R.id.ansResult);
        ansNum.setText(ansArrayList.get(position).getAnsNum());
        ansResult.setText(ansArrayList.get(position).getAnsResult());
        return view;
    }
}

