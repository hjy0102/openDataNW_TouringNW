package com.kisubs.tournw;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kisubs on 2017-02-25.
 */

//website,kml_name,address2,Address,city,prov,pcode,phone,Name,Descriptn,id,category,company,fax,email,social_networks,summary,catname,X,Y
public class PublicArt{
    String name, address, description;
    int id;
    LatLng latlng;

    public PublicArt(String address, String name,  String description, int id, LatLng latlng){
        this.address = address;
        this.name = name;
        this.description = description;
        this.id = id;
        this.latlng = latlng;
    }

    public String getAddress(){
        return address;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getId(){
        return id;
    }

    public LatLng getLatlng(){
        return latlng;
    }
}