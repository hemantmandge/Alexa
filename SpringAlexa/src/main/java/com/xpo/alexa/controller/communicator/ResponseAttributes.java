package com.xpo.alexa.controller.communicator;

public class ResponseAttributes {

	private Boolean shouldEndSession;
	
	private OutputSpeechAttributes outputSpeech;
	
	private CardAttributes card;
	
	private RepromptAttributes reprompt;
	
	public ResponseAttributes() {
		this.shouldEndSession = true;
		this.outputSpeech = new OutputSpeechAttributes();
		this.card = new CardAttributes();
		this.reprompt = new RepromptAttributes();
	}

	public Boolean getShouldEndSession() {
		return shouldEndSession;
	}

	public void setShouldEndSession(Boolean shouldEndSession) {
		this.shouldEndSession = shouldEndSession;
	}

	public OutputSpeechAttributes getOutputSpeech() {
		return outputSpeech;
	}

	public void setOutputSpeech(OutputSpeechAttributes outputSpeech) {
		this.outputSpeech = outputSpeech;
	}

	public CardAttributes getCard() {
		return card;
	}

	public void setCard(CardAttributes card) {
		this.card = card;
	}

	public RepromptAttributes getReprompt() {
		return reprompt;
	}

	public void setReprompt(RepromptAttributes reprompt) {
		this.reprompt = reprompt;
	}

}
