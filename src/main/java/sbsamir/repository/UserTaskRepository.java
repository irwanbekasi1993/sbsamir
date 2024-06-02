package sbsamir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sbsamir.model.UserTask;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask,Long> {

    @Query(value="select ut.* from user_task ut " +
            "where ut.id=:id",nativeQuery = true)
    UserTask findUserTaskByUserTaskId(@Param("id")long id);

    @Query(value="select * from user_task where owner=:ownerId",nativeQuery = true)
    List<UserTask> findUserTaskOwner(@Param("ownerId")long ownerId);

    @Query(value="select * from user_task where assign=:assignId",nativeQuery = true)
    List<UserTask> findUserTaskAssign(@Param("assignId")long assignId);

    @Query(value="select ut.* from user_task ut ",nativeQuery = true)
    List<UserTask> viewAllUserTask();

    @Transactional
    @Modifying
    @Query(value = "insert into user_task(task_code,assign,owner) " +
            "values(:taskCode,:assign,:owner)",nativeQuery = true)
    int insertUserTask(@Param("taskCode")long taskCode,
                       @Param("assign")long assign,
                       @Param("owner")long owner);

    @Transactional
    @Modifying
    @Query(value = "update user_task set task_code=:taskCode,assign=:assign,owner=:owner  " +
            "where id=:id",nativeQuery = true)
    int updateUserTask(@Param("id")long taskCode,
                       @Param("assign")long assign,
                       @Param("owner")long owner,
                       @Param("id")long id);

    @Query(value="select * from user_task where id%2=0",nativeQuery = true)
    List<UserTask> listEvenUserTask();
}
