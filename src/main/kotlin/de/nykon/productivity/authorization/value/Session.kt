package de.nykon.productivity.authorization.value

import de.nykon.productivity.exceptions.DecodingException
import de.nykon.productivity.exceptions.FormattingException
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
 * Session details for a protected project.
 */
@Document(collection = "sessions")
data class Session(
    @Id val projectId: String,
    val token: String
) {
    companion object {
        fun fromBase64(authorization: String): Session {

            val base64Authorization: String
            try {
                base64Authorization = authorization.split(" ")[1]
            } catch (e: Exception) {
                throw FormattingException(
                    "Authorization is not properly formatted. Should be 'Authorization: Basic projectId:token'")
            }

            try {
                val decodedBytes = Base64.getDecoder().decode(base64Authorization)
                val decodedString = String(decodedBytes).split(":")

                return Session(decodedString[0], decodedString[1])
            } catch (e: Exception) {
                e.printStackTrace()
                throw DecodingException("Authorization could not be decoded.")
            }
        }
    }
}