// Logging
// This Groovy Flowstep Version 2, running with Groovy 4, Downgrade the script if older behaviour needed.

import com.sap.it.script.v2.api.Message;

def Message processData(Message message) {
	
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties() as Map<String, Object>;
	
	def propertiesAsString ="\n";
	properties.each{ it -> propertiesAsString = propertiesAsString + "${it}" + "\n" };

	def messageLog = messageLogFactory.getMessageLog(message);
	if(messageLog != null && properties.get("enableLog") == "true") {
		messageLog.addAttachmentAsString("Log - Payload" , "\n Properties \n ----------   \n" + propertiesAsString +
		                                                   "\n Body \n ----------  \n\n" + body,
		                                                   "text/xml");
	}
	
	return message;
}
