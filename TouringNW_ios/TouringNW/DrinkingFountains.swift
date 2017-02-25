//
//  drinkingFountains.swift
//  TouringNW
//
//  Created by Mike Yoon on 2017-02-25.
//  Copyright © 2017 TouringNW. All rights reserved.
//

import Foundation

class DrinkingFountains{
    var structureId: String!
    var latLong:LatLong = LatLong();
    var rating: Int!
    
    func getStructureID() -> String{
        return structureId;
    }
    func getLatLong() -> LatLong{
        return latLong;
    }
    func getRating() -> Int {
        return rating;
    }
}
