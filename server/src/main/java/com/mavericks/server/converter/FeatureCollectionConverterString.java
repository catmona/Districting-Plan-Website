package com.mavericks.server.converter;

import com.mavericks.server.entity.Point;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSONFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class FeatureCollectionConverterString implements AttributeConverter<FeatureCollection, String> {

    @Override
    public String convertToDatabaseColumn(FeatureCollection meta) {
        return "";
    }

    @Override
    public FeatureCollection convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        FeatureCollection featureCollection = (FeatureCollection) GeoJSONFactory.create(dbData);
        return featureCollection;
    }
}
