package sbsamir.service.impl;

import antlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbsamir.model.Task;
import sbsamir.model.User;
import sbsamir.model.UserTask;
import sbsamir.payload.request.UserTaskRequest;
import sbsamir.payload.response.AssignResponse;
import sbsamir.payload.response.MessageResponse;
import sbsamir.payload.response.OwnerResponse;
import sbsamir.payload.response.UserTaskResponse;
import sbsamir.repository.TaskRepository;
import sbsamir.repository.UserRepository;
import sbsamir.repository.UserTaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserTaskService {

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private MessageResponse messageResponse = new MessageResponse();

    public MessageResponse findByAssignName(String username){
        List<AssignResponse> listUserTaskResponse = new ArrayList<>();
        if(Objects.isNull(username)){
            messageResponse.setMsg("please fill the field");
        }
        List<User> userAssignList = userRepository.findByAllUserAssign(username);
        if(userAssignList.size()==0){
            messageResponse.setMsg("user assign not found");
        }
        for(User userAssign: userAssignList){
            List<UserTask> userTaskAssignList = userTaskRepository.findUserTaskAssign(userAssign.getId());
            if(userTaskAssignList.size()==0){
                messageResponse.setMsg("user task assign not found");
            }
            for(UserTask userTaskAssign: userTaskAssignList){
                AssignResponse assignResponse = new AssignResponse();
                assignResponse.setId(userTaskAssign.getId());
                assignResponse.setTaskName(userTaskAssign.getTask().getTaskName());
                assignResponse.setAssign(userTaskAssign.getAssign().getUsername());
                listUserTaskResponse.add(assignResponse);
            }
        }
        messageResponse.setData(listUserTaskResponse);
        return messageResponse;
    }

    public MessageResponse findByOwnerName(String username){
        List<OwnerResponse> listUserTaskResponse = new ArrayList<>();
        if(Objects.isNull(username)){
            messageResponse.setMsg("please fill the field");
        }
        List<User> userOwnerList = userRepository.findByAllUserOwner(username);
        if(userOwnerList.size()==0){
            messageResponse.setMsg("user assign not found");
        }
        for(User userOwner: userOwnerList){
            List<UserTask> userTaskOwnerList = userTaskRepository.findUserTaskOwner(userOwner.getId());
            if(userTaskOwnerList.size()==0){
                messageResponse.setMsg("user task assign not found");
            }
            for(UserTask userTaskOwner: userTaskOwnerList){
                OwnerResponse ownerResponse = new OwnerResponse();
                ownerResponse.setId(userTaskOwner.getId());
                ownerResponse.setTaskName(userTaskOwner.getTask().getTaskName());
                ownerResponse.setOwner(userTaskOwner.getOwner().getUsername());
                listUserTaskResponse.add(ownerResponse);
            }
        }
        messageResponse.setData(listUserTaskResponse);
        return messageResponse;
    }

    public MessageResponse findUserByUserTaskId(long id){
        UserTaskResponse response = new UserTaskResponse();
if(Objects.isNull(id) || id<=0){
    messageResponse.setMsg("please fill the fields");
}
        if(Objects.nonNull(id)){
            UserTask cekUT =  userTaskRepository.findUserTaskByUserTaskId(id);
            if(Objects.isNull(cekUT)){
                messageResponse.setMsg("user task not found");
            }
            if(Objects.nonNull(cekUT)){
                Task cekTask = taskRepository.findByTaskId(cekUT.getTask().getId());
                User cekOwner = userRepository.findByUserOwner(cekUT.getOwner().getUsername());
                User cekAssign = userRepository.findByUserAssign(cekUT.getAssign().getUsername());

                response.setId(cekUT.getId());
                response.setTaskName(cekTask.getTaskName());
                response.setOwner(cekOwner.getUsername());
                response.setAssign(cekAssign.getUsername());
                messageResponse.setMsg("");
                messageResponse.setData(response);
            }


        }

        return messageResponse;
    }

    public MessageResponse listAllUserTask(){
        List<Object> listResponse = new ArrayList<>();
        List<UserTask> listUT =  userTaskRepository.viewAllUserTask();
        if(listUT.size()==0){
            messageResponse.setMsg("user task not found");
        }
        for (UserTask ut :
                listUT) {
            Task cekTask = taskRepository.findByTaskId(ut.getTask().getId());
            List<User> cekOwner = userRepository.findByAllUserOwner(ut.getOwner().getUsername());
            for(User o: cekOwner){
                OwnerResponse ownerResponse = new OwnerResponse();
                ownerResponse.setId(ut.getId());
                ownerResponse.setTaskName(cekTask.getTaskName());
                ownerResponse.setOwner(o.getUsername());
                listResponse.add(ownerResponse);

            }
            List<User> cekAssign = userRepository.findByAllUserAssign(ut.getAssign().getUsername());
            for(User a: cekAssign){
                AssignResponse assignResponse = new AssignResponse();
                assignResponse.setId(ut.getId());
                assignResponse.setTaskName(cekTask.getTaskName());
                assignResponse.setAssign(a.getUsername());
                listResponse.add(assignResponse);

            }
        }
        messageResponse.setMsg("");
        messageResponse.setData(listResponse);
        return messageResponse;
    }

    public MessageResponse insertUserTask(UserTaskRequest userTaskRequest){
        String result = null;
        int insertFlag=0;

        Task cekTaskAssigned = taskRepository.findTaskWhereStatusAssignedUsingTaskName(userTaskRequest.getTaskName());
        User cekOwner = userRepository.findByUserOwner(userTaskRequest.getOwnerName());
        User cekAssign = userRepository.findByUserAssign(userTaskRequest.getAssignName());

        if(Objects.isNull(userTaskRequest)){
            result="please fill the field";
            messageResponse.setMsg(result);
        }
        if(Objects.nonNull(cekTaskAssigned)){
            result="task assigned is exists";
            messageResponse.setMsg(result);
        }
        if(Objects.isNull(cekOwner)){
            result="owner not found";
            messageResponse.setMsg(result);
        }
        if(Objects.isNull(cekAssign)){
            result="assign not found";
            messageResponse.setMsg(result);
        }

        if(cekTaskAssigned==null && cekOwner!=null && cekAssign!=null && userTaskRequest!=null){
            Task cekTaskNotAssign = taskRepository.findTaskWhereStatusNotAssignUsingTaskName(userTaskRequest.getTaskName());
            if(Objects.nonNull(cekTaskNotAssign)){
                result="task not assign not found";
                messageResponse.setMsg(result);
            }
            if(Objects.nonNull(cekTaskNotAssign)){
                insertFlag = userTaskRepository.insertUserTask(cekTaskNotAssign.getId(),cekAssign.getId(),cekOwner.getId());
                if(insertFlag==0){
                    result="failed insert user task";
                    messageResponse.setMsg(result);
                }
                taskRepository.updateTaskToAssignedTask(cekTaskNotAssign.getId());
                result="success insert user task";
                messageResponse.setMsg(result);
            }

        }
        return messageResponse;
    }

    public MessageResponse updateUserTask(UserTask userTask){
        String result = null;
        int updateFlag=0;

        Task cekTask = taskRepository.findByTaskId(userTask.getTask().getId());
        User cekOwner = userRepository.findByUserOwner(userTask.getOwner().getUsername());
        User cekAssign = userRepository.findByUserAssign(userTask.getAssign().getUsername());
        UserTask cekUserTask = userTaskRepository.findById(userTask.getId()).get();

        if(cekTask==null || cekOwner==null || cekAssign==null || userTask==null || cekUserTask==null){
            result="field must be filled";
            messageResponse.setMsg(result);
        }

        if(cekTask!=null && cekOwner!=null && cekAssign!=null && userTask!=null && cekUserTask!=null){
            updateFlag = userTaskRepository.updateUserTask(cekTask.getId(),cekAssign.getId(),cekOwner.getId(), cekUserTask.getId());
            if(updateFlag==0){
                result="failed update user task";
                messageResponse.setMsg(result);
            }
                result="success update user task";
                messageResponse.setMsg(result);

        }
        return messageResponse;
    }

    public MessageResponse listEvenUserTask(){
        List<UserTaskResponse> listResponse = new ArrayList<>();
        List<UserTask> listUT = userTaskRepository.listEvenUserTask();
        if(listUT.size()==0){
            messageResponse.setMsg("user task not found");
        }
        for (UserTask ut:
             listUT) {
            UserTaskResponse response = new UserTaskResponse();
            response.setId(ut.getId());
            response.setTaskName(ut.getTask().getTaskName());
            response.setOwner(ut.getOwner().getUsername());
            response.setAssign(ut.getAssign().getUsername());
            listResponse.add(response);
        }
        messageResponse.setMsg("");
        messageResponse.setData(listResponse);
        return messageResponse;
    }
}
