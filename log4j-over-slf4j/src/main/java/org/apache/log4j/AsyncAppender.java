package org.apache.log4j;

import org.apache.log4j.spi.AppenderAttachable;

import java.util.Enumeration;

public class AsyncAppender extends AppenderSkeleton implements AppenderAttachable {

	public static final int DEFAULT_BUFFER_SIZE = 128;

	public void addAppender(Appender newAppender) {
	}

	public Enumeration getAllAppenders() {
		return null;
	}

	public Appender getAppender(String name) {
		return null;
	}

	public boolean isAttached(Appender appender) {
		return false;
	}

	public void removeAllAppenders() {
	}

	public void removeAppender(Appender appender) {
	}

	public void removeAppender(String name) {
	}

	public void setBufferSize(final int size){
	}

	public int getBufferSize(){
		return DEFAULT_BUFFER_SIZE;
	}

	public boolean getBlocking(){
		return false;
	}

	public void setBlocking(final boolean value){
	}

	public boolean getLocationInfo(){
		return false;
	}

	public void setLocationInfo(final boolean flag){
	}

	public void close(){
	}

	public boolean requiresLayout(){
		return false;
	}
}
