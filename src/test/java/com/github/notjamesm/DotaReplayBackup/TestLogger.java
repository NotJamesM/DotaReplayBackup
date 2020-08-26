package com.github.notjamesm.DotaReplayBackup;

import org.slf4j.Logger;
import org.slf4j.Marker;

import java.util.ArrayList;
import java.util.List;

public class TestLogger implements Logger {

    private final List<String> infoEvents = new ArrayList<>();
    private final List<String> warnEvents = new ArrayList<>();
    private final List<String> debugEvents = new ArrayList<>();
    private final List<String> errorEvents = new ArrayList<>();
    private final List<Throwable> errorCauses = new ArrayList<>();
    private final List<Throwable> warnCauses = new ArrayList<>();

    public List<String> getInfoEvents() {
        return infoEvents;
    }

    public List<String> getWarnEvents() {
        return warnEvents;
    }

    public List<String> getDebugEvents() {
        return debugEvents;
    }

    public List<String> getErrorEvents() {
        return errorEvents;
    }

    public List<Throwable> getErrorCauses() {
        return errorCauses;
    }

    public List<Throwable> getWarnCauses() {
        return warnCauses;
    }

    @Override
    public String getName() {
        return "TestLogger";
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String msg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void trace(String format, Object arg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void trace(String format, Object... arguments) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void trace(String msg, Throwable t) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public void trace(Marker marker, String msg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String msg) {
        debugEvents.add(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void debug(String format, Object... arguments) {
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    public void debug(String msg, Throwable t) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return false;
    }

    @Override
    public void debug(Marker marker, String msg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String msg) {
        infoEvents.add(msg);
    }

    @Override
    public void info(String format, Object arg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void info(String format, Object... arguments) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void info(String msg, Throwable t) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return false;
    }

    @Override
    public void info(Marker marker, String msg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String msg) {
        warnEvents.add(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void warn(String format, Object... arguments) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void warn(String msg, Throwable t) {
        warn(msg);
        warnCauses.add(t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return false;
    }

    @Override
    public void warn(Marker marker, String msg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String msg) {
        errorEvents.add(msg);
    }

    @Override
    public void error(String format, Object arg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void error(String format, Object... arguments) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void error(String msg, Throwable t) {
        error(msg);
        errorCauses.add(t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return false;
    }

    @Override
    public void error(Marker marker, String msg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
