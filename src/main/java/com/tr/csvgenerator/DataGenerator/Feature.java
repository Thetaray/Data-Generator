package com.tr.csvgenerator.DataGenerator;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Naor Ben David
 * Mail: naor.bendaivd@thetaray.com
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public interface Feature {

    Object NextValue() throws Exception;
}
