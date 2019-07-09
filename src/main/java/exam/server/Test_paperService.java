package exam.server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Test_paperService {
    @Autowired
    private Test_paperDao test_paperDao;

    //增加一个测试卷
    public void addTest_paper(Test_paper test_paper){
        test_paperDao.save(test_paper);
    }

    //根据id删除一个卷子
    public void deleteTest_paperById(int id){
        test_paperDao.deleteById(id);
    }

    //根据id查询一条数据
    public Test_paper findTest_paperById(int id){
        return test_paperDao.findById(id).get();
    }

    //查询所有
    public List<Test_paper> findAll(){
        return test_paperDao.findAll();
    }



}
