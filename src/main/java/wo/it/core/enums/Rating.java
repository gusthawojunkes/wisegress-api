package wo.it.core.enums;

import org.apache.commons.lang3.StringUtils;

public enum Rating {

    POOR("Ruim"),
    FAIR("Razoável"),
    GOOD ("Bom"),

    VERY_GOOD("Muito bom"),
    EXCELLENT("Excelente");

    private final String description;

    Rating(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description != null ? description : StringUtils.EMPTY;
    }

}
