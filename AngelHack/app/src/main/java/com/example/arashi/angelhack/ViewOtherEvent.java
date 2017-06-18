package com.example.arashi.angelhack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class ViewOtherEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_other_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.toolbar_title_sub)).setText("イベント");

        Intent intent = getIntent();
        final int selectedm = Integer.parseInt(intent.getStringExtra(EventMap.EXTRA_SELECTED_MARKER));


        //return button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button likeButton = (Button) findViewById(R.id.likebutton);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventMap.eventStatus.set(selectedm,"liked");
                Intent intent = new Intent(getApplicationContext(),EventMap.class);
                startActivity(intent);
            }
        });

        Button notlikeButton = (Button) findViewById(R.id.notlikebutton);
        notlikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventMap.eventStatus.remove(selectedm);
                EventMap.eventLatLng.remove(selectedm);
                EventMap.eventLocationName.remove(selectedm);
                EventMap.eventStartTime.remove(selectedm);
                EventMap.eventEndTime.remove(selectedm);
                EventMap.eventOwner.remove(selectedm);
                EventMap.eventTitle.remove(selectedm);
                EventMap.eventSnipet.remove(selectedm);

                Intent intent = new Intent(getApplicationContext(),EventMap.class);
                startActivity(intent);
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
