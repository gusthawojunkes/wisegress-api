package wo.it.core.enums;

import org.apache.commons.lang3.StringUtils;

public enum Rating {

    EXCELLENT("Excelente"),
    VERY_GOOD("Muito bom"),
    GOOD ("Bom"),
    FAIR("Razoável"),
    POOR("Ruim");

    private final String description;

    Rating(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description != null ? description : StringUtils.EMPTY;
    }

}
