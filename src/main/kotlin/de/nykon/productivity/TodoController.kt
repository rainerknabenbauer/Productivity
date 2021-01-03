package de.nykon.productivity

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController {

    @GetMapping(path = ["/mocks"])
    fun getMocks(): ResponseEntity<List<Mock>> {

        println("called getMock")

        val mocks = ArrayList<Mock>()
        mocks.add(Mock("001", "Mock title", "Description"))
        mocks.add(Mock("002", "Mocking2", "Description2"))
        mocks.add(Mock("003", "Mocking3", "Description3"))

        return ResponseEntity.ok(mocks);
    }

    @GetMapping(path = ["/mock"])
    fun getMock() = ResponseEntity.ok(Mock("001", "Mock title", "Description"))

    @GetMapping(path = ["", "/"])
    fun getDefault() : String {
        Thread.sleep(1000)
        return "Demo string"
    }
}