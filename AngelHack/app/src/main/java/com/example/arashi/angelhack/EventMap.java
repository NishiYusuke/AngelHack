package com.example.arashi.angelhack;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.arashi.angelhack.R.id.map;

public class EventMap extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleApiClient.ConnectionCallbacks {

    private final int REQUEST_PERMISSION = 10;

    //LocationManagerの変わり、これがなくてもGoogleApiClientとsetMyLocationEnabled(true)を使えば現在位置の表示はできるが情報の取得はできないし、精度がわるい
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;

    //現在地を取得する設定、何秒間隔で取得するか、setfastestintervalはいアプリを起動したとき
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)
            .setFastestInterval(16)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    GoogleMap mMap;
    private GoogleApiClient mLocationClient = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_map);

        setTitle("");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.toolbar_title_sub)).setText("Joinas");

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        //現在地表示のための設定、アダプター的な
        mLocationClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mLocationClient.connect();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
                startActivity(intent);
            }
        });

        ImageButton settingsButton = (ImageButton) findViewById(R.id.button_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    LatLng[] eventLatLngl = {new LatLng(34.70705, 135.485783),new LatLng(34.702484, 135.493174), new LatLng(34.702564, 135.495757), new LatLng(34.70557, 135.500782)};
    String[] eventLocationNamel = {"大阪府大阪市北区大淀北１丁目６−５","大阪府大阪市北区大深町２−２５","大阪府大阪市北区梅田３丁目１−１ ","大阪府大阪市北区茶屋町１−1−45"};
    String[] eventTitlel = {"路上ライブやるよ！","路上ライブやるよ！","路上ライブやるよ！","路上ライブやるよ！"};
    String[] eventSnipetl = {"15:00からグランフロントの下でライブやります。\n よかったら見に来てください。 ","15:00からグランフロントの下でライブやります。\n よかったら見に来てください。 "
            ,"15:00からグランフロントの下でライブやります。\n よかったら見に来てください。 ","15:00からグランフロントの下でライブやります。\n よかったら見に来てください。 "};
    String[] eventOwnerl = {"おおひら","さこ","おおひら","さこ"};
    String[] eventStartTimel = {"15:00","15:00","15:30","17:00"};
    String[] eventStartEndl = {"16:00","17:00","20:30","21:00"};
    String[] eventStatusl = {"nolook","nolook","liked","mine"};



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // InfoWindowAdapter設定
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            // この部分はマーカーがクリックされたときに初めて呼ばれる！
            @Override
            public View getInfoContents(Marker marker) {
                // TODO Auto-generated method stub
                View view = getLayoutInflater().inflate(R.layout.info_window, null);
                // タイトル設定
                TextView title = (TextView)view.findViewById(R.id.info_title);
                title.setText(marker.getTitle());
                // 時間
                TextView nitizi = (TextView)view.findViewById(R.id.info_nitizi);
                nitizi.setText("時間：15:00〜17:00");
                // 内容
                TextView snippet = (TextView)view.findViewById(R.id.info_snippet);
                snippet.setText(marker.getSnippet());
                return view;
            }
            @Override
            public View getInfoWindow(Marker marker) {
                // TODO Auto-generated method stub
                return null;
            }
        });

        //マーカーをクリックした時googlemapsのボタンが表示されないようにする。
        UiSettings us = mMap.getUiSettings();
        us.setMapToolbarEnabled(false);

        for (int i=0; i<eventTitle.length;i++){
            LatLng eve = eventLatLng[i];
            if(eventStatus[i].equals("nolook")){
                mMap.addMarker(new MarkerOptions().position(eve).title(eventTitle[i]).snippet(eventSnipet[i]));
            }else if(eventStatus[i].equals("liked")){
                mMap.addMarker(new MarkerOptions().position(eve).title(eventTitle[i]).snippet(eventSnipet[i]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }else if(eventStatus[i].equals("mine")){
                mMap.addMarker(new MarkerOptions().position(eve).title(eventTitle[i]).snippet(eventSnipet[i]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.705912, 135.494472), 15));


        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);

        //現在地の表示
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    private boolean firstlocationf = true;
    @Override
    public void onLocationChanged(Location location){
    //最初だけ現在地のところをズームする
        if(firstlocationf == true){
            LatLng kobe = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kobe, 10));
            firstlocationf = false;
        }
    }

    //infowindowがクリックされたときのイベント
    @Override
    public void onInfoWindowClick(Marker marker) {
        if(eventStatus[Integer.parseInt(marker.getId().substring(1))].equals("mine")){
            Intent intent = new Intent(getApplicationContext(), MyEventPage.class);
            startActivity(intent);
        }else if(eventStatus[Integer.parseInt(marker.getId().substring(1))].equals("liked")){
            Intent intent = new Intent(getApplicationContext(), LikedEvent.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), ViewOtherEvent.class);
            startActivity(intent);
        }

        Toast.makeText(getApplicationContext(), marker.getId().substring(1), Toast.LENGTH_LONG).show();

    }

    // 位置情報許可の確認
    public void checkPermission() {
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
        }
        // 拒否していた場合
        else{
            requestLocationPermission();
        }
    }

    // 許可を求める
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(EventMap.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
        } else {
            Toast toast = Toast.makeText(this, "許可されないとアプリが実行できません", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // 結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //再リロードして再描写する必要がある
                finish();
                startActivity(getIntent());
            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this, "これ以上なにもできません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
