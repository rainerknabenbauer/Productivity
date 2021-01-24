package de.nykon.productivity.utility

import de.nykon.productivity.PurgeService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Purger(private val purgeService: PurgeService) {

    @Scheduled(cron ="* 5 4 * * *")
    fun purge() {
        val deletedProjects = purgeService.purgeUnusedProjects()
        println("$deletedProjects got purged.")
    }

}