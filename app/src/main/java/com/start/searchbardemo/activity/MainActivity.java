package com.start.searchbardemo.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;
import com.start.searchbardemo.R;
import com.start.searchbardemo.view.SearchBar;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener{
    SearchBar searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBar = (SearchBar)findViewById(R.id.search_bar_custom);
        searchBar.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        Toast.makeText(this,"StartSearching:"+textView.getText().toString(),Toast.LENGTH_SHORT).show();
        searchBar.startSearching(true);

        doSomething();
        return false;
    }
    public void doSomething() {
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Toast.makeText(getApplicationContext(),"FinishSearching",Toast.LENGTH_SHORT).show();
                searchBar.finishSearching();
            }
        };
        handler.sendEmptyMessageDelayed(0,3000);
    }
}
