package exam.server;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordDao extends JpaRepository<Record,RecordMultiKeys> {

}
