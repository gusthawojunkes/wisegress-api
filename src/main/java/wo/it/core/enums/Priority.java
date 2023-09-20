package wo.it.core.enums;

import org.apache.commons.lang3.StringUtils;

public enum Priority {

    HIGHEST("Muito alta"),
    HIGH("Alta"),
    MEDIUM("Média"),
    LOW("Baixa"),
    LOWEST("Muito baixa");

    private final String description;

    Priority(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description != null ? description : StringUtils.EMPTY;
    }

}
