package com.mavericks.server;

import com.mavericks.server.entity.CensusBlock;
import com.mavericks.server.repository.CensusBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

public class PostProcessing {

    @Autowired
    public static CensusBlockRepository repo;

    public static void main(String[] args) throws IOException {
//        String path = "arkansas_county_boundaries.geojson";
//        InputStream in = new ClassPathResource(path).getInputStream();
//        String data = StreamUtils.copyToString(in, Charset.defaultCharset());
//        in.close();
//        /**
//         FeatureCollection featureCollection = (FeatureCollection) GeoJSONFactory.create(data);
//         GeoJSONReader reader = new GeoJSONReader();
//         Feature[]fs=featureCollection.getFeatures();
//         List<Feature> collect=Arrays.asList(fs);
//         GeoJSON json= new GeoJSONWriter().write(collect);**/
//        System.out.println(data);


    }
}
