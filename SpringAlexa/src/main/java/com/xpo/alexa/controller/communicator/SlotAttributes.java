package com.xpo.alexa.controller.communicator;

public class SlotAttributes {

	    private String name;
	    
	    private String value;
	    
	    private String  confirmationStatus;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getConfirmationStatus() {
			return confirmationStatus;
		}

		public void setConfirmationStatus(String confirmationStatus) {
			this.confirmationStatus = confirmationStatus;
		}
	    
}
