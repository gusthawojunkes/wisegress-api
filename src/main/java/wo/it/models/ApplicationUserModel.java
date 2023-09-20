package wo.it.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import wo.it.core.enums.Status;
import wo.it.database.entities.ApplicationUser;
import wo.it.database.entities.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApplicationUserModel extends AbstractModel {
    private String name;
    private String email;
    private String password;
    private LocalDate birthday;
    private Status status;
    private List<TodoModel> todos;

    public static ApplicationUserModel loadFrom(ApplicationUser user) {
        var model = new ApplicationUserModel();
        model.setName(user.getName());
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        model.setUuid(user.getUuid());
        model.setStatus(user.getStatus());
        model.setBirthday(user.getBirthday());
        model.setInsertedAt(user.getInsertedAt());
        model.setUpdatedAt(user.getUpdatedAt());

        List<TodoModel> todos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(user.getTodos())) {
            for (Todo todo : user.getTodos()) {
                todos.add(TodoModel.loadFrom(todo));
            }
        }

        model.setTodos(todos);

        return model;
    }
}
