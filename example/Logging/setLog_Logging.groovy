import com.sap.gateway.ip.core.customdev.util.Message

def Message logInputPayload(Message message) {
    return log("Input Payload", message)
}

def Message logOutputPayload(Message message) {
    return log("Output Payload", message)
}

def Message logErrorPayload(Message message) {
    return LogPropertyPayload("Input Payload Exception Flow", message)
}

def Message logErrors(Message message) {
    return LogPropertyPayload("Erros", message)
}

def Message logIgnoredPayload(Message message) {
    return log("IgnoredPayload", message)
}

def Message log(String title,Message message) {

    map = message.getProperties();
	property_ENABLE_LOGGING = map.get("EnableLogging");
	def messageLog = messageLogFactory.getMessageLog(message);
	if (property_ENABLE_LOGGING.toUpperCase().equals("TRUE") && messageLog != null) {	
		def body = message.getBody(java.lang.String) as String;
		messageLog.addAttachmentAsString(title, body, "text/plain");
	}	

	return message;
}


def Message LogPropertyPayload(String title,Message message) {

    map = message.getProperties();
    def body = map.get("sourcePayload");
	def messageLog = messageLogFactory.getMessageLog(message);
	if (messageLog != null) {
	    messageLog.addAttachmentAsString(title, body, "text/plain");
	}
	return message;
}
