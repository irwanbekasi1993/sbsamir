package sbsamir.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sbsamir.model.Role;
import sbsamir.payload.request.UserRoleView;

import javax.transaction.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(String name);

    @Query(value = "select r.name from role r join user_role ur on r.id=ur.role_id join users u on u.id=ur.user_id where u.username=:username",nativeQuery = true)
    String getRoleName(@Param("username")String username);
}
