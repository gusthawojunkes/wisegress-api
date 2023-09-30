package wo.it.models;

import lombok.Setter;

@Setter
public class PreferencesModel extends AbstractModel {

    private PomodoroConfigurationModel pomodoroConfiguration;

    private boolean useKanban;

    private boolean usePomodoro;

    private boolean useAgenda;

    private boolean useTodo;

    public boolean useKanban() {
        return useKanban;
    }

    public boolean usePomodoro() {
        return usePomodoro;
    }

    public boolean useAgenda() {
        return useAgenda;
    }

    public boolean useTodo() {
        return useTodo;
    }

}
