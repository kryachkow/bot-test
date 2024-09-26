package kriach.bot.services;

import kriach.bot.entities.DegreeName;
import kriach.bot.utils.LectorUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static kriach.bot.utils.DefaultMessages.COMMAND_NOT_FOUND;
import static kriach.bot.utils.DefaultMessages.HELP;
import static kriach.bot.utils.DefaultMessages.HELP_RESPONSE;
import static kriach.bot.utils.DefaultMessages.THAT_IS_ALL;
import static kriach.bot.utils.DefaultMessages.THAT_IS_ALL_RESPONSE;

@Service
public class MessageService {

    private static final String HEAD_OF_DEPARTMENT_FORMAT = "Head of '%s' department is %s";
    private static final String STATISTICS_FORMAT = """
            Assistants - %d
            Associate professors - %d
            Professors - %d
            """;
    private static final String AVERAGE_SALARY_FORMAT = "The average salary of '%s' department is %.2f";
    private static final String EMPLOYEE_COUNT_FORMAT = "There are %d employees in '%s' department";
    private static final String GLOBAL_SEARCH_FORMAT = "Global search results: %s";
    private static final String DEPARTMENT_NOT_FOUND_FORMAT = "Department by name '%s' is not found";

    private final DepartmentService departmentService;
    private final LectorService lectorService;
    private final Map<Pattern, Function<Matcher, String>> patternHandlers;


    public MessageService(DepartmentService departmentService, LectorService lectorService) {
        this.departmentService = departmentService;
        this.lectorService = lectorService;
        patternHandlers = new HashMap<>();
        patternHandlers.put(Pattern.compile(HELP, Pattern.CASE_INSENSITIVE), this::handleHelp);
        patternHandlers.put(Pattern.compile("Who is head of department (.+)", Pattern.CASE_INSENSITIVE),
                this::handleHeadOfDepartment);
        patternHandlers.put(Pattern.compile("Show (.+) statistics", Pattern.CASE_INSENSITIVE),
                this::handleStatistics);
        patternHandlers.put(Pattern.compile("Show the average salary for the department (.+)", Pattern.CASE_INSENSITIVE),
                this::handleAverageSalary);
        patternHandlers.put(Pattern.compile("Show count of employee for (.+)", Pattern.CASE_INSENSITIVE),
                this::handleEmployeeCount);
        patternHandlers.put(Pattern.compile("Global search by (.+)", Pattern.CASE_INSENSITIVE),
                this::handleGlobalSearch);
        patternHandlers.put(Pattern.compile(THAT_IS_ALL, Pattern.CASE_INSENSITIVE), this::handleThatsAll);
    }

    public String answerToInput(String input) {
        for (Map.Entry<Pattern, Function<Matcher, String>> pattern : patternHandlers.entrySet()) {
            Matcher matcher = pattern.getKey().matcher(input.trim());
            if (matcher.matches()) {
                return pattern.getValue().apply(matcher);
            }
        }
        return COMMAND_NOT_FOUND;
    }

    private String handleHelp(Matcher matcher) {
        return HELP_RESPONSE;
    }

    private String handleThatsAll(Matcher matcher) {
        return THAT_IS_ALL_RESPONSE;
    }

    private String handleHeadOfDepartment(Matcher matcher) {
        String departmentName = matcher.group(1).trim();
        return departmentService.getDepartmentByNameIgnoreCase(departmentName)
                .map(department -> String.format(HEAD_OF_DEPARTMENT_FORMAT, department.getName(), LectorUtils.getFormatedLectorName(department.getHead())))
                .orElse(departmentNotFound(departmentName));
    }

    private String handleStatistics(Matcher matcher) {
        String departmentName = matcher.group(1).trim();
        return departmentService.getDepartmentByNameIgnoreCase(departmentName)
                .map(departmentService::countEachDegreeInDepartment)
                .map(map -> String.format(STATISTICS_FORMAT,
                        map.getOrDefault(DegreeName.ASSISTANT, 0L),
                        map.getOrDefault(DegreeName.ASSOCIATE_PROFESSOR, 0L),
                        map.getOrDefault(DegreeName.PROFESSOR, 0L)))
                .orElse(departmentNotFound(departmentName));
    }

    private String handleAverageSalary(Matcher matcher) {
        String departmentName = matcher.group(1).trim();
        return departmentService.getDepartmentByNameIgnoreCase(departmentName)
                .map(departmentService::calculateAverageSalary)
                .map(optionalDouble -> optionalDouble.orElse(0))
                .map(avg -> String.format(AVERAGE_SALARY_FORMAT, departmentName, avg))
                .orElse(departmentNotFound(departmentName));
    }

    private String handleEmployeeCount(Matcher matcher) {
        String departmentName = matcher.group(1).trim();
        return departmentService.getDepartmentByNameIgnoreCase(departmentName)
                .map(department -> String.format(EMPLOYEE_COUNT_FORMAT, department.getLectors().size(), departmentName))
                .orElse(departmentNotFound(departmentName));
    }

    private String handleGlobalSearch(Matcher matcher) {
        return String.format(GLOBAL_SEARCH_FORMAT,
                lectorService.getLectorsByTemplate(matcher.group(1).trim()).stream()
                        .map(LectorUtils::getFormatedLectorName)
                        .collect(Collectors.joining(", "))
        );
    }

    private String departmentNotFound(String absentDepartmentName) {
        return String.format(DEPARTMENT_NOT_FOUND_FORMAT, absentDepartmentName);
    }

}
