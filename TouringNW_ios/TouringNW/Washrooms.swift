//
//  Washrooms.swift
//  TouringNW
//
//  Created by Mike Yoon on 2017-02-25.
//  Copyright © 2017 TouringNW. All rights reserved.
//

import Foundation

class Washroom {
    var name: String!
    var category: String!
    var address: String!
    var hours: String!
    var neighbourhood: String!
    var latLong:LatLong = LatLong();
    var rating: Int!
    
    func getName() -> String{
        return name;
    }
    func getCategory() -> String{
        return category;
    }
    func getAddress() -> String{
        return address;
    }
    func getHours() -> String{
        return hours;
    }
    func getLatLong() -> LatLong{
        return latLong;
    }
    func getRating() -> Int {
        return rating;
    }
}
