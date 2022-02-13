package my.education.iexcloudrestapidemo.mysql.repository;

import my.education.iexcloudrestapidemo.mysql.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("mysqlTransactionManager")
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(String name);
}
