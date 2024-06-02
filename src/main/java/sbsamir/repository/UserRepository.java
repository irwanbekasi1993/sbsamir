package sbsamir.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sbsamir.model.Role;
import sbsamir.model.User;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query(value = "select u.* from users u " +
            "join user_role ur on u.id=ur.user_id " +
            "join role r on r.id=ur.role_id " +
            "where r.name='ROLE_ASSIGN' and u.username=:username",nativeQuery = true)
    User findByUserAssign(@Param("username")String username);

    @Query(value = "select u.* from users u " +
            "join user_role ur on u.id=ur.user_id " +
            "join role r on r.id=ur.role_id " +
            "where r.name='ROLE_OWNER' and u.username=:username",nativeQuery = true)
    User findByUserOwner(@Param("username")String username);

    @Query(value = "select u.* from users u " +
            "join user_role ur on u.id=ur.user_id " +
            "join role r on r.id=ur.role_id " +
            "where r.name='ROLE_ASSIGN' and u.username=:username",nativeQuery = true)
    List<User> findByAllUserAssign(@Param("username")String username);

    @Query(value = "select u.* from users u " +
            "join user_role ur on u.id=ur.user_id " +
            "join role r on r.id=ur.role_id " +
            "where r.name='ROLE_OWNER' and u.username=:username",nativeQuery = true)
    List<User> findByAllUserOwner(@Param("username")String username);


    @Modifying
    @Transactional
    @Query(value = "update users set password=:newPassword where username=:username ",nativeQuery = true)
    void resetPassword(@Param("newPassword") String newPassword, @Param("username") String username);
}
