package ru.job4j.trackingsystem.lombok;

import lombok.Builder;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Builder(builderMethodName = "of")
@ToString
public class Permission {
    private int id;
    private String name;

    @Singular("rules")
    private List<String> rules;
}
