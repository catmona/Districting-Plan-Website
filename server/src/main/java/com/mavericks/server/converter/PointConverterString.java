package com.mavericks.server.converter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mavericks.server.entity.Point;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter(autoApply = true)
public class PointConverterString implements AttributeConverter<Point, String> {

    @Override
    public String convertToDatabaseColumn(Point meta) {
        return meta.toString();
    }

    @Override
    public Point convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        String[] s = dbData.split(",");
        return new Point(Double.parseDouble(s[0]), Double.parseDouble(s[1]));
    }

}
