package com.example.mikeyoon.touristnw;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kisubs on 2017-02-25.
 */

//website,kml_name,address2,Address,city,prov,pcode,phone,Name,Descriptn,id,category,company,fax,email,social_networks,summary,catname,X,Y
public class publicArt{
    String name, address, descripttion;
    int id;
    LatLng latlng;

    public publicArt(String address, String name,  String descripttion, int id, LatLng latlng){
        this.address = address;
        this.name = name;
        this.descripttion = descripttion;
        this.id = id;
        this.latlng = latlng;
    }

    public String getAddress(){
        return address;
    }

    public String getName(){
        return name;
    }

    public String getDescripttion(){
        return descripttion;
    }

    public int getId(){
        return id;
    }

    public LatLng getLatlng(){
        return latlng;
    }
}