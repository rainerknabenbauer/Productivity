package de.nykon.productivity.domain.value

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class InfrastructureConfig {

    @Value("\${server.ui.rootUri}")
    lateinit var frontendUri: String

    @Value("\${server.backend.rootUri}")
    lateinit var backendUri: String

}