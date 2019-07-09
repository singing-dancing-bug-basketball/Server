package exam.server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional

public class TestService {
    @Autowired
    private TestDao testDao;

    //增加一个测试
    public void addTest(Test test){
        testDao.save(test);
    }

    //根据id删除一次测试
    public void deleteTestById(int id){
        testDao.deleteById(id);
    }

    //根据id查询一条数据
    public Test findTestById(int id){
        return testDao.findById(id).get();
    }

    //查询所有
    public List<Test> findAll(){
        return testDao.findAll();
    }


}
