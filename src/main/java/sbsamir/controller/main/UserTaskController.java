package sbsamir.controller.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbsamir.model.UserTask;
import sbsamir.payload.request.UserTaskRequest;
import sbsamir.payload.response.MessageResponse;
import sbsamir.payload.response.UserTaskResponse;
import sbsamir.service.impl.UserTaskService;

import java.util.List;

@RestController
@RequestMapping("/sbsamir/test/v1")
public class UserTaskController {

    @Autowired
    private UserTaskService userTaskService;

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/userTask",method = RequestMethod.POST)
    public ResponseEntity<MessageResponse> insertUserTask(@RequestBody UserTaskRequest userTaskRequest){
        return ResponseEntity.ok(userTaskService.insertUserTask(userTaskRequest));
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/userTask",method = RequestMethod.PUT)
    public ResponseEntity<MessageResponse> updateUserTask(@RequestBody UserTask userTask){
        return ResponseEntity.ok(userTaskService.updateUserTask(userTask));
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/userTask/owner/{ownerName}",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> findUserTaskByOwnerName(@PathVariable("ownerName")String ownerName){
        return ResponseEntity.ok(userTaskService.findByOwnerName(ownerName));
    }

    @PreAuthorize("hasRole('ROLE_ASSIGN')")
    @RequestMapping(value = "/userTask/assign/{assignName}",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> findUserTaskByAssignName(@PathVariable("assignName")String assignName){
        return ResponseEntity.ok(userTaskService.findByAssignName(assignName));
    }

    @RequestMapping(value = "/userTask/{id}",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> findUserTaskById(@PathVariable("id")long id){
        return ResponseEntity.ok(userTaskService.findUserByUserTaskId(id));
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/userTask",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> listAllUserTask(){
        return ResponseEntity.ok(userTaskService.listAllUserTask());
    }

    @RequestMapping(value = "/userTask/even",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> listEvenUserTask(){
        return ResponseEntity.ok(userTaskService.listEvenUserTask());
    }
}
