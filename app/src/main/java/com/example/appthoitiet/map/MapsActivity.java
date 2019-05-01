package com.example.appthoitiet.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appthoitiet.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.places.ui.PlacePicker;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Button btnOk;
    private TextView txtDiaDiem;
    private static final String TAG = "MapsActivity";
    private boolean mLocationPermissionGrant = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int DEFAULT_ZOOM = 10;
    public static final int REQUEST_CODE_ACTIVITY = 1234;
    Marker marker;
    private GoogleApiClient mClient;
    private static final int PLACE_PICKER_REQUEST = 1;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getLocationPermission();
        btnOk = findViewById(R.id.buttonOK);
        txtDiaDiem = findViewById(R.id.TextViewPlace);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //khoi tao gg api client
        mClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();
        txtDiaDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapsActivity.this, "Bạn cần bật vị trí lên", Toast.LENGTH_LONG).show();
                    return;
                }
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent i = builder.build(MapsActivity.this);
                    startActivityForResult(i, PLACE_PICKER_REQUEST);
                }catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
                } catch (Exception e) {
                    Log.e(TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
                }
            }
        });



    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();
        if (mLocationPermissionGrant) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                geoLocateLatLng(latLng);
            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                getDeviceLocation();
                return false;
            }
        });

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    // Cấp quyền truy cập vào vị trí thiết bị
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: get quyền");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGrant = true;
            }else{
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    //lấy vị trí hiện tại của thiết bị
    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: lấy vị trí thiết bị");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if(mLocationPermissionGrant){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "getDeviceLocation: Tìm thấy vị trí thiết bị" );
                            Location currentLocation = (Location) task.getResult();
                            if(currentLocation != null) {
                                LatLng lmn = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                if(marker != null){
                                    marker.remove();
                                }
//                                marker = mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Vị trí của tôi"));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lmn, DEFAULT_ZOOM));

                                geoLocateLatLng(lmn);





//                            moveCamera(lmn, DEFAULT_ZOOM, "Vị trí của tôi");
//                                txtDiaDiem.setText("Vị trí của tôi");
                            }else{
                                Log.d(TAG, "getDeviceLocation: Vị trí null");
                                Toast.makeText(MapsActivity.this, "Null Bạn chưa bật vị trí trên thiết bị của bạn, Không thể lấy vị trí", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Log.d(TAG, "getDeviceLocation: Vị trí null");
                            Toast.makeText(MapsActivity.this, "Bạn chưa bật vị trí trên thiết bị của bạn, Không thể lấy vị trí", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
                Toast.makeText(MapsActivity.this, "Bạn chưa bật vị trí", Toast.LENGTH_LONG).show();
            }
        }catch (SecurityException e){
            Log.d(TAG, "getDeviceLocation: " + e.getMessage());

        }

    }
    // Nhận kết quả tìm kiếm vị trí
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == REQUEST_CODE_ACTIVITY  && resultCode == RESULT_OK && data != null){
//            double latitude = Double.parseDouble(data.getStringExtra("lat"));
//            double longitude = Double.parseDouble(data.getStringExtra("lng"));
//            String address = data.getStringExtra("address");
//            LatLng latFind = new LatLng(latitude, longitude);
//        }
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                geoLocateLatLng(place.getLatLng());
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
//                if (marker != null){
//                    marker.remove();
//                }
//                moveCamera(place.getLatLng(), DEFAULT_ZOOM, toastMsg);

//                geoLocateLatLng(place.getLatLng());
            }
        }
    }
    //xử lý kết quả cấp quyền truy cập vị trí thiết bị
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: đã gọi");
        mLocationPermissionGrant = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 ){
                    for(int i = 0 ; i<grantResults.length ; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                            mLocationPermissionGrant = false;
                        Log.d(TAG, "onRequestPermissionsResult: lỗi cấp quyền");
                        return;
                    }
                }
                Log.d(TAG, "onRequestPermissionsResult: Được cấp quyền");
                mLocationPermissionGrant = true;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    private void moveCamera(LatLng latLng, int zoom, String title) {
//        Log.d(TAG, "moveCamera: đang di chuyển camera đến " + latLng.latitude + ", " + latLng.longitude);
//        Marker TP = mMap.addMarker(new MarkerOptions().position(latLng).title(title));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "API Client Connection Failed!");
    }

    private void geoLocateLatLng(final LatLng lat){
        Log.d(TAG, "geoLocate: đang tim dia diem theo toa do lat");
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocation(lat.latitude, lat.longitude,1);
        }catch (IOException e){
            Log.d(TAG, "geoLocate: IOException " + e.getMessage());
        }
        if (list.size() > 0){
            //tim ten dia diem theo vi tri toa do
            Address address = list.get(0);
            String chuoi[] = address.getAddressLine(0).split(",");
            String placeText = "";
            for(int len = 1; len < chuoi.length - 1 ; len++){
                placeText += chuoi[len] + ", ";
            }
            placeText += chuoi[chuoi.length-1];
            Log.d(TAG, "geoLocateLatLng: " + address.toString() );
            Log.d(TAG, "geoLocateLatLng: " + address.getLocality());
            Log.d(TAG, "adressline: " + address.getAddressLine(0));
            if (marker != null){
                marker.remove();
            }
            marker = mMap.addMarker(new MarkerOptions().position(lat).title(placeText));

        }
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat.latitude + "&lon=" + lat.longitude+"&apikey=f8f31d0777ea04c4be8cd4c8a7ca25b5&units=metric";
//        String url = "http://api.openweathermap.org/data/2.5/weather?q=hanoi&apikey=ee451b250872fe4ae214f6747ec3bc5e&units=metric";
        RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
