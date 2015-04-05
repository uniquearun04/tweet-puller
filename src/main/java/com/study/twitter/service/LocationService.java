package com.study.twitter.service;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import twitter4j.GeoLocation;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;


@Service
public class LocationService {

	private static final Logger logger = Logger.getLogger(LocationService.class);

	public GeoLocation getLocation(String ipAddress) {

		String dataFile = "location/GeoLiteCity.dat";
		return getLocation(ipAddress, dataFile);

	}

	
	private GeoLocation getLocation(String ipAddress, String locationDataFile) {

		GeoLocation geoLocation = null;

		URL url = getClass().getClassLoader().getResource(locationDataFile);

		if (url == null) {
			logger.error("location database is not found - "
					+ locationDataFile);
		} else {

			try {

				LookupService lookup = new LookupService(url.getPath(),
						LookupService.GEOIP_MEMORY_CACHE);
				Location locationServices = lookup.getLocation(ipAddress);

				geoLocation = new GeoLocation(Double.parseDouble(String
						.valueOf(locationServices.latitude)), Double.parseDouble(String
						.valueOf(locationServices.longitude)));
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}

		return geoLocation;

	}
}
