package kriach.bot.entities;

import lombok.Getter;

@Getter
public enum DegreeName {
    ASSISTANT("assistant"), ASSOCIATE_PROFESSOR("Associate professor"), PROFESSOR("Professor");


    private final String prettyName;

    DegreeName(String prettyName) {
        this.prettyName = prettyName;
    }
}
