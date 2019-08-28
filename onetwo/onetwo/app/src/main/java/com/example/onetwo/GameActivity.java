package com.example.onetwo;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.onetwo.AnsAdapter;
import com.example.onetwo.AnsListBean;
import com.example.onetwo.Main;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity {
    //랜덤값
    private int ranNum;
    //카운트
    private int cnt = 0;
    //List보여주는 어댑터
    private AnsAdapter ansAdapter;
    //내가 작성한 값들 저장 객체 빈
    private ArrayList<AnsListBean> ansArrayList = new ArrayList<AnsListBean>();
    public static boolean isCorrect = false;
    public static Context context;


    EditText ansEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        context=this;
        //정수 생성
        ranNum =generateRanNum(context);
        ansEditText = (EditText) findViewById(R.id.ansEditTextId); // 입력값
        ListView ansList = (ListView) findViewById(R.id.ansListId); // 입력한 숫자 리스트
        //파라미터 값으로 넘겨줌
        ansAdapter = new AnsAdapter(this,R.layout.anslist, ansArrayList);
        ansList.setAdapter(ansAdapter);
    }

    private int generateRanNum(Context context) {
        Main m = new Main(context);
        int ranNum = m.genRanNum();        // 네자리 정수 생성
        return ranNum;
    }

    //버튼클릭 이벤트처리
    public void mOnClick(View v) {
        switch(v.getId()){
            case R.id.ansBtnId:
                cnt++;
                ansArrayList = doCompare();
                ansEditText.setText("");        // 기존 입력값 초기화
                ansAdapter.notifyDataSetChanged();

                if(isCorrect) {        // 정답을 맞추면 재시작할건지를 묻는다.
                    alertDialog();
                    isCorrect = !isCorrect;
                }
                break;
        }
    }

    private void alertDialog() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ranNum = generateRanNum(context);        // 재시작
                ansArrayList.clear();
                ansAdapter.notifyDataSetChanged();
            }
        };

        Builder builder = new Builder(this);
        builder.setTitle("정답입니다.(시도회수 : " + cnt + ")\n재시작하시겠습니까?");
        builder.setPositiveButton("Yes", listener);
        builder.setNegativeButton("No", null);
        cnt = 0;
        builder.show();
    }


    private ArrayList<AnsListBean> doCompare() {
        Main m = new Main(getApplicationContext());
        String ansNum = (String) ansEditText.getText().toString();
        int ans = m.isValid(ansNum);    // 입력받은 값이 숫자인지 확인

        if( ans != -1 ) {
            String result = m.process(ans, ranNum);
            System.out.println(result);
            AnsListBean ansListBean = new AnsListBean();
            ansListBean.setAnsNum(ansNum);
            if(!result.contains("4 strike")) {
                ansListBean.setAnsResult(result);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            } else {
                ansListBean.setAnsResult("정답");
                isCorrect = !isCorrect;
            }
            ansArrayList.add(ansListBean);
        }
        return ansArrayList;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}