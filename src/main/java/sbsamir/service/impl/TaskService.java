package sbsamir.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbsamir.model.Task;
import sbsamir.payload.request.TaskRequest;
import sbsamir.payload.response.MessageResponse;
import sbsamir.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    private MessageResponse messageResponse = new MessageResponse();

    public MessageResponse viewAllTask() {

        List<Task> taskList = taskRepository.viewAllTask();
        if (taskList.size() == 0) {
            messageResponse.setMsg("task data not found");
        }
        messageResponse.setData(taskList);
        return messageResponse;
    }

    public MessageResponse viewAllNotAssign() {
        List<Task> taskList = taskRepository.viewAllNotAssign();
        if (taskList.size() == 0) {
            messageResponse.setMsg("task data not found");
        }
        messageResponse.setMsg("");
        messageResponse.setData(taskList);
        return messageResponse;
    }

    public MessageResponse viewAllAssigned() {
        List<Task> taskList = taskRepository.viewAllAssigned();
        if (taskList.size() == 0) {
            messageResponse.setMsg("task data not found");
        }
        messageResponse.setMsg("");
        messageResponse.setData(taskList);
        return messageResponse;
    }

    public MessageResponse viewAllOnProgressTask() {

        List<Task> taskList = taskRepository.viewAllOnProgressTask();
        if (taskList.size() == 0) {
            messageResponse.setMsg("task data not found");
        }
        messageResponse.setMsg("");
        messageResponse.setData(taskList);
        return messageResponse;
    }

    public MessageResponse viewAllCompletedTask() {
        List<Task> taskList = taskRepository.viewAllCompletedTask();
        if (taskList.size() == 0) {
            messageResponse.setMsg("task data not found");
        }
        messageResponse.setMsg("");
        messageResponse.setData(taskList);
        return messageResponse;
    }

    public MessageResponse viewAllNoTaskBetween(String taskName, Date startDate, Date endDate) {
        List<Task> taskList = taskRepository.viewAllNoTaskBetween(taskName, startDate, endDate);
        if (taskList.size() == 0) {
            messageResponse.setMsg("task data not found");
        }
        messageResponse.setData(taskList);
        return messageResponse;
    }

    public MessageResponse viewAllOnProgressTaskBetween(String taskName, Date startDate, Date endDate) {
        List<Task> taskList = taskRepository.viewAllOnProgressTaskBetween(taskName, startDate, endDate);
        if (taskList.size() == 0) {
            messageResponse.setMsg("task data not found");
        }
        messageResponse.setData(taskList);
        return messageResponse;
    }

    public MessageResponse viewAllCompletedTaskBetween(String taskName, Date startDate, Date endDate) {
        List<Task> taskList = taskRepository.viewAllCompletedTaskBetween(taskName, startDate, endDate);
        if (taskList.size() == 0) {
            messageResponse.setMsg("task data not found");
        }
        messageResponse.setData(taskList);
        return messageResponse;
    }

    public MessageResponse findTaskWhereStatusNotAssignUsingTaskName(String taskName) {
        if(Objects.isNull(taskName)){
            messageResponse.setMsg("please fill the fields");
        }
        Task cekTask = taskRepository.findTaskWhereStatusNotAssignUsingTaskName(taskName);
        if(Objects.isNull(cekTask)){
            messageResponse.setMsg("task not found");
        }
        if(Objects.nonNull(cekTask)){
            messageResponse.setMsg("");
            messageResponse.setData(cekTask);
        }
        return messageResponse;
    }

    public MessageResponse findTaskWhereStatusOnProgressTaskUsingTaskName(String taskName) {
        if(Objects.isNull(taskName)){
            messageResponse.setMsg("please fill the field");
        }
        Task cekTask = taskRepository.findTaskWhereStatusOnProgressTaskUsingTaskName(taskName);
        if(Objects.isNull(cekTask)){
            messageResponse.setMsg("task not found");
        }
        if(Objects.nonNull(cekTask)){
            messageResponse.setMsg("");
            messageResponse.setData(cekTask);
        }
        return messageResponse;
    }

    public MessageResponse findTaskWhereStatusCompletedTaskUsingTaskName(String taskName) {
        if(Objects.isNull(taskName)){
            messageResponse.setMsg("please fill the field");
        }
        Task cekTask = taskRepository.findTaskWhereStatusCompletedTaskUsingTaskName(taskName);
        if(Objects.isNull(cekTask)){
            messageResponse.setMsg("task not found");
        }
        if(Objects.nonNull(cekTask)){
            messageResponse.setMsg("");
            messageResponse.setData(cekTask);
        }
        return messageResponse;
    }

    public MessageResponse findTaskWhereStatusNotAssignUsingId(long id) {
        if (Objects.isNull(id)) {
            messageResponse.setMsg("please fill the field");
        }
        Task cekTask = taskRepository.findTaskWhereStatusNotAssignUsingId(id);
        if(Objects.isNull(cekTask)){
            messageResponse.setMsg("task not found");
        }
        if(Objects.nonNull(cekTask)){
            messageResponse.setMsg("");
            messageResponse.setData(cekTask);
        }
        return messageResponse;
    }

    public MessageResponse findTaskWhereStatusAssignedUsingId(long id) {
        if (Objects.isNull(id)) {
            messageResponse.setMsg("please fill the field");
        }
        Task cekTask = taskRepository.findTaskWhereStatusAssignedUsingId(id);
        if(Objects.isNull(cekTask)){
            messageResponse.setMsg("task not found");
        }
        if(Objects.nonNull(cekTask)){
            messageResponse.setMsg("");
            messageResponse.setData(cekTask);
        }
        return messageResponse;
    }

    public MessageResponse findTaskWhereStatusOnProgressTaskUsingId(long id) {
        if (Objects.isNull(id)) {
            messageResponse.setMsg("please fill the field");
        }
        Task cekTask = taskRepository.findTaskWhereStatusOnProgressTaskUsingId(id);
        if(Objects.isNull(cekTask)){
            messageResponse.setMsg("task not found");
        }
        else if(Objects.nonNull(cekTask)){
            messageResponse.setMsg("");
            messageResponse.setData(cekTask);
        }
        return messageResponse;
    }

    public MessageResponse findTaskWhereStatusCompletedTaskUsingId(long id) {
        if (Objects.isNull(id)) {
            messageResponse.setMsg("please fill the field");
        }
        Task cekTask = taskRepository.findTaskWhereStatusCompletedTaskUsingId(id);
        if(Objects.isNull(cekTask)){
            messageResponse.setMsg("task not found");
        }
        else if(Objects.nonNull(cekTask)){
            messageResponse.setMsg("");
            messageResponse.setData(cekTask);
        }
        return messageResponse;
    }

    public MessageResponse insertTask(TaskRequest inputTask) {
        String result = null;
        int flagInsert = 0;
        if (inputTask == null) {
            result = "please fill the field";
            messageResponse.setMsg(result);
        }
        if (inputTask != null) {
            Task cekNotAssignTask = taskRepository.findTaskWhereStatusNotAssignUsingTaskName(inputTask.getTaskName());
            Task cekAssignedTask = taskRepository.findTaskWhereStatusAssignedUsingTaskName(inputTask.getTaskName());
            Task cekOnProgressTask = taskRepository.findTaskWhereStatusOnProgressTaskUsingTaskName(inputTask.getTaskName());
            Task cekCompletedTask = taskRepository.findTaskWhereStatusCompletedTaskUsingTaskName(inputTask.getTaskName());
            if (cekNotAssignTask != null || cekOnProgressTask != null || cekCompletedTask != null || cekAssignedTask!=null) {
                result = "task already exists";
                messageResponse.setMsg(result);
            }

            if (inputTask != null && cekNotAssignTask == null && cekOnProgressTask == null && cekCompletedTask == null && cekAssignedTask==null) {

                flagInsert = taskRepository.insertTask(
                        inputTask.getTaskName(),
                        inputTask.getTaskDescription(),
                        new Date(System.currentTimeMillis()),
                        "not assign");

                if (flagInsert == 0) {
                    result = "insert task failed";
                    messageResponse.setMsg(result);
                }
                    result = "insert task success";
                    messageResponse.setMsg(result);

            }
        }
        return messageResponse;
    }

    public MessageResponse updateTask(Task updateTask) {
        String result = null;
        int flagUpdate = 0;
        if (updateTask == null) {
            result = "please fill the field";
            messageResponse.setMsg(result);
        }
        if (updateTask != null) {
            Task cekNotAssignTask = taskRepository.findTaskWhereStatusNotAssignUsingId(updateTask.getId());
            Task cekAssignedTask = taskRepository.findTaskWhereStatusAssignedUsingTaskName(updateTask.getTaskName());
            Task cekOnProgressTask = taskRepository.findTaskWhereStatusOnProgressTaskUsingId(updateTask.getId());
            Task cekCompletedTask = taskRepository.findTaskWhereStatusCompletedTaskUsingId(updateTask.getId());
            if (cekNotAssignTask == null || cekOnProgressTask == null || cekCompletedTask == null || cekAssignedTask==null) {
                result = "task not found";
                messageResponse.setMsg(result);
            }

            if (updateTask != null && cekNotAssignTask != null && cekOnProgressTask != null && cekCompletedTask != null && cekAssignedTask!=null) {
                flagUpdate = taskRepository.updateTask(
                        updateTask.getId(),
                        updateTask.getTaskName(),
                        updateTask.getTaskDescription());

                if (flagUpdate == 0) {
                    result = "update task failed";
                    messageResponse.setMsg(result);
                }
                    result = "update task success";
                    messageResponse.setMsg(result);

            }

        }
        return messageResponse;
    }



    public MessageResponse updateTaskToOnProgressTask(long id) {

        String result = null;
        int flagUpdate = 0;
        if (Objects.isNull(id)) {
            result = "please fill the field";
            messageResponse.setMsg(result);
        }
        else if (Objects.nonNull(id)) {
            Task cekOnProgressTask = taskRepository.findTaskWhereStatusOnProgressTaskUsingId(id);
            if (cekOnProgressTask != null) {
                result = "task on progress already exists";
                messageResponse.setMsg(result);
            }

            if (Objects.nonNull(id) &&  Objects.isNull(cekOnProgressTask) ) {

                flagUpdate = taskRepository.updateTaskToOnProgressTask(id);
                if (flagUpdate == 0) {
                    result = "failed update to on progress";
                    messageResponse.setMsg(result);
                }
                result = "success update to on progress";
                messageResponse.setMsg(result);
                messageResponse.setData(null);
            }

        }
        return messageResponse;
    }

    public MessageResponse updateTaskToCompletedTask(long id) {
        String result = null;
        int flagUpdate = 0;
        if (Objects.isNull(id)) {
            result = "please fill the field";
            messageResponse.setMsg(result);
        }
         if (Objects.nonNull(id)) {
            Task cekCompletedTask = taskRepository.findTaskWhereStatusCompletedTaskUsingId(id);
            if ( cekCompletedTask != null) {
                result = "task completed already exists";
                messageResponse.setMsg(result);
            }
            if (Objects.nonNull(id) && Objects.isNull(cekCompletedTask)) {

                flagUpdate = taskRepository.updateTaskToCompletedTask(id);
                if (flagUpdate == 0) {
                    result = "failed update to completed";
                    messageResponse.setMsg(result);
                }

                result = "status update to completed";
                messageResponse.setMsg(result);
                messageResponse.setData(null);
            }
        }
            return messageResponse;
        }

        public MessageResponse listEvenTask () {
            List<Task> taskList = taskRepository.listEvenTask();
            if (taskList.size() == 0) {
                messageResponse.setMsg("task even not found");
            }
            messageResponse.setMsg("");
            messageResponse.setData(taskList);
            return messageResponse;
        }


}
