package com.kisubs.tournw;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.AdapterView.OnItemSelectedListener;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private ArrayList<String[]> CSVList;

    private ArrayList<Washroom> washroomList;
    private ArrayList<Parks> parkList;
    private ArrayList<BusStop> busStopList;
    private ArrayList<DrinkingFountain> drinkingFountainList;
    private ArrayList<Bench> benchList;
    private ArrayList<PublicArt> publicArtList;
    private GoogleMap mMap;
    private Spinner spinner;
    private DatabaseReference mDatabase;
    private ProgressBar mProgress;

    private Handler mHandler = new Handler();
    private TextView percentage;



    SupportMapFragment mapFrag;

    ArrayList<Marker> markerList = new ArrayList<>();

    //add markers on map

    public void addMarkers(){
        for (Washroom washroom : washroomList) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(washroom.getLatlng())
                    .title("Washroom")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            marker.setTag(washroom.getName());
            markerList.add(marker);

        }
        for (Bench bench : benchList) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(bench.getLatlng())
                    .title("Bench").icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            marker.setTag(bench.getStructureId());
            markerList.add(marker);
        }
        for (DrinkingFountain drinkingFountain : drinkingFountainList) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(drinkingFountain.getLatlng())
                    .title("Water Fountain").icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            marker.setTag(drinkingFountain.getStructureId());
            markerList.add(marker);
        }
        for (PublicArt publicArt : publicArtList) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(publicArt.getLatlng())
                    .title("Public Art").icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            marker.setTag(publicArt.getId());
            markerList.add(marker);
        }
    }

    //loads csv to arrayLists
    public void initiateData(){
        //initialize data
        washroomList = new ArrayList<>();
        parkList = new ArrayList<>();
        busStopList = new ArrayList<>();
        drinkingFountainList = new ArrayList<>();
        benchList = new ArrayList<>();
        publicArtList = new ArrayList<>();




        CSVList = new ArrayList<String[]>();

        //benches
        InputStream inputStream = getResources().openRawResource(R.raw.benches);
        CSVParser csvParser = new CSVParser(inputStream);
        CSVList = csvParser.read();
        for(String[] stringList: CSVList) {
            LatLng latlng = new LatLng(Double.parseDouble(stringList[7]), Double.parseDouble(stringList[6]));
            Bench bench = new Bench(Integer.parseInt(stringList[4]), stringList[3], latlng);

            benchList.add(bench);
        }
        CSVList.clear();

        //washrooms
        inputStream = getResources().openRawResource(R.raw.washrooms);
        csvParser = new CSVParser(inputStream);
        CSVList = csvParser.read();
        for(String[] stringList: CSVList) {
            LatLng latlng = new LatLng(Double.parseDouble(stringList[8]), Double.parseDouble(stringList[7]));
            Washroom washroom = new Washroom(stringList[0], stringList[2], stringList[4], stringList[6],latlng);

            washroomList.add(washroom);
        }
        CSVList.clear();

        //drinking fountains
        inputStream = getResources().openRawResource(R.raw.drinking_fountains);
        csvParser = new CSVParser(inputStream);
        CSVList = csvParser.read();
        for(String[] stringList: CSVList) {
            LatLng latlng = new LatLng(Double.parseDouble(stringList[25]), Double.parseDouble(stringList[24]));
            DrinkingFountain drinkingFountain = new DrinkingFountain(Integer.parseInt(stringList[1]), Integer.parseInt(stringList[23]), stringList[10], stringList[11],latlng);

            drinkingFountainList.add(drinkingFountain);
        }
        CSVList.clear();

        //public arts
        inputStream = getResources().openRawResource(R.raw.public_arts);
        csvParser = new CSVParser(inputStream);
        CSVList = csvParser.read();
        for(String[] stringList: CSVList) {
            LatLng latlng = new LatLng(Double.parseDouble(stringList[19]), Double.parseDouble(stringList[18]));
            PublicArt publicArt = new PublicArt(stringList[3], stringList[8], stringList[9], Integer.parseInt(stringList[10]),latlng);

            publicArtList.add(publicArt);
        }
        CSVList.clear();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener (new OnItemSelectedListener()  {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                String item = parent.getItemAtPosition(pos).toString();


                if(item.equals("Washrooms")){
                    System.out.println(item);
                    for(Marker marker : markerList){
                        if(marker.getTitle().equals("Washroom")){
                            marker.setVisible(true);
                        }else{
                            marker.setVisible(false);
                        }

                    }
                }else if(item.equals("Benches")){
                    System.out.println(item);

                    for(Marker marker : markerList){
                        if(marker.getTitle().equals("Bench")){
                            marker.setVisible(true);
                        }else{
                            marker.setVisible(false);
                        }
                    }

                }else if(item.equals("Public Arts")){
                    System.out.println(item);

                    for(Marker marker : markerList){
                        if(marker.getTitle().equals("Public Art")){
                            marker.setVisible(true);
                        }else{
                            marker.setVisible(false);
                        }
                    }

                }else if(item.equals("Water Fountains")){
                    System.out.println(item);

                    for(Marker marker : markerList){
                        if(marker.getTitle().equals("Water Fountain")){
                            marker.setVisible(true);
                        }else{
                            marker.setVisible(false);
                        }
                    }

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }

        });


        initiateData();
        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        for(Washroom washroom : washroomList){
//            mDatabase.child("items").child("Washroom").child(washroom.getName()).setValue(washroom);
//            mDatabase.child("items").child("Washroom").child(washroom.getName()).setValue(washroom);
//        }
//        for(Bench bench : benchList){
//            mDatabase.child("items").child("Bench").child(Integer.toString(bench.getStructureId())).setValue(bench);
//            mDatabase.child("items").child("Bench").child(Integer.toString(bench.getStructureId())).setValue(bench);
//        }
//        for(PublicArt publicArt : publicArtList){
//            mDatabase.child("items").child("Public Art").child(Integer.toString(publicArt.getId())).setValue(publicArt);
//            mDatabase.child("items").child("Public Art").child(Integer.toString(publicArt.getId())).setValue(publicArt);
//
//        }
//        for(DrinkingFountain drinkingFountain : drinkingFountainList){
//            mDatabase.child("items").child("Drinking Fountain").child(Integer.toString(drinkingFountain.getStructureId())).setValue(drinkingFountain);
//            mDatabase.child("items").child("Drinking Fountain").child(Integer.toString(drinkingFountain.getStructureId())).setValue(drinkingFountain);
//        }
//        for(Washroom washroom : washroomList){
//            mDatabase.child("items").child("Washroom").child(washroom.getName()).child("Likes").setValue(0);
//            mDatabase.child("items").child("Washroom").child(washroom.getName()).child("Dislikes").setValue(0);
//        }
//        for(Bench bench : benchList){
//            mDatabase.child("items").child("Bench").child(Integer.toString(bench.getStructureId())).child("Likes").setValue(0);
//            mDatabase.child("items").child("Bench").child(Integer.toString(bench.getStructureId())).child("Dislikes").setValue(0);
//        }
//        for(PublicArt publicArt : publicArtList){
//            mDatabase.child("items").child("Public Art").child(Integer.toString(publicArt.getId())).child("Likes").setValue(0);
//            mDatabase.child("items").child("Public Art").child(Integer.toString(publicArt.getId())).child("Dislikes").setValue(0);
//
//        }
//        for(DrinkingFountain drinkingFountain : drinkingFountainList){
//            mDatabase.child("items").child("Drinking Fountain").child(Integer.toString(drinkingFountain.getStructureId())).child("Likes").setValue(0);
//            mDatabase.child("items").child("Drinking Fountain").child(Integer.toString(drinkingFountain.getStructureId())).child("Dislikes").setValue(0);
//        }


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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            //Not in api-23, no need to prompt
            mMap.setMyLocationEnabled(true);
        }
        //add all markers
        addMarkers();
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(final Marker marker) {

                final String title = marker.getTitle();
                final String key = marker.getTag().toString();

                DatabaseReference myRef = mDatabase.getRef().child("items").child(title).child(key);

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Object value = (long) dataSnapshot.child("Likes").getValue();
                        long i = (long) value;
                        int l = (int)i ;
                        //int likes = l;
                        value = (long) dataSnapshot.child("Dislikes").getValue();
                        i = (long) value;
                        int dl = (int)i;
                        //int dislikes = dl;
                        int total = l + dl;
                        int p = (int) Math.ceil(((float) l / total) * 100);
                        System.out.println(l + " " + dl + " " + total);
                        percentage = (TextView)findViewById(R.id.percentage);
                        percentage.setText(Integer.toString(p)+"%↑");
                        percentage.setVisibility(View.VISIBLE);

                    }
                    @Override
                    public void onCancelled(DatabaseError firebaseError) {
                        System.out.println("error");
                    }
                });

                Button likeButton = (Button) findViewById(R.id.likeButton);
                likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String title = marker.getTitle();
                        final String key = marker.getTag().toString();
                        // Retrieve new posts as they are added to Firebase
                        //mDatabase.child("items").child(title).child(key).child("Likes").setValue(l);
                        DatabaseReference myRef = mDatabase.getRef().child("items").child(title).child(key).child("Likes");

                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Object value = (long) dataSnapshot.getValue();
                                long i = (long) value;

                                int l = (int)i + 1;
                                mDatabase.child("items").child(title).child(key).child("Likes").setValue(l);

                                System.out.println(value);
                                System.out.println(key);

                            }
                            @Override
                            public void onCancelled(DatabaseError firebaseError) {
                                System.out.println("error");
                            }
                        });
                        //

                    }
                });

                Button dislikeButton = (Button) findViewById(R.id.dislikeButton);
                dislikeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String title = marker.getTitle();
                        final String key = marker.getTag().toString();
                        // Retrieve new posts as they are added to Firebase
                        //mDatabase.child("items").child(title).child(key).child("Likes").setValue(l);
                        DatabaseReference myRef = mDatabase.getRef().child("items").child(title).child(key).child("Dislikes");

                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Object value = (long) dataSnapshot.getValue();
                                long i = (long) value;


                                int l = (int)i + 1;


                            mDatabase.child("items").child(title).child(key).child("Dislikes").setValue(l);
                                System.out.println(value);
                                System.out.println(key);

                            }
                            @Override
                            public void onCancelled(DatabaseError firebaseError) {
                                System.out.println("error");
                            }
                        });

                    }
                });
                return null;
            }

        });
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                percentage.setVisibility(View.INVISIBLE);
            }
        });


    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //  TODO: Prompt with explanation!

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    if (ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }

                return;
            }

        }
    }

    public int findAverage(int likes, int dislike){
        int total = likes + dislike;
        if(total != 0){
            int percentage = ((int) Math.ceil(likes / total)) * 100;
            return percentage;
        }
        return 0;
    }

}
