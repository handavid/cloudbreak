package com.sequenceiq.it.cloudbreak.newway.context;

public class RunningParameter {

    private String who;

    private boolean skipOnFail = true;

    private String key;

    private boolean logError;

    public String getWho() {
        return who;
    }

    public RunningParameter withWho(String who) {
        this.who = who;
        return this;
    }

    public boolean isSkipOnFail() {
        return skipOnFail;
    }

    public boolean isLogError() {
        return logError;
    }

    public RunningParameter withSkipOnFail(boolean skipOnFail) {
        this.skipOnFail = skipOnFail;
        return this;
    }

    public RunningParameter withLogError(boolean logError) {
        this.logError = logError;
        return this;
    }

    public String getKey() {
        return key;
    }

    public RunningParameter withKey(String key) {
        this.key = key;
        return this;
    }

    public static RunningParameter emptyRunningParameter() {
        return new RunningParameter();
    }

    public static RunningParameter force() {
        return new RunningParameter()
                .withSkipOnFail(false);
    }

    public static RunningParameter who(String who) {
        return new RunningParameter()
                .withWho(who);
    }

    public static RunningParameter key(String key) {
        return new RunningParameter()
                .withKey(key);
    }

    public RunningParameter withAggregate(String aggregation) {
        return this;
    }
}
