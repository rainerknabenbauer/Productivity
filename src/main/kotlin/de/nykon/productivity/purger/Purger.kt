package de.nykon.productivity.purger

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Purger(private val purgeService: PurgeService) {

    @Scheduled(cron ="0 5 4 * * *")
    fun purge() {
        val deletedProjects = purgeService.purgeUnusedProjects()
        println("$deletedProjects got purged.")
    }

}