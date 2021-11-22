package com.mavericks.server.converter;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GeometryConverterString implements AttributeConverter<Geometry, String> {
    @Override
    public String convertToDatabaseColumn(Geometry meta) {
        return "n/a";
    }

    @Override
    public Geometry convertToEntityAttribute(String dbData) {
        try {
            WKTReader reader = new WKTReader();
            return reader.read(dbData);
        } catch (ParseException ex) {
            //Unexpected ParseException decoding json from database
            return null;
        }
    }
}
