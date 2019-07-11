package exam.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecordDao extends JpaRepository<Record,RecordMultiKeys> {

}
