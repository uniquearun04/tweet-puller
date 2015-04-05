<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>

		<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		
		<script>
	var map;
 	var centerLat = 28.5355;
 	var centerLong = 77.391;
 
	function showMap(latitude,longitude) { 
 
		var googleLatandLong = new google.maps.LatLng(latitude,longitude);
 
		var mapOptions = { 
			zoom: 9,
			center: googleLatandLong,
			mapTypeId: google.maps.MapTypeId.ROADMAP 
		};
 
		var mapDiv = document.getElementById("map");
		map = new google.maps.Map(mapDiv, mapOptions);
 		map.setCenter(googleLatandLong);
 
	}
 
	function addMarker(map, latlong, title, content) { 
 
		var markerOptions = {
			position: latlong, 
			map: map,
			title: title,
			content:centerLat +"-"+centerLong,
			clickable: true
		};
		var marker = new google.maps.Marker(markerOptions); 
	}
	

	
/* 	function addPoint(status){
		var latLong = new google.maps.LatLng(latitude,longitude);
	} */
 
	function loadTweets(){
		$.getJSON("${pageContext.request.contextPath}/twitter/getTweets",
				"", 
				function(data) {
		 
					var data = JSON.stringify(data);
					var json = JSON.parse(data);
					
					showMap(centerLat, centerLong);
					
					for(i = 0; i < json.length; i++){
						var tweet = json[i];
						var geoLocation = tweet.geoLocation;
						var user = tweet.user.name;
						var text = tweet.text;
						var place = tweet.place;
						if(place === null && typeof place === "object"){
						} else {
							place = place.fullName;
						}
						var tweetText = "user: "+user +"\n"+"place: "+place+"\n"+"tweet: "+text;
						console.log("user: "+ user);
						console.log("text: "+text);
						console.log("geoLocation: "+geoLocation);
						if(geoLocation === null && typeof geoLocation === "object"){
							console.log("got null geoLocation.");
						} else {
							console.log("got non-null geolocation");
							var latitude = geoLocation.latitude;
							var longitude = geoLocation.longitude;
							var gLatLong = new google.maps.LatLng(latitude,longitude);
							console.log("lattitue: "+latitude + ",longitued: "+ longitude+", tweetText: "+tweetText)
							addMarker(map, gLatLong, tweetText, "");
						}
						
						
					}
					$("#result").html(data)
		 
				})
				.done(function() {							
				})
				.fail(function() { 
				})
				.complete(function() { 			
				});
	}

	function init(){
		showMap(centerLat, centerLong);
		var title = "Your current Location"; 
		var googleLatandLong = new google.maps.LatLng(centerLat,centerLong);
		addMarker(map, googleLatandLong, title, "");
		
		var intervalID = setInterval(loadTweets, 30000);
	}
  </script>
	</head> 
	<body onload="init()">
		
		<div id="map" style="width: 800px; height: 800px"></div>

	</body>
</html>
