package com.example.jungwh.fragmenttest.gui.main;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by jungwh on 2016-10-30.
 */

public class IncomeCateEditActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener
{
    // 추가될 아이템 내용을 입력받는 EditText
    private EditText mEtInputText;

    // 아이템 추가 버튼
    private Button mBInputToList;

    // 리스트뷰
    private ListView mLvList;

    // 데이터 리스트
    private ArrayList<String> mAlData;

    // 리스트뷰에 사용되는 ArrayAdapter
    private ArrayAdapter<String> mAaString;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_cate_edit);

        //////////////////////////////////////////////////////////////

        // 위젯 레퍼런스 시작
        mEtInputText = (EditText) findViewById(R.id.income_et_text);
        mBInputToList = (Button) findViewById(R.id.income_add_to_list);
        mLvList = (ListView) findViewById(R.id.income_list);

        // 위젯 레퍼런스 끝
        ////////////////////////////////////////////////////////////


        // 아이템 추가 버튼에 클릭리스너를 등록한다.
        mBInputToList.setOnClickListener(this);

        // ArrayList 생성
        mAlData = new ArrayList<String>();

        // ArrayAdapter 생성
        mAaString = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mAlData);

        // 어뎁터를 리스트뷰에 세팅한다.
        mLvList.setAdapter(mAaString);

        // 리스트뷰에 아이템클릭리스너를 등록한다.
        mLvList.setOnItemClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onResume() {
        super.onResume();


        // ArrayList 초기화
        mAlData.clear();


        // ArrayList에 더미 데이터 입력
        defaultData();
    }


    private void defaultData() {
        mAlData.add("아이템 00");
        mAlData.add("아이템 01");
        mAlData.add("아이템 02");
        mAlData.add("아이템 03");
        mAlData.add("아이템 04");
        mAlData.add("아이템 05");
        mAlData.add("아이템 06");
        mAlData.add("아이템 07");
        mAlData.add("아이템 08");
        mAlData.add("아이템 09");
        mAlData.add("아이템 10");
        mAlData.add("아이템 11");
        mAlData.add("아이템 12");
        mAlData.add("아이템 13");
        mAlData.add("아이템 14");
        mAlData.add("아이템 15");
        mAlData.add("아이템 16");
        mAlData.add("아이템 17");
        mAlData.add("아이템 18");
        mAlData.add("아이템 19");
    }


    public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
        // 리스트에서 데이터를 받아온다.
//      String data = (String) parent.getItemAtPosition(position);

        String data = mAlData.get(position);

        // 삭제 다이얼로그에 보여줄 메시지를 만든다.
        String message = "해당 카테고리를 삭제하시겠습니까?<br />";


        DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // 선택된 아이템을 리스트에서 삭제한다.
                mAlData.remove(position);

                // Adapter에 데이터가 바뀐걸 알리고 리스트뷰에 다시 그린다.
                mAaString.notifyDataSetChanged();
            }
        };


        // 삭제를 물어보는 다이얼로그를 생성한다.
        new AlertDialog.Builder(this)

                .setTitle("http://croute.me - 예제")
                .setMessage(Html.fromHtml(message))
                .setPositiveButton("삭제", deleteListener)
                .show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            // 리스트에 추가 버튼이 클릭되었을때의 처리
            case R.id.income_add_to_list :
                if (mEtInputText.getText().length() == 0) {
                    // 데이터를 입력하라는 메시지 토스트를 출력한다.
                    Toast.makeText(this, "데이터를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 입력할 데이터를 받아온다.
                    String data = mEtInputText.getText().toString();

                    // 리스트에 데이터를 입력한다.
                    mAlData.add(data);

                    // Adapter에 데이터가 바뀐걸 알리고 리스트뷰에 다시 그린다.
                    mAaString.notifyDataSetChanged();

                    // 데이터 추가 성공 메시지 토스트를 출력한다.
                    Toast.makeText(this, "데이터가 추가되었습니다.", Toast.LENGTH_SHORT).show();

                    // EditText의 내용을 지운다.
                    mEtInputText.setText("");

                    // 데이터가 추가된 위치(리스트뷰의 마지막)으로 포커스를 이동시킨다.
                    mLvList.setSelection(mAlData.size() - 1);
                }
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Setiings Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
