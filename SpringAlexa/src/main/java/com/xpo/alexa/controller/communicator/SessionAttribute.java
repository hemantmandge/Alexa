package com.xpo.alexa.controller.communicator;

public class SessionAttribute {

	private String sessionId; 

	private ApplicationAttributes application;

	private SessionCustomAttributes attributes;

	private UserAttributes user;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public ApplicationAttributes getApplication() {
		return application;
	}

	public void setApplication(ApplicationAttributes application) {
		this.application = application;
	}

	public SessionCustomAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(SessionCustomAttributes attributes) {
		this.attributes = attributes;
	}

	public UserAttributes getUser() {
		return user;
	}

	public void setUser(UserAttributes user) {
		this.user = user;
	}

    //public Boolean new;
}
