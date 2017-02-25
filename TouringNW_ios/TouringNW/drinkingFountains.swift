//
//  drinkingFountains.swift
//  TouringNW
//
//  Created by Mike Yoon on 2017-02-25.
//  Copyright © 2017 TouringNW. All rights reserved.
//

import Foundation

class drinkingFountains{
    var structureId: String!
    var latLong:LatLong = LatLong();
    
    func getStructureID() -> String{
        return structureId;
    }
    func getLatLong() -> LatLong{
        return latLong;
    }
}
