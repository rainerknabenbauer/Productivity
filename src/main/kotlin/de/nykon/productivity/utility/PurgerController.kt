package de.nykon.productivity.utility

import de.nykon.productivity.PurgeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PurgeController(private val purgeService: PurgeService) {

    @GetMapping(path = ["/purge"])
    fun purge(): Int {
        return purgeService.purgeUnusedProjects()
    }
}