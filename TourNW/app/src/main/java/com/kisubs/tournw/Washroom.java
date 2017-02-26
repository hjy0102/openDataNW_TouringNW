package com.kisubs.tournw;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kisubs on 2017-02-25.
 */
//Name,Category,Address,Neighbourhood,Hours,Source,Accessible,X,Y
public class Washroom{
  String name, address, hours, accessible;
  LatLng latlng;

  public Washroom(String name, String address, String hours, String accessible, LatLng latlng){
      this.name = name;
      this.address = address;
      this.hours = hours;
      this.accessible = accessible;
      this.latlng = latlng;
  }



  public String getName() {
        return name;
  }

  public String getAddress() {
        return address;
  }

  public String getHours() {
        return hours;
  }

  public String getaccessible() {
        return accessible;
  }

  public LatLng getLatlng() {
        return latlng;
  }
}
