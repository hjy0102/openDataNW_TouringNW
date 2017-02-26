//
//  PublicArt.swift
//  TouringNW
//
//  Created by Mike Yoon on 2017-02-25.
//  Copyright © 2017 TouringNW. All rights reserved.
//

import Foundation

class PublicArt{
    var name: String!
    var address: String!
    var description: String!
    var id: String!
    var latLong:LatLong = LatLong();
    var rating: Int!
    
    func getName() -> String{
        return name;
    }
    
    func getAddress() -> String{
        return address;
    }
    func getDescription() -> String{
        return description;
    }
    func getId() -> String{
        return id;
    }
    func getLatLong() -> LatLong{
        return latLong;
    }
    func getRating() -> Int{
        return rating;
    }
}
