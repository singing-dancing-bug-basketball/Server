package exam.server;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Question_ownershipDao extends JpaRepository<Question_ownership,QuestionOwnerMultiKeys>,JpaSpecificationExecutor {




}
