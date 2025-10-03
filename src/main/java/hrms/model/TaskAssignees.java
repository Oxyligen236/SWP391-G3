
package hrms.model;

import java.util.List;

public class TaskAssignees {
    private int TaskId;
    private List<Integer> receiverId;

    public TaskAssignees() {
    }

    public TaskAssignees(int TaskId, List<Integer> receiverId) {
        this.TaskId = TaskId;
        this.receiverId = receiverId;
    }

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int TaskId) {
        this.TaskId = TaskId;
    }

    public List<Integer> getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(List<Integer> receiverId) {
        this.receiverId = receiverId;
    }

    
    
    
}
