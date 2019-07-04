package exam.server;

import exam.server.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface QuestionDao extends JpaRepository<Question,Integer> {

}
