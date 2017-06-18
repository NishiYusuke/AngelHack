package com.example.arashi.angelhack;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CommentPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_page);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ((TextView) findViewById(R.id.toolbar_title_sub)).setText("コメント");

        //return button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final ListView listView = (ListView) findViewById(R.id.listview_comment);

        CommentAdapter.nameArray.clear();
        CommentAdapter.commentArray.clear();
        CommentAdapter.imageArray.clear();

        ImageButton commentSendButton = (ImageButton) findViewById(R.id.button_comment_send);
        commentSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edittext = (EditText) findViewById(R.id.edittext_comment);
                String comment = edittext.getText().toString();
                if (!comment.equals("")) {
                    CommentAdapter.nameArray.add(0, "さこ");
                    CommentAdapter.commentArray.add(0, comment);
                    CommentAdapter.imageArray.add(0, R.drawable.danbo);
                    listView.setAdapter(new CommentAdapter(getApplicationContext()));
                    edittext.setText("");
                }
            }
        });
    }


    //return button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean result = true;

        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
    }
}
