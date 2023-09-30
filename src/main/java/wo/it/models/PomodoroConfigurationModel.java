package wo.it.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PomodoroConfigurationModel extends AbstractModel {

    private Integer duration;
    private Integer shortbreakDuration;
    private Integer longbreakDuration;
    private int cicles;

}
