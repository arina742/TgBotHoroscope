package org.example.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class Zodiac {
    private String name;
    private String url;
    private String description;


    public Zodiac(String name, String description) {
        this.name = name;
        this.description = description;

    }

    @Override
    public String toString() {
        return name+" твоё предсказание на сегодня\n\n"+description;
    }
}
