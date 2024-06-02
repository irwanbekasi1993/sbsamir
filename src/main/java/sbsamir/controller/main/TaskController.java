package sbsamir.controller.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sbsamir.model.Task;
import sbsamir.payload.request.TaskRequest;
import sbsamir.payload.response.MessageResponse;
import sbsamir.service.impl.TaskService;

import java.util.List;

@RestController
@RequestMapping("/sbsamir/test/v1")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/task",method = RequestMethod.POST)
    public ResponseEntity<MessageResponse> insertTask(@RequestBody TaskRequest task){
        return ResponseEntity.ok(taskService.insertTask(task));
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/task",method = RequestMethod.PUT)
    public ResponseEntity<MessageResponse> updateTask(@RequestBody Task task){
        return ResponseEntity.ok(taskService.updateTask(task));
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/task",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> viewAllTask(){
        return ResponseEntity.ok(taskService.viewAllTask());
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/task/notAssign",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> viewAllTaskNotAssign(){
        return ResponseEntity.ok(taskService.viewAllNotAssign());
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/task/assigned",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> viewAllTaskAssigned(){
        return ResponseEntity.ok(taskService.viewAllAssigned());
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/task/onProgress",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> viewAllTaskOnProgress(){
        return ResponseEntity.ok(taskService.viewAllOnProgressTask());
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/task/completed",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> viewAllTaskCompleted(){
        return ResponseEntity.ok(taskService.viewAllCompletedTask());
    }


    @PreAuthorize("hasRole('ROLE_ASSIGN')")
    @RequestMapping(value = "/task/onProgress/{id}",method = RequestMethod.PUT)
    public ResponseEntity<MessageResponse> updateToOnProgress(@PathVariable("id")Long id){
        return ResponseEntity.ok(taskService.updateTaskToOnProgressTask(id));
    }

    @PreAuthorize("hasRole('ROLE_ASSIGN')")
    @RequestMapping(value = "/task/completed/{id}",method = RequestMethod.PUT)
    public ResponseEntity<MessageResponse> updateToCompleted(@PathVariable("id")Long id){
        return ResponseEntity.ok(taskService.updateTaskToCompletedTask(id));
    }

    @PreAuthorize("hasRole('ROLE_OWNER')")
    @RequestMapping(value = "/task/notAssign/{id}",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> getNotAssignTaskById(@PathVariable("id")long id){
        return ResponseEntity.ok(taskService.findTaskWhereStatusNotAssignUsingId(id));
    }

    @RequestMapping(value = "/task/assigned/{id}",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> getAssignedTaskById(@PathVariable("id")long id){
        return ResponseEntity.ok(taskService.findTaskWhereStatusAssignedUsingId(id));
    }

    @RequestMapping(value = "/task/onProgress/{id}",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> getOnProgressById(@PathVariable("id")Long id){
        return ResponseEntity.ok(taskService.findTaskWhereStatusOnProgressTaskUsingId(id));
    }

    @RequestMapping(value = "/task/completed/{id}",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> getCompletedById(@PathVariable("id")Long id){
        return ResponseEntity.ok(taskService.findTaskWhereStatusCompletedTaskUsingId(id));
    }

    @RequestMapping(value = "/task/even",method = RequestMethod.GET)
    public ResponseEntity<MessageResponse> listEven(){
        return ResponseEntity.ok(taskService.listEvenTask());
    }

}
