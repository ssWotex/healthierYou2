package com.example.healthieryou;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

class Coordinates extends SugarRecord {
    double latitudeStart;
    double longitudeStart;
    double startTime;

    double endTime;
    double latitudeEnd;
    double longitudeEnd;

    public Coordinates(double latitudeStart, double longitudeStart, double startTime) {
        this.latitudeStart = latitudeStart;
        this.longitudeStart = longitudeStart;
        this.startTime = startTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public void setLatitudeEnd(double latitudeEnd) {
        this.latitudeEnd = latitudeEnd;
    }

    public void setLongitudeEnd(double longitudeEnd) {
        this.longitudeEnd = longitudeEnd;
    }
}
