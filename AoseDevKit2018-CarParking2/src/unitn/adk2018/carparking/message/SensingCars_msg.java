package unitn.adk2018.carparking.message;

import unitn.adk2018.event.RequestMessage;

public final class SensingCars_msg extends RequestMessage {
	
	public SensingCars_msg(String _from, String _to) {
		super(_from, _to);
	}
	
}
