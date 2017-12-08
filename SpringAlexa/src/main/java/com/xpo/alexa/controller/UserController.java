package com.xpo.alexa.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.SpeechletRequest;
import com.xpo.alexa.controller.communicator.AlexaRequest;
import com.xpo.alexa.controller.communicator.AlexaResponse;
import com.xpo.alexa.controller.communicator.OutputSpeechAttributes;
import com.xpo.alexa.controller.communicator.ResponseAttributes;
import com.xpo.alexa.controller.communicator.SessionCustomAttributes;

@CrossOrigin
@RestController
@RequestMapping("/shipment")
public class UserController {

	
	@RequestMapping(value = "/getDetails", method = RequestMethod.POST)
	public AlexaResponse user(@RequestBody AlexaRequest alexaRequest) {
		AlexaResponse alexaResponse = new AlexaResponse();
		alexaResponse.setVersion("1.0");
		ResponseAttributes response = new ResponseAttributes();
		OutputSpeechAttributes outputSpeech = new OutputSpeechAttributes();
		outputSpeech.setType("PlainText");
		outputSpeech.setText("Hello Aditi");
		response.setOutputSpeech(outputSpeech);
		response.setShouldEndSession(true);
		alexaResponse.setResponse(response);
		return alexaResponse;
	}
	
	@RequestMapping(value = "/test")
	public String greet() {
		return "Testing Service";
	}
	
	@RequestMapping(value = "/getMoreDetails", method = RequestMethod.POST)
	public String userDetails(@RequestBody SessionCustomAttributes sessionCustomAttributes) {
		SpeechletRequestEnvelope<SpeechletRequest> av = null ;
		SpeechletRequestEnvelope<IntentRequest> envelope = null;
		return "Success";
	}
}
