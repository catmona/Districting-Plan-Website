package com.mavericks.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@SpringBootApplication
@RestController
@RequestMapping(path = "api/")
public class ServerApplication {
	private static final String AR="AR.2012";
	private static final String NV="NV";
	private static final String WA="WA.2012";

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
	@GetMapping(value="districts",produces = MediaType.APPLICATION_JSON_VALUE)
	public String getDistricts(@RequestParam("state")String state,@RequestParam("file")String file,
							   @RequestParam(value = "year",defaultValue = "2012")Integer year){
		//construct the filepath
		String path="data/";
		if(state.equals("Arkansas")){
			path+=(AR);
		}
		else if(state.equals("Nevada")){
			path+=(NV)+"/"+year;
		}
		else if(state.equals("Washington")){
			path+=(WA);
		}
		else{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid query params");
		}
		path+="/"+file+".geojson";

		try{
			//read file and return
			InputStream in = new ClassPathResource(path).getInputStream();
			String data = StreamUtils.copyToString(in, Charset.defaultCharset());
			in.close();
			return data;
		}catch (IOException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"error reading file",e);
		}
	}



}

