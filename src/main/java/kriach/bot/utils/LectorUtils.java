package kriach.bot.utils;

import kriach.bot.entities.Lector;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LectorUtils {

    private static final String LECTOR_NAME_FORMAT = "%s %s";

    public static String getFormatedLectorName(Lector lector) {
        return String.format(LECTOR_NAME_FORMAT, lector.getName(), lector.getSurname());
    }
}
