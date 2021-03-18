package de.nykon.productivity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.google.gson.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.serializer.support.SerializationFailedException
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


@Configuration
class JacksonConfig {

    /**
     * Add LocalDateTime capabilities to JSON de-/serialization.
     */
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val module = JavaTimeModule()
        module.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
        return ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(module)
    }

    /**
     * Supply GSON to have another Serializer for integration testing.
     */
    @Bean
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
        return gsonBuilder.create()
    }

}

/**
 * Modify GSON LocalDateTime serialization to match Jackson format.
 */
private class LocalDateTimeSerializer : JsonSerializer<LocalDateTime?> {
    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (Objects.nonNull(src)) {
            val stringVar = src!!.format(DateTimeFormatter.ISO_DATE_TIME)
            return JsonPrimitive(stringVar)
        }

        throw SerializationFailedException("Src was null!")
    }
}