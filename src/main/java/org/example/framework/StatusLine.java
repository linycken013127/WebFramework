package org.example.framework;

public class StatusLine {
    private final String version;
    private int statusCode;
//    private final String reasonPhrase;

    public StatusLine(String version, int statusCode) {
        this.version = version;
        this.statusCode = statusCode;
    }

    public String getVersion() {
        return version;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
