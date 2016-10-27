package com.example.jungwh.fragmenttest.gui.secondTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.ListRetrieveDetailData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by jungwh on 2016-10-19.
 */

public class BaseExpandableAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private LinkedHashMap<String, ArrayList<ListRetrieveDetailData>> _listRetrieveDetailDatasMap;

    public BaseExpandableAdapter(Context context, LinkedHashMap<String, ArrayList<ListRetrieveDetailData>> listRetrieveDetailDatasMap){
        _context = context;
        _listRetrieveDetailDatasMap = listRetrieveDetailDatasMap;
    }

    @Override
    public Object getGroup(int groupPosition){
        return _listRetrieveDetailDatasMap.keySet().toArray()[groupPosition];
    }

    @Override
    public int getGroupCount(){
        return _listRetrieveDetailDatasMap.size();
    }

    @Override
    public long getGroupId(int groupPosition){
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
        final String inputDate = (String) getGroup(groupPosition);

        final ArrayList<ListRetrieveDetailData> listRetrieveDetailDatas = (ArrayList<ListRetrieveDetailData>) getChild(groupPosition, 0);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        // 홀수 행일 경우 어두운 색, 짝수 행일 경우 밝은 색
        convertView.setBackgroundResource(groupPosition % 2 == 1 ? R.drawable.bg_lv_row_colored : R.drawable.bg_lv_row_white);

        // 날짜
        TextView tvInputDate = (TextView) convertView.findViewById(R.id.groupInputDateValue);
        tvInputDate.setText(inputDate);

        // 총 가격
        int sumInputPrice = 0;
        for (ListRetrieveDetailData childData : listRetrieveDetailDatas) {
            sumInputPrice += childData.getInputPrice();
        }
        TextView tvTotalInputPrice = (TextView) convertView.findViewById(R.id.groupTotalInputPriceValue);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedTotalInputPrice = decimalFormat.format(sumInputPrice);
        tvTotalInputPrice.setText(String.valueOf(formattedTotalInputPrice));

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition){
        return _listRetrieveDetailDatasMap.values().toArray()[groupPosition];
    }

    @Override
    public int getChildrenCount(int groupPosition){
        ArrayList<ListRetrieveDetailData> listRetrieveDetailDatas = (ArrayList<ListRetrieveDetailData>) _listRetrieveDetailDatasMap.values().toArray()[groupPosition];
        return listRetrieveDetailDatas.size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
        final ArrayList<ListRetrieveDetailData> listRetrieveDetailDatas = (ArrayList<ListRetrieveDetailData>) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_detail_row, null);
        }

        if (groupPosition % 2 == 1)
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.lv_alternating_row_dark));
        else
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.lv_alternating_row_light));

        // 분류
        TextView tvIemValue = (TextView) convertView.findViewById(R.id.childIemValue);
        tvIemValue.setText(listRetrieveDetailDatas.get(childPosition).getInputIem());

        // 항목
        TextView tvCategoryValue = (TextView) convertView.findViewById(R.id.childCategoryValue);
        tvCategoryValue.setText(listRetrieveDetailDatas.get(childPosition).getInputCategory());

        TextView tvPriceValue = (TextView) convertView.findViewById(R.id.childPriceValue);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedPrice = decimalFormat.format(listRetrieveDetailDatas.get(childPosition).getInputPrice());
        tvPriceValue.setText(String.valueOf(formattedPrice));

        TextView tvMemoValue = (TextView) convertView.findViewById(R.id.childMemoValue);
        tvMemoValue.setText(listRetrieveDetailDatas.get(childPosition).getInputMemo());

        return convertView;
    }

    @Override
    public boolean hasStableIds(){return true;}

    @Override
    public boolean isChildSelectable(int groupPostion, int childPosition){return true;}



    class ViewHolder{
        //public ImageView iv_image;
        public TextView tv_groupName;
        public TextView tv_childName;
    }
}
