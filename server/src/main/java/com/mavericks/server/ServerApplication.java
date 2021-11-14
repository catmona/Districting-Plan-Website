package com.mavericks.server;

import com.mavericks.server.entity.Districting;
import com.mavericks.server.entity.State;
import com.mavericks.server.repository.DistrictingRepository;
import com.mavericks.server.repository.StateRepository;
import com.mavericks.server.service.DistrictingService;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.io.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSON;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping(path = "api/")
public class ServerApplication {
	private static final String AR="AR";
	private static final String NV="NV";
	private static final String WA="WA";

	//@Autowired
	//private DistrictingService districtingService;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	/**
	 *
	 * Get request endpoint: http://localhost:8080/api/districts
	 * Queries sent in the form of:
	 *
	 * {
	 *     "state":"NAME_OF_STATE",
	 *     "file":"NAME_OF_GEOJSON_FILE",
	 *     "year":"YEAR_OF_DISTRICTING"
	 * }
	 *
	 * Sample query to send via postman: http://localhost:8080/api/districts?state=Nevada&file=State&year=2013
	 */
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping(value="districts", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getDistricts(@RequestParam("state")String state,@RequestParam("file")String file,
							   @RequestParam(value = "year",defaultValue = "2012")Integer year){
		//construct the filepath
		String path="data/";
		switch (state) {
			case "Arkansas":
				path += (AR) + "/" + year;
				break;
			case "Nevada":
				path += (NV) + "/" + year;
				break;
			case "Washington":
				path += (WA) + "/" + year;
				break;
			default:
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid query params");
		}
		path+="/"+file+".geojson";

		try{
			//read file and return
			InputStream in = new ClassPathResource(path).getInputStream();
			String data = StreamUtils.copyToString(in, Charset.defaultCharset());
			in.close();
			/**
			FeatureCollection featureCollection = (FeatureCollection) GeoJSONFactory.create(data);
			GeoJSONReader reader = new GeoJSONReader();
			Feature[]fs=featureCollection.getFeatures();
			List<Feature> collect=Arrays.asList(fs);
			GeoJSON json= new GeoJSONWriter().write(collect);**/
			return data;
		}catch (IOException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"error reading file",e);
		}
	}


	/**
	 *
	 * Sample query to send via postman: http://localhost:8080/api/stateinfo
	 *
	 * Just returns washington state data
	 * @return
	 */
	@CrossOrigin(origins = "http://localhost:8081")
	@GetMapping(value = "stateinfo",produces = MediaType.APPLICATION_JSON_VALUE)
	public String stateData(){
		try{
			//read file and return
			InputStream in = new ClassPathResource("district_data.json").getInputStream();
			String data = StreamUtils.copyToString(in, Charset.defaultCharset());
			in.close();
			return data;
		}catch (IOException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"error reading file",e);
		}
	}

}

