package com.tr.csvgenerator.DataGenerator;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public interface Container {

    public List<Object> Next() throws Exception;

    public Integer getNumberOfElements();

    boolean hasNext();

    public String getLabel();

    public Integer getLabelColumn();

    void setLabel(String no_label);
}
