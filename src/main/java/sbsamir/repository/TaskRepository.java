package sbsamir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import sbsamir.model.Task;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query(value = "select * from task where id=:id",nativeQuery = true)
    Task findByTaskId(@Param("id")long id);

    @Transactional
    @Modifying
    @Query(value = "insert into task(task_name," +
            "task_description,work_date,status) " +
            "values(:taskName,:taskDescription,:workDate,:status)",nativeQuery = true)
    int insertTask(
                       @RequestParam("taskName")String taskName,
                       @RequestParam("taskDescription")String taskDescription,
                       @RequestParam("workDate")Date workDate,
                       @RequestParam("status")String status);

    @Transactional
    @Modifying
    @Query(value = "update task set " +
            "task_name=:taskName," +
            "task_description=:taskDescription"+
            "where task_code=:taskCode",nativeQuery = true)
    int updateTask(
            @RequestParam("taskCode") Long taskCode,
            @RequestParam("taskName")String taskName,
            @RequestParam("taskDescription")String taskDescription
    );

    @Transactional
    @Modifying
    @Query(value = "update task set status='assigned',work_date=current_date" +
            " where id=:id and status='not assign'",nativeQuery = true)
    int updateTaskToAssignedTask(@RequestParam("id")Long id);

    @Transactional
    @Modifying
    @Query(value = "update task set status='on progress',work_date=current_date" +
            " where id=:id and status='assigned'",nativeQuery = true)
    int updateTaskToOnProgressTask(@RequestParam("id")Long id);

    @Transactional
    @Modifying
    @Query(value = "update task set status='completed',work_date=current_date" +
            " where id=:id and status='on progress'",nativeQuery = true)
    int updateTaskToCompletedTask(@RequestParam("id")Long id);

    @Query(value = "select * from task " +
            "where task_name=:taskName and status='not assign'",nativeQuery = true)
    Task findTaskWhereStatusNotAssignUsingTaskName(@RequestParam("taskName")String taskName);

    @Query(value = "select * from task " +
            "where task_name=:taskName and status='assigned'",nativeQuery = true)
    Task findTaskWhereStatusAssignedUsingTaskName(@RequestParam("taskName")String taskName);


    @Query(value = "select * from task" +
            " where task_name=:taskName and status='on progress'",nativeQuery = true)
    Task findTaskWhereStatusOnProgressTaskUsingTaskName(@RequestParam("taskName")String taskName);

    @Query(value = "select * from task " +
            "where task_name=:taskName and status='completed'",nativeQuery = true)
    Task findTaskWhereStatusCompletedTaskUsingTaskName(@RequestParam("taskName")String taskName);

    @Query(value = "select * from task " +
            "where id=:id and status='not assign'",nativeQuery = true)
    Task findTaskWhereStatusNotAssignUsingId(@RequestParam("id")Long id);

    @Query(value = "select * from task " +
            "where id=:id and status='assigned'",nativeQuery = true)
    Task findTaskWhereStatusAssignedUsingId(@RequestParam("id")Long id);

    @Query(value = "select * from task" +
            " where id=:id and status='on progress'",nativeQuery = true)
    Task findTaskWhereStatusOnProgressTaskUsingId(@RequestParam("id")Long id);

    @Query(value = "select * from task " +
            "where id=:id and status='completed'",nativeQuery = true)
    Task findTaskWhereStatusCompletedTaskUsingId(@RequestParam("id")Long id);


    @Query(value = "select * from task ",nativeQuery = true)
    List<Task> viewAllTask();

    @Query(value = "select * from task where status='not assign'",nativeQuery = true)
    List<Task> viewAllNotAssign();

    @Query(value = "select * from task where status='assigned'",nativeQuery = true)
    List<Task> viewAllAssigned();

    @Query(value = "select * from task where status='on progress'",nativeQuery = true)
    List<Task> viewAllOnProgressTask();

    @Query(value = "select * from task where status='completed'",nativeQuery = true)
    List<Task> viewAllCompletedTask();

    @Query(value = "select * from task where task_name=:taskName and status='no task' and workDate between :startDate and :endDate",nativeQuery = true)
    List<Task> viewAllNoTaskBetween(@RequestParam("taskName")String taskName, @RequestParam("startDate")Date startDate, @RequestParam("endDate")Date endDate);

    @Query(value = "select * from task where task_name=:taskName and status='on progress' and workDate between :startDate and :endDate",nativeQuery = true)
    List<Task> viewAllOnProgressTaskBetween(@RequestParam("taskName")String taskName, @RequestParam("startDate")Date startDate, @RequestParam("endDate")Date endDate);

    @Query(value = "select * from task where task_name= :taskName and status='completed' and workDate between :startDate and :endDate",nativeQuery = true)
    List<Task> viewAllCompletedTaskBetween(@RequestParam("taskName")String taskName, @RequestParam("startDate")Date startDate, @RequestParam("endDate")Date endDate);

    @Query(value = "select * from task where id%2=0",nativeQuery = true)
    List<Task> listEvenTask();
}
