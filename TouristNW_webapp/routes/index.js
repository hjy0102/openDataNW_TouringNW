var express = require('express');
var router = express.Router();


var toRad = function(number) {
  return number * (Math.PI / 180);
};

var calculate = function(lat1,lng1,lat2,lng2,radius) {
  var R = 6371;
  var dlat = toRad((lat2-lat1));
  var dlng = toRad((lng2-lng1));
  var lat1r = toRad(lat1);
  var lat2r = toRad(lat2);

  var a = Math.sin(dlat/2) * Math.sin(dlat/2) +
      Math.sin(dlng/2) * Math.sin(dlng/2) * Math.cos(lat1r) * Math.cos(lat2r);
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
  var d = R * c;
  return d < radius;
};

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.post('/Search', function(req, res, next) {
  var monk = require('monk');
  var db = monk("mongodb://admin:admin@ds161069.mlab.com:61069/nwtour");


  var radius = req.body.radius;
  // var currentLng = req.body.currentLng;
  // var currentLat = req.body.currentLat;
  var currentLng = -122.9109;
  var currentLat = 49.2067;
  var type = req.body.type;
  var collection;
  if (type == 1) {
    collection = db.get('public_washroom');
  } else if (type == 2) {
    collection = db.get('drinking_fountains');
  } else if (type == 3) {
    collection = db.get('benches');
  }

  var results = [];
  collection.find({}, function(err, docs) {
    for (var i = 0; i < docs.length; i++) {
      var facility = docs[i];
      var lng = facility.X;
      var lat = facility.Y;
      if (calculate(currentLat, currentLng, lat, lng, radius)) {
        var result = {
          X: facility.X,
          Y: facility.Y
        };
        if (type == 1) {
          result.content = facility.Hours;
        } else if (type == 2) {
          result.content = facility.ParkName;
        } else if (type == 3) {
          result.content = facility.ParkName
        }
        results.push(result);
      }
    }
    res.send({
      response: results

    });
  });
});

router.post('/parks', function (req, res) {
  var db = req.db;
  var collection = db.get('parks');
  /*TODO: need modification*/
  collection.findById(userID, function (err, doc) {
    var status;
    var message;
    var newUserName = req.body.newUserName;
    collection.find({ username: newUserName }, function (err, docs) {
      if (newUserName == "") {
        status = "warning";
        message = "Please enter a new user name >_<";
      }
      else if (docs == "") {
        status = "success";
        message = "You have successfully updated your user name :D";
        collection.findAndModify({ _id: userID }, { $set: { username: newUserName } });
      }
      else {
        status = "failure";
        message = "The user name has already exist, please use another one.";
      }
      res.send({
        "status": status,
        "returnData": {
          "message": message,
          "latestUserName": newUserName
        }
      });
    });
  });
});

module.exports = router;
