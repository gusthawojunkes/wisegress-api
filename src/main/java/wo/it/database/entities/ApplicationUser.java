package wo.it.database.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import wo.it.models.ApplicationUserModel;
import wo.it.core.enums.Status;
import wo.it.models.TodoModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ApplicationUser extends AbstractEntity {

    @Column(length = 60, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDate birthday;

    private Status status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Todo> todos;

    public boolean isBlocked() {
        return this.status == Status.BLOCKED;
    }

    public ApplicationUserModel toModel() {
        var model = new ApplicationUserModel();
        List<TodoModel> todos = new ArrayList<>();

        model.setName(this.getName());
        model.setEmail(this.getEmail());
        model.setPassword(this.getPassword());
        model.setBirthday(this.getBirthday());
        model.setUuid(this.getUuid());
        model.setStatus(this.getStatus());
        model.setInsertedAt(this.getInsertedAt());
        model.setUpdatedAt(this.getUpdatedAt());

        if (CollectionUtils.isNotEmpty(this.getTodos())) {
            for (Todo todo : this.getTodos()) {
                todos.add(TodoModel.loadFrom(todo));
            }
        }

        model.setTodos(todos);

        return model;
    }

    public List<Todo> getTodos() {
        return this.todos == null ? new ArrayList<>() : this.todos;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s)", this.getStatus().getDescription(), this.getName(), this.getEmail());
    }
}
