package com.tr.csvgenerator.RandomDataGenerator.ContainerPackage;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public interface Container {

    public List<Object> Next() throws Exception;

    public Integer getNumberOfElements();

    boolean hasNext();

    public String getLabel();

    void setLabel(String no_label);

    public Integer getLabelColumn();
}
