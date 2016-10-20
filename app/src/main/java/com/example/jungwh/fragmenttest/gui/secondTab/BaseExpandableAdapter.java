package com.example.jungwh.fragmenttest.gui.secondTab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.ListRetrieveDetailData;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by jungwh on 2016-10-19.
 */

public class BaseExpandableAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private LinkedHashMap<String, ArrayList<ListRetrieveDetailData>> _listRetrieveDetailDatasMap;

    /*public BaseExpandableAdapter(Context context, ArrayList<ListRetrieveDetailData> listRetrieveDetailDatas){
        _context = context;
        _listRetrieveDetailDatas = listRetrieveDetailDatas;
    }*/
    public BaseExpandableAdapter(Context context, LinkedHashMap<String, ArrayList<ListRetrieveDetailData>> listRetrieveDetailDatasMap){
        _context = context;
        _listRetrieveDetailDatasMap = listRetrieveDetailDatasMap;
    }

    @Override
    public Object getGroup(int groupPosition){
        //return _listRetrieveDetailDatasMap.get(groupPosition);
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
        //final ListRetrieveDetailData listRetrieveDetailData = (ListRetrieveDetailData) getGroup(groupPosition);
        final String inputDate = (String) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        // 홀수 행일 경우 어두운 색, 짝수 행일 경우 밝은 색
        //convertView.setBackgroundResource(groupPosition % 2 == 1 ? R.drawable.bg_lv_row_colored : R.drawable.bg_lv_row_white);

        TextView tv_group = (TextView) convertView.findViewById(R.id.tv_group);
        tv_group.setText(inputDate);

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
            convertView = layoutInflater.inflate(R.layout.list_row, null);
        }

        /*if (groupPosition % 2 == 1)
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.lv_alternating_row_dark));
        else
            convertView.setBackgroundColor(parent.getResources().getColor(R.color.lv_alternating_row_light));*/

        TextView tv_child = (TextView) convertView.findViewById(R.id.tv_child);
        tv_child.setText(listRetrieveDetailDatas.get(childPosition).getInputMemo());

        /*TextView tvInternationalCode = (TextView) convertView.findViewById(R.id.row_order_detail_tv_international_code);
        tvInternationalCode.setText(orderDetails.getInternationalCode());

        TextView tvColor = (TextView) convertView.findViewById(R.id.row_order_detail_tv_color);
        tvColor.setText(orderDetails.getColor());

        TextView tvDiscount = (TextView) convertView.findViewById(R.id.row_order_detail_tv_discount);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formattedDiscount = decimalFormat.format(orderDetails.getPercentDiscount());
        tvDiscount.setText(formattedDiscount + "%");

        TextView tvMargin = (TextView) convertView.findViewById(R.id.row_order_detail_tv_margin);
        String formattedMargin = decimalFormat.format(orderDetails.getPercentMargin());
        tvMargin.setText(formattedMargin + "%");

        TextView tvUnitSellingPrice = (TextView) convertView.findViewById(R.id.row_order_detail_tv_unit_selling_price);
        decimalFormat = new DecimalFormat("#,##0");
        String formattedUnitSellingPrice = decimalFormat.format(orderDetails.getUnitSellingPrice());
        tvUnitSellingPrice.setText(formattedUnitSellingPrice);*/

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
