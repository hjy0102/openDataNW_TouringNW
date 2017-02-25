package com.example.mikeyoon.touristnw;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kisubs on 2017-02-25.
 */
//BrandModel,Dedicated,InstallYear,ParkName,StructureId,StructureType,X,Y
public class Bench{
    int structureId;
    String parkName;
    LatLng latlng;

    public Bench(int structureId, String parkName, LatLng latlng){
        this.structureId = structureId;
        this.parkName = parkName;
        this.latlng = latlng;
    }

    public int getStructureId() {
        return structureId;
    }

    public String getParkName() {
        return parkName;
    }

    public LatLng getLatlng() {
        return latlng;
    }
}
