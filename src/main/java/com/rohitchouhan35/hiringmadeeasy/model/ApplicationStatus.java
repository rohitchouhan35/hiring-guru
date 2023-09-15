package com.rohitchouhan35.hiringmadeeasy.model;

public enum ApplicationStatus {
    NEW("New"),
    IN_REVIEW("In Review"),
    INTERVIEWED("Interviewed"),
    HIRED("Hired"),
    REJECTED("Rejected");

    private final String displayName;

    ApplicationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

