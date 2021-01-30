package de.nykon.productivity.purger

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PurgeController(private val purgeService: PurgeService) {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping(path = ["/purge"])
    fun purge(): Int {
        val numberOfPurgedProjects = purgeService.purgeUnusedProjects()
        log.info("$numberOfPurgedProjects projects got purged.")
        return numberOfPurgedProjects
    }
}