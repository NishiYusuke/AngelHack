package com.example.arashi.angelhack;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Address;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.example.arashi.angelhack.R.id.map;

public class CreateEvent extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.toolbar_title_sub)).setText("イベント作成");


        //return button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);



        ImageButton searchButton = (ImageButton) findViewById(R.id.button_searchidokeido);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geocoder = new Geocoder(getApplicationContext());
                EditText name = (EditText)findViewById(R.id.edittext_searchidokeido);
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocationName(name.getText().toString(),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addresses != null) {
                    for (Address loc : addresses) {
                        MarkerOptions opts = new MarkerOptions()
                                .position(new LatLng(loc.getLatitude(), loc.getLongitude()))
                                .title(loc.getAddressLine(0));
                        EventMap.eventLatLng.add(new LatLng(loc.getLatitude(), loc.getLongitude()));
                        EditText basyo = (EditText)findViewById(R.id.edittext_searchidokeido);
                        EventMap.eventLocationName.add(basyo.getText().toString());
                        EditText title = (EditText)findViewById(R.id.eventtitle);
                        EventMap.eventTitle.add(title.getText().toString());
                        EditText naiyou = (EditText)findViewById(R.id.eventnaiyou);
                        EventMap.eventSnipet.add(naiyou.getText().toString());
                        EditText stime = (EditText)findViewById(R.id.starttime);
                        EventMap.eventStartTime.add(stime.getText().toString());
                        EditText etime = (EditText)findViewById(R.id.endtime);
                        EventMap.eventStartTime.add(etime.getText().toString());
                        EventMap.eventStatus.add("mine");
                        EventMap.eventOwner.add("おおひら");
                        mMap.addMarker(opts);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 10));
                    }
                }
            }
        });

        Button okButton = (Button) findViewById(R.id.button_createevent_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EventMap.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng kobe = new LatLng(34.705912, 135.494472);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kobe, 10));
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
