package de.nykon.productivity.utility

import de.nykon.productivity.PurgeService
import org.springframework.scheduling.annotation.Scheduled

class Purger(private val purgeService: PurgeService) {

    @Scheduled(cron ="5 4 * * *")
    fun purge() {
        val deletedProjects = purgeService.purgeUnusedProjects()
        println("$deletedProjects got purged.")
    }

}