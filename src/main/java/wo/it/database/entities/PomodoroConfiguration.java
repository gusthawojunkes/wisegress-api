package wo.it.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import wo.it.models.PomodoroConfigurationModel;

@Getter
@Setter
@Entity
public class PomodoroConfiguration extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "preferences_uuid", referencedColumnName = "uuid")
    private Preferences preferences;

    @Column(
        nullable = false,
        columnDefinition = "int4 default 25"
    )
    private int duration;

    @Column(
        name = "shortbreak_duration",
        nullable = false,
        columnDefinition = "int4 default 5"
    )
    private int shortbreakDuration;

    @Column(
        name = "longbreak_duration",
        nullable = false,
        columnDefinition = "int4 default 15"
    )
    private int longbreakDuration;

    @Column(nullable = false, columnDefinition = "int4 default 3")
    private int cicles;

    public PomodoroConfiguration() {
        this.duration = 25;
        this.shortbreakDuration = 5;
        this.longbreakDuration = 15;
        this.cicles = 3;
    }

    public PomodoroConfigurationModel toModel() {
        var model = new PomodoroConfigurationModel();

        model.setUuid(this.getUuid());
        model.setInsertedAt(this.getInsertedAt());
        model.setUpdatedAt(this.getUpdatedAt());
        model.setDuration(this.getDuration());
        model.setShortbreakDuration(this.getShortbreakDuration());
        model.setLongbreakDuration(this.getLongbreakDuration());
        model.setCicles(this.getCicles());

        return model;
    }

}
