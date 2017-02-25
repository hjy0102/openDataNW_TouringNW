//
//  ParkBenches.swift
//  TouringNW
//
//  Created by Mike Yoon on 2017-02-25.
//  Copyright © 2017 TouringNW. All rights reserved.
//

import Foundation

class ParkBenches{
    var parkName: String!
    var structureID: String!
    var latLong:LatLong = LatLong();
    var rating:Int!
    
    func getparkName() -> String{
        return parkName;
    }
    
    func getStructureID() -> String{
        return structureID;
    }
    func getLatLong() -> LatLong{
        return latLong;
    }
    func getRating() -> Int {
        return rating;
    }
}
