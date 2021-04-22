package de.nykon.productivity.domain.value

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class CurrentDate {

    val localDate = LocalDate.now()!!

    fun toMySql(): String {
        val date = localDate
        val formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd")
        return formatter.format(date)
    }

}