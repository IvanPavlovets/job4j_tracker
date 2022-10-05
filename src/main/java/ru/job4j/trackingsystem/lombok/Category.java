package ru.job4j.trackingsystem.lombok;

import lombok.*;

@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    @Getter
    @EqualsAndHashCode.Include
    @NonNull
    private int id;
    @Getter
    @Setter
    private String name;
}
