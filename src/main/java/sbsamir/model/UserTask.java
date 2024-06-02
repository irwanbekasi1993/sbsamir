package sbsamir.model;

import javax.persistence.*;

@Entity
@Table(name="user_task")
public class UserTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="taskCode")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="assign")
    private User assign;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="owner")
    private User owner;

    public User getAssign() {
        return assign;
    }

    public void setAssign(User assign) {
        this.assign = assign;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
