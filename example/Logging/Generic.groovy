
import com.sap.gateway.ip.core.customdev.util.Message;

def addAttachment(Message message, String baseName, String contentType, String logPropertyName) {
    def body        = message.getBody(java.lang.String) as String
    def messageLog  = messageLogFactory.getMessageLog(message)
    def propertyMap = message.getProperties()
    def headerMap   = message.getHeaders()

    //String logVar = propertyMap.get("StorePayload")
    String logVar   = headerMap.get("StorePayload")
    
    String mode = headerMap.get("Mode") ?: ""       // Safe null handling
    String suffix = mode ? "_${mode}" : ""          // Add suffix only if Mode exists

    if (messageLog != null && "true".equals(logVar)) {
        messageLog.setStringProperty(logPropertyName, "Persist Message as Attachment to Log")
        messageLog.addAttachmentAsString("${baseName}${suffix}", body, contentType)
    }
    return message
}

// ==== Individual message logging methods ====

def Message incomingMessage(Message message) {
    return addAttachment(message, "01_IncomingPayload", "text/xml", "Logging before Mapping")
}

def Message messageBeforeMapping(Message message) {
    return addAttachment(message, "02_MessageBeforeMapping", "text/xml", "Logging before Mapping")
}

def Message messageAfterMapping(Message message) {
    return addAttachment(message, "03_MessageAfterMapping", "text/xml", "Logging before Mapping")
}

def Message outgoingMessage(Message message) {
    return addAttachment(message, "04_OutgoingPayload", "text/xml", "Logging before Mapping")
}

def Message responseMessage(Message message) {
    return addAttachment(message, "05_ResponsePayload", "text/xml", "Logging Response Payload")
}

def Message debugMessage(Message message) {
    def body = message.getBody(java.lang.String) as String
    def messageLog = messageLogFactory.getMessageLog(message)
    def propertyMap = message.getProperties()
    def headerMap = message.getHeaders()
    String logVar = propertyMap.get("DebugPayload")
    String mode = headerMap.get("Mode") ?: ""
    String suffix = mode ? "_${mode}" : ""

    if (messageLog != null && "true".equals(logVar)) {
        messageLog.setStringProperty("Logging debug Payload", "Persist Message as Attachment to Log")
        messageLog.addAttachmentAsString("_Debug${suffix}", body, "text")
    }
    return message
}

def Message errorMessage(Message message) {
    return addAttachment(message, "_ERROR", "text", "Logging Error Payload")
}

def Message rawMessage(Message message) {
    return addAttachment(message, "99_RawPayload", "text", "Logging Response Payload")
}

def Message errorFeedback(Message message) {
    return addAttachment(message, "06_ErrorPayload", "text", "Logging Error Response Payload")
}

def Message successFeedback(Message message) {
    return addAttachment(message, "07_SuccessPayload", "text", "Logging Success Response Payload")
}
