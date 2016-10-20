package com.example.jungwh.fragmenttest.gui.secondTab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.ListRetrieveData;
import com.example.jungwh.fragmenttest.business.data.ListRetrieveDetailData;
import com.example.jungwh.fragmenttest.business.logic.ListRetrieveService;
import com.example.jungwh.fragmenttest.util.AlertDialogWrapper;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.example.jungwh.fragmenttest.util.Tuple;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by jungwh on 2016-09-26.
 */

@SuppressLint("ValidFragment")
public class SecondTabActivity extends Fragment {
    private ListRetrieveTask authTask = null;
    private View progressView, formView;
    //private ListRetrieveData listRetrieveData;
    private Tuple<ListRetrieveData, LinkedHashMap<String, ArrayList<ListRetrieveDetailData>>> tupleResult;
    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> mChildListContent = null;
    private ExpandableListView mListView;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View secondTabView = layoutInflater.inflate(R.layout.fragment_second_tab, viewGroup, false);

        formView = secondTabView.findViewById(R.id.activity_list_ll_retrieve_form);
        progressView = secondTabView.findViewById(R.id.activity_list_rl_retrieve_layout);

        mListView = (ExpandableListView) secondTabView.findViewById(R.id.second_expandable_list_view);
        asyncTaskStart();
        return secondTabView;
    }

    private void asyncTaskStart() {
        if (authTask != null) {
            return;
        }

        //authTask = new ListRetrieveTask(getApplicationContext(), inputDateFrom, inputDateTo, userId);
        authTask = new ListRetrieveTask(getActivity(), "20161001", "20161031", "test");
        authTask.execute((Void) null);
    }

    private void retrieve(LinkedHashMap<String, ArrayList<ListRetrieveDetailData>> mapResult){

        if (mapResult == null) return;

        ExpandableListAdapter expandableListAdapter = new BaseExpandableAdapter(getActivity(),
                mapResult);
        mListView.setAdapter(expandableListAdapter);

        //DecimalFormat decimalFormat = new DecimalFormat("#,###");
        //tvQtyGrandTotal.setText(decimalFormat.format(orderList.getQtyGrandTotal()));
        //tvPriceGrandTotal.setText(decimalFormat.format(orderList.getTaxedSupplyPriceGrandTotal()));

        /*mGroupList = new ArrayList<String>();
        mChildList = new ArrayList<ArrayList<String>>();
        mChildListContent = new ArrayList<String>();

        mGroupList.add("가위");
        mGroupList.add("바위");
        mGroupList.add("보");

        mChildListContent.add("1");
        mChildListContent.add("2");
        mChildListContent.add("3");

        mChildList.add(mChildListContent);
        mChildList.add(mChildListContent);
        mChildList.add(mChildListContent);

        mListView.setAdapter(new BaseExpandableAdapter(getActivity(), mGroupList, listRetrieveData.getChildList()));*/

        /*mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(getActivity(), "g click = " + groupPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(), "c click = " + childPosition,
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity(), "g Collapse = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity(), "g Expand = " + groupPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            formView.setVisibility(show ? View.GONE : View.VISIBLE);
            formView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    formView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            formView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class ListRetrieveTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String inputDateFrom;
        private final String inputDateTo;
        private final String userId;
        private ListRetrieveService listRetrieveService = new ListRetrieveService();
        private String listRetrieveErrMsg;

        ListRetrieveTask(Context context, String inputDateFrom, String inputDateTo, String userId) {
            this.context = context;
            this.inputDateFrom = inputDateFrom;
            this.inputDateTo = inputDateTo;
            this.userId = userId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                tupleResult = listRetrieveService.listRetrieve(inputDateFrom, inputDateTo, userId);
                return tupleResult.getX().isThereData();
            } catch (JSONException | IOException e) {
                listRetrieveErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            showProgress(false);

            if (success) {
                retrieve(tupleResult.getY());
            } else {
                listRetrieveErrMsg = tupleResult.getX().getErrMsg();
                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(getActivity(), getString(R.string.help), listRetrieveErrMsg, AlertDialogWrapper.DialogButton.OK);
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            showProgress(false);
        }
    }
}