//        http://api.openweathermap.org/data/2.5/weather?lat=19.882950052408006&lon=105.50286199897526&apikey=f8f31d0777ea04c4be8cd4c8a7ca25b5&units=metric
//        String url = "http://http://api.openweathermap.org/data/2.5/weather?lat="+lat.latitude+"&lon="+lat.longitude+"&apikey=f8f31d0777ea04c4be8cd4c8a7ca25b5&units=metric";
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //lấy thời gian
                    String day = jsonObject.getString("dt");
                    String time = FormatTime(day);
                    //lấy trạng thái
                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject statusWeather = jsonArrayWeather.getJSONObject(0);
//                    JSONObject statusWeather1 = jsonObject.getJSONObject("weather");
                    String status = statusWeather.getString("main");
                    //lấy nhiệt độ(C)
                    JSONObject temWeather = jsonObject.getJSONObject("main");
                    String tem = temWeather.getString("temp");
                    //lấy độ ẩm (%)
                    String humidity = temWeather.getString("humidity");
                    //lấy sức gió (m/s)
                    JSONObject windWeather = jsonObject.getJSONObject("wind");
                    String wind = windWeather.getString("speed");
                    //lấy trạng thái mây (%)
                    JSONObject cloudWeather = jsonObject.getJSONObject("clouds");
                    String cloud = cloudWeather.getString("all");
                    //lấy thời gian mat troi moc, lan
                    JSONObject sysWeather = jsonObject.getJSONObject("sys");
                    String sunrise = FormatHour(sysWeather.getString("sunrise"));
                    String sunset = FormatHour(sysWeather.getString("sunset"));
                    //lay tam nhin (m)
//                    String visibility = jsonObject.getString("visibility");
                    String tamnhin = jsonObject.getString("visibility");
                    System.out.println(tamnhin);
                    String visibility  = 1000 +"";

//                    if(marker!=null){
//                        marker.remove();
//                    }
                    //set adapter
                    MyInfoWindowAdapter adapter = new MyInfoWindowAdapter(MapsActivity.this);
                    mMap.setInfoWindowAdapter(adapter);
                    //dd
                    ThoiTiet thoiTiet = new ThoiTiet(marker.getTitle(), time, status, tem, humidity, wind, cloud, sunrise, sunset, visibility );
                    marker.setTag(thoiTiet);
                    //show infowindow
                    marker.showInfoWindow();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat,DEFAULT_ZOOM));
//                    mMap.animateCamera(CameraUpdateFactory.newLatLng(lat));
                } catch (JSONException e) {
                    System.out.println("Lỗi đọc json");
                    Log.d("Loi Json: ", "Lỗi json");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Loi Json: ", error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public String FormatTime(String time){
        long l = Long.valueOf(time); //43200;
        Date date = new Date(l*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
        String Day = simpleDateFormat.format(date);
        return Day;
    }
    public String FormatHour(String time){
        long l = Long.valueOf(time)+43200;
        Date date = new Date(l*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String Day = simpleDateFormat.format(date);
        return Day;
    }

}
