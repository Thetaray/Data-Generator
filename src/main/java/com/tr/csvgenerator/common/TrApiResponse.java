package com.tr.csvgenerator.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by erez on 11/25/15.
 */
public class TrApiResponse extends HashMap<String, Object> {
    private static final long serialVersionUID = 7083556881719883520L;

    private static final String STATUS = "status";
    private static final String MESSAGE = "Message";
    private static final String TIMESTAMP = "ts";
    private static final String HOST = "host";

    public enum StatusCode{
        Ok, Error, BadData
    }

    public TrApiResponse() {
        setTimestamp(null);
        String hostname = "";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = "Unresolved";
        }
        put(HOST, hostname);
    }

    public TrApiResponse(StatusCode retCode) {
        this();
        setRetCode(retCode);
    }

    public void setOk() {
        setRetCode(StatusCode.Ok);
    }

    public void setError(String message) {
        setRetCode(StatusCode.Error);
        put(MESSAGE, message);
    }

    public void setRetCode(StatusCode retCode) {
        put(STATUS, retCode);
    }

    public void setMessage(String msg) {
        put(MESSAGE, msg);
    }

    public void setTimestamp(Date dt) {
        if (dt==null) {
            dt = new Date();
        }
        put(TIMESTAMP, dt.toString());
    }

}
