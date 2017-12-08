package com.xpo.alexa.controller.communicator;

public class AlexaResponse
{
    private String version;

    private SessionCustomAttributes sessionAttributes;

    private ResponseAttributes response;

    public AlexaResponse () {
    	this.version = "1.0";
    	this.sessionAttributes = new SessionCustomAttributes();
    	this.response = new ResponseAttributes();
    }
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public SessionCustomAttributes getSessionAttributes() {
		return sessionAttributes;
	}

	public void setSessionAttributes(SessionCustomAttributes sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}

	public ResponseAttributes getResponse() {
		return response;
	}

	public void setResponse(ResponseAttributes response) {
		this.response = response;
	}

}
