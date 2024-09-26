package kriach.bot.services;

import kriach.bot.entities.Lector;
import kriach.bot.repositories.LectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectorService {

    private final LectorRepository lectorRepository;


    public List<Lector> getLectorsByTemplate(String template) {
        return lectorRepository.getLectorsByTemplate(template);
    }
}
