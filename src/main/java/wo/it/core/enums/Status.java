package wo.it.core.enums;

public enum Status {

    ACTIVE ("Ativo"),
    INACTIVE("Inativo"),
    BLOCKED("Bloqueado");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
