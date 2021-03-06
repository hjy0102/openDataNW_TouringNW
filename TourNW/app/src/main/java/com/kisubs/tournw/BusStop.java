package com.kisubs.tournw;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kisubs on 2017-02-25.
 */
//BUSSTOPNUM,ONSTREET,ATSTREET,DIRECTION,POSITION,STATUS,ACCESSIBLE,CITY_NAME,X,Y
public class BusStop{
  int stopNumber;
  String onStreet, atStreet, direction, position, status, accessible, cityName;
  LatLng latlng;

  public BusStop(int stopNumber, String onStreet, String atStreet, String direction, String position,
    String status, String accessible, String cityName, LatLng latlng){
      this.stopNumber = stopNumber;
      this.onStreet = onStreet;
      this.atStreet = atStreet;
      this.direction = direction;
      this.position = position;
      this.status = status;
      this.accessible = accessible;
      this.cityName = cityName;
      this.latlng = latlng;
  }

  public int getStopNumber() {
        return stopNumber;
  }

  public String getOnStreet() {
        return onStreet;
  }

  public String getAtStreet() {
        return atStreet;
  }

  public String getDirection() {
        return direction;
  }

  public String getPosition() {
        return position;
  }

  public String getStatus() {
        return status;
  }

  public String getAccessible() {
        return accessible;
  }

  public String getCityName() {
        return cityName;
  }

  public LatLng getLatlng() {
        return latlng;
  }
}
