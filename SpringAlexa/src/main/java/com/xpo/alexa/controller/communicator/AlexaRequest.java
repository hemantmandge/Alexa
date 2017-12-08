package com.xpo.alexa.controller.communicator;

public class AlexaRequest
{
    private String version;

    private SessionCustomAttributes session;

    private RequestAttributes request; 	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public SessionCustomAttributes getSession() {
		return session;
	}

	public void setSession(SessionCustomAttributes session) {
		this.session = session;
	}

	public RequestAttributes getRequest() {
		return request;
	}

	public void setRequest(RequestAttributes request) {
		this.request = request;
	}

}
