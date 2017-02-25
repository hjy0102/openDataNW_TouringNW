//
//  Park.swift
//  TouringNW
//
//  Created by Mike Yoon on 2017-02-25.
//  Copyright © 2017 TouringNW. All rights reserved.
//

import Foundation

class Park{
    var streetName:String!
    var streetNumber:String!
    var parkName: String!
    var category: String!
    var neighbourhood: String!
    var rating: Int!
    
    
    func getStreetName() -> String{
        return streetName;
    }
    func getStreetNumber() -> String{
        return streetNumber;
    }
    func getParkName() -> String{
        return parkName;
    }
    func getCategory() -> String{
        return category;
    }
    func getNeighbourhood() -> String{
        return neighbourhood;
    }
    func getRating() -> Int {
        return rating;
    }

}
