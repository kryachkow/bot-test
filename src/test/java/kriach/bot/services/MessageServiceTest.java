package kriach.bot.services;

import kriach.bot.entities.Degree;
import kriach.bot.entities.DegreeName;
import kriach.bot.entities.Department;
import kriach.bot.entities.Lector;
import kriach.bot.repositories.DegreeRepository;
import kriach.bot.repositories.DepartmentRepository;
import kriach.bot.repositories.LectorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class MessageServiceTest {

    @Autowired
    MessageService messageService;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    LectorRepository lectorRepository;
    @Autowired
    DegreeRepository degreeRepository;

    @BeforeAll
    void setUp() {
        Degree assistant = new Degree(null, DegreeName.ASSISTANT);
        Degree associateProfessor = new Degree(null, DegreeName.ASSOCIATE_PROFESSOR);
        Degree professor = new Degree(null, DegreeName.PROFESSOR);

        degreeRepository.saveAll(Arrays.asList(assistant, associateProfessor, professor));

        Lector john = new Lector(null, "John", "Doe", 1000, assistant, null);
        Lector jane = new Lector(null, "Jane", "Smith", 2000, associateProfessor, null);
        Lector robert = new Lector(null, "Robert", "Johnson", 3000, professor, null);
        Lector michael = new Lector(null, "Michael", "Williams", 1500, assistant, null);
        Lector sarah = new Lector(null, "Sarah", "Jones", 2500, associateProfessor, null);
        Lector jessica = new Lector(null, "Jessica", "Brown", 3600, professor, null);

        lectorRepository.saveAll(Arrays.asList(john, jane, robert, michael, sarah, jessica));

        Department math = new Department(null, "Mathematics", robert, null);
        Department physics = new Department(null, "Physics", john, null);
        Department chemistry = new Department(null, "Chemistry", jane, null);

        departmentRepository.saveAll(Arrays.asList(math, physics, chemistry));

        john.setDepartments(Arrays.asList(math, physics));
        jane.setDepartments(List.of(chemistry));
        robert.setDepartments(Arrays.asList(math, physics, chemistry));
        michael.setDepartments(Arrays.asList(math, chemistry));
        sarah.setDepartments(Arrays.asList(chemistry, physics));
        jessica.setDepartments(Arrays.asList(math, physics, chemistry));

        lectorRepository.saveAll(Arrays.asList(john, jane, robert, michael, sarah, jessica));
    }

    @DisplayName("MessageService answerToInput test")
    @ParameterizedTest(name = "{index} => input={0}, expected={1}")
    @MethodSource("messageServiceTestArguments")
    void testAnswerToInput(String input, String expected) {
        assertEquals(expected, messageService.answerToInput(input));
    }


    private static Stream<Arguments> messageServiceTestArguments() {
        return Stream.of(
                Arguments.of("hElp", """
                        Available commands are:
                        1) Who is head of department {department_name}
                        2) Show {department_name} statistics
                        3) Show the average salary for the department {department_name}
                        4) Show count of employee for {department_name}
                        5) Global search by {template}
                        6) Help
                        7) That's all, thank you!
                        """),
                Arguments.of("Unknown command", "Sorry, there is no such command, use command 'HELP' to see available commands."),
                Arguments.of("Who is head of department Mathematics", "Head of 'Mathematics' department is Robert Johnson"),
                Arguments.of("who is Head oF departMent Physics", "Head of 'Physics' department is John Doe"),
                Arguments.of("Who is head of department absentDepartment", "Department by name 'absentDepartment' is not found"),
                Arguments.of("Show Mathematics statistics", """
                        Assistants - 2
                        Associate professors - 0
                        Professors - 2
                        """),
                Arguments.of("Show chemistry statistics", """
                        Assistants - 1
                        Associate professors - 2
                        Professors - 2
                        """),
                Arguments.of("Show count of employee for unknownDepartment", "Department by name 'unknownDepartment' is not found"),
                Arguments.of("Show the average salary for the department Physics", "The average salary of 'Physics' department is 2525.00"),
                Arguments.of("Show the average salary for the department Chemistry", "The average salary of 'Chemistry' department is 2520.00"),
                Arguments.of("Show count of employee for Mathematics", "There are 4 employees in 'Mathematics' department"),
                Arguments.of("Show count of employee for Chemistry", "There are 5 employees in 'Chemistry' department"),
                Arguments.of("Global search by nothing", "Global search results: "),
                Arguments.of("Global search by oh", "Global search results: John Doe, Robert Johnson"),
                Arguments.of("That's all, thank you!", "Thank you for using this bot, have a nice day!")
        );
    }
}