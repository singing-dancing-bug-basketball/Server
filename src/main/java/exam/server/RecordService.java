package exam.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional

public class RecordService {

    @Autowired
    private RecordDao recordDao;

    //增加一个学生答题记录
    public void addRecord(Record record){
        recordDao.save(record);
    }
    //根据id删除一个记录
    public void deleteRecordById(RecordMultiKeys recordMultiKeys){
        recordDao.deleteById(recordMultiKeys);
    }

    //根据id查询一条数据
    public Record findRecordById(RecordMultiKeys recordMultiKeys){
        return  recordDao.findById(recordMultiKeys).get();
    }





}
