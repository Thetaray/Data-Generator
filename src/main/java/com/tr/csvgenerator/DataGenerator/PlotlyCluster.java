package com.tr.csvgenerator.DataGenerator;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 * Date: 4/30/16
 * Time: 3:54 PM
 */
public class PlotlyCluster {

    public double x[];
    public double y[];
    public double z[];

    public String mode = "markers";
    public Marker marker;
    public String type = "scatter3d";

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double[] getY() {
        return y;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public double[] getZ() {
        return z;
    }

    public void setZ(double[] z) {
        this.z = z;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" + "\nx:" + Arrays.toString(x) + ",\ny:" + Arrays.toString(y) + ",\nz:" + Arrays.toString(z) + ",\nmode:'" + mode + "',\nmarker:" + marker + ",\ntype:'" + type + "'" + "}";
    }
}

class Marker {
    public String color = "green";
    public int size = 12;
    public String symbol = "circle";
    // line: {
    //   color: 'green',
    //   width: 0.5
    // },
    public double opacity = 0.8;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    @Override
    public String toString() {
        return "{color:'" + color + "',size:" + size + ",symbol:'" + symbol + "',opacity:" + opacity + "}";
    }
}