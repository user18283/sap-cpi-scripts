import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {

    def messageLog = messageLogFactory.getMessageLog(message)
    def payload = message.getProperty("sourcePayload")

    if (messageLog != null && payload != null) {
        messageLog.addAttachmentAsString(
            "source_payload in Exception",
            payload.toString(),
            "text/plain"
        )
    }

    return message
}
