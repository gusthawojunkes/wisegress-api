package wo.it.core.enums;

import lombok.Getter;

@Getter
public enum Feature {

    TODO("Todo"),
    POMODORO("Pomodoro"),
    KANBAN("Kanban"),
    LIST("Lista");

    private final String description;

    Feature(String description) {
        this.description = description;
    }

    public static Feature getByDigit(Short number) {
        return switch (number) {
            case 0 -> TODO;
            case 1 -> POMODORO;
            case 2 -> KANBAN;
            case 3 -> LIST;
            default -> throw new IllegalStateException("Unexpected value: " + number);
        };
    }

}
