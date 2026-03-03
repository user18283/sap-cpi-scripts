import com.sap.gateway.ip.core.customdev.util.Message
import groovy.json.JsonSlurper

def Message processData(Message message) {

    String finalErrorMessage = "Unknown error occurred"

    try {

        def errPayload = message.getProperty("Err_Message") as String

        if (errPayload?.trim()) {

            def json = new JsonSlurper().parseText(errPayload)

            // Safely extract top-level message
            def topMessageObj = json?.error?.message

            String topMessage = null

            if (topMessageObj instanceof Map) {
                topMessage = topMessageObj?.value?.toString()
            } else if (topMessageObj instanceof String) {
                topMessage = topMessageObj
            }

            if (topMessage && topMessage.toString().trim().length() > 0) {
                finalErrorMessage = topMessage.trim()
            }
            else if (json?.error?.innererror?.errordetails instanceof List) {

                def messages = json.error.innererror.errordetails
                        .findAll { it?.message }
                        .collect { it.message.toString().trim() }

                if (!messages.isEmpty()) {
                    finalErrorMessage = messages.join(" | ")
                }
            }
        }

    } catch (Exception e) {
        message.setProperty("GroovyException", e.getMessage())
    }

    message.setProperty("Err_Message", finalErrorMessage)

    return message
}
