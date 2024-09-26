package kriach.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Scanner;

import static kriach.bot.utils.DefaultMessages.THAT_IS_ALL;
import static kriach.bot.utils.DefaultMessages.WELCOME_MESSAGE;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.console.runner.enabled", havingValue = "true", matchIfMissing = true)
public class ConsoleService implements CommandLineRunner {

    private final MessageService messageService;

    @Override
    public void run(String... args) {
        log.info("Initializing console work");
        System.out.println(WELCOME_MESSAGE);
        String input;
        Scanner scanner = new Scanner(System.in);
        do {
            input = scanner.nextLine();
            System.out.println(messageService.answerToInput(input));
        } while (!input.equalsIgnoreCase(THAT_IS_ALL));
        log.info("Shutting down console work");
    }

}
