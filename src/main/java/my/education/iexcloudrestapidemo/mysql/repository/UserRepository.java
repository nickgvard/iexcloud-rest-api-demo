package my.education.iexcloudrestapidemo.mysql.repository;

import my.education.iexcloudrestapidemo.mysql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional("mysqlTransactionManager")
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
}
