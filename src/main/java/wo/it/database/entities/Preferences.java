package wo.it.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import wo.it.models.PreferencesModel;

@Getter
@Setter
@Entity
public class Preferences extends AbstractEntity {

    @OneToOne(mappedBy = "preferences", optional = false)
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private ApplicationUser user;

    @OneToOne(mappedBy = "preferences", optional = false)
    private PomodoroConfiguration pomodoroConfiguration;

    @Column(
        name = "use_kanban",
        nullable = false,
        columnDefinition = "boolean default true"
    )
    private boolean useKanban;

    @Column(
        name = "use_pomodoro",
        nullable = false,
        columnDefinition = "boolean default true"
    )
    private boolean usePomodoro;

    @Column(
        name = "use_agenda",
        nullable = false,
        columnDefinition = "boolean default true"
    )
    private boolean useAgenda;

    @Column(
        name = "use_todo",
        nullable = false,
        columnDefinition = "boolean default true"
    )
    private boolean useTodo;

    public PreferencesModel toModel() {
        var model = new PreferencesModel();
        model.setUuid(this.uuid);
        model.setInsertedAt(this.insertedAt);
        model.setUpdatedAt(this.updatedAt);
        model.setUseKanban(this.useKanban);
        model.setUsePomodoro(this.usePomodoro);
        model.setUseAgenda(this.useAgenda);
        model.setUseTodo(this.useTodo);
        model.setPomodoroConfiguration(this.pomodoroConfiguration.toModel());
        return model;
    }

}
