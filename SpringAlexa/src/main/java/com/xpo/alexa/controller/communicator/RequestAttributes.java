package com.xpo.alexa.controller.communicator;

import java.util.Date;


public class RequestAttributes {

    private String type;

    private String requestId;

    private Date timestamp;

    private String dialogState;
    
    private String reason;

    private IntentAttributes intent;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getDialogState() {
		return dialogState;
	}

	public void setDialogState(String dialogState) {
		this.dialogState = dialogState;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public IntentAttributes getIntent() {
		return intent;
	}

	public void setIntent(IntentAttributes intent) {
		this.intent = intent;
	}

}
