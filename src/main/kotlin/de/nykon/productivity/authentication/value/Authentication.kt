package de.nykon.productivity.authentication.value

import de.nykon.productivity.exceptions.FormattingException
import org.springframework.core.codec.EncodingException
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.http.ResponseEntity
import java.lang.Exception
import java.util.*

/**
 * Credentials for a protected project.
 */
@Document(collection = "credentials")
data class Authentication(
    @Id val projectId: String,
    val token: String
) {
    companion object {
        fun fromBase64(authorization: String): Authentication {

            val base64Authorization: String
            try {
                base64Authorization = authorization.split(" ")[1]
            } catch (e: Exception) {
                throw FormattingException(
                    "Authorization is not properly formatted. Should be 'Authorization: Basic projectId:token'")
                //TODO throw formatting exception

            }

            val authentication: Authentication
            try {
                val decodedBytes = Base64.getDecoder().decode(base64Authorization)
                val decodedString = String(decodedBytes).split(":")

                return Authentication(decodedString[0], decodedString[1])
            } catch (e: Exception) {
                throw EncodingException("Authorization could not be decoded.")
                //TODO throw decoding exception

            }
        }
    }
}