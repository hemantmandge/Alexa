package com.xpo.alexa.controller.communicator;

import java.util.Map;

public class IntentAttributes {

    private String name;

    private Map<String, SlotAttributes>  slots;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, SlotAttributes> getSlots() {
		return slots;
	}

	public void setSlots(Map<String, SlotAttributes> slots) {
		this.slots = slots;
	}

}
