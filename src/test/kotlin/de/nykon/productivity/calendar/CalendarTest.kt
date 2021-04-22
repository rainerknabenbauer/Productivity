package de.nykon.productivity.calendar

import de.nykon.productivity.domain.value.Task
import de.nykon.productivity.domain.value.TaskDescription
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.apache.coyote.http11.Constants.a




@SpringBootTest
internal class CalendarTest(
    @Autowired private val calendar: Calendar
) {

    @Test
    fun `create a single component calendar`() {
        // arrange
        val tasks = ArrayList<Task>()
        tasks.add(Task(
            projectId = "bamboozled",
            title = "Hello moto",
            description = TaskDescription("You better", "read a book", null)
        ))

        // act
        val icsCalendar = calendar.build(tasks)

        println(icsCalendar)

        // assert
        Assertions.assertTrue(icsCalendar.contains("https://productivity.to/?id=bamboozled"))
        Assertions.assertTrue(icsCalendar.contains("SUMMARY:Hello moto"))
        Assertions.assertTrue(icsCalendar.contains("DESCRIPTION:You better | read a book | "))
        Assertions.assertTrue(icsCalendar.contains("DTSTART;VALUE=DATE:"))  // 20100101

        val date = icsCalendar
            .split(System.getProperty("line.separator"))[4]
            .split(":")[1]

        Assertions.assertTrue(date.length == 8)
        Assertions.assertTrue(date[0].toString() == "2")
    }

    @Test
    fun `create a multi component calendar`() {
        // arrange
        val tasks = ArrayList<Task>()
        tasks.add(Task(
            projectId = "bamboozled",
            title = "Hello moto",
            description = TaskDescription("You better", "read a book", null)
        ))
        tasks.add(Task(
            projectId = "anotherone",
            title = "Roboto",
            description = TaskDescription("I build", "things", "recursively")
        ))

        // act
        val icsCalendar = calendar.build(tasks)

        print(icsCalendar)

        // assert
        Assertions.assertTrue(icsCalendar.contains("https://productivity.to/?id=bamboozled"))
        Assertions.assertTrue(icsCalendar.contains("SUMMARY:Hello moto"))
        Assertions.assertTrue(icsCalendar.contains("DESCRIPTION:You better | read a book | "))

        Assertions.assertTrue(icsCalendar.contains("https://productivity.to/?id=anotherone"))
        Assertions.assertTrue(icsCalendar.contains("SUMMARY:Roboto"))
        Assertions.assertTrue(icsCalendar.contains("DESCRIPTION:I build | things | recursively"))
    }


}