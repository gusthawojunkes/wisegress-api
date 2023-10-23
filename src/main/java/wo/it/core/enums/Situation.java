package wo.it.core.enums;

import org.apache.commons.lang3.StringUtils;

public enum Situation {

    PENDING("Pendente"),
    IN_PROGRESS("Em progresso"),
    DONE("Feito");

    private final String description;

    Situation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description != null ? description : StringUtils.EMPTY;
    }

}
