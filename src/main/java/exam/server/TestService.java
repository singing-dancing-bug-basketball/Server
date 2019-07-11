package exam.server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    //获取测试列表
    public Page<Test> getPage(Integer pageNum, Integer pageLimit) {
        Pageable pageable = PageRequest.of(pageNum-1, pageLimit);
        return testDao.findAll(pageable);
    }


    public Test curentTest(){
        Date nowdate = new Date();
        for(Test a : testDao.findAll()){
            if(a.getEnd_time().compareTo(nowdate)<0){
                return a;
            }
        }
        return null;
    }

    public List<Integer> getTestidList(){
        List<Integer> list = new ArrayList<>();
        for(Test a : testDao.findAll()){
            list.add(a.getId());
        }
        return list;
    }


}
