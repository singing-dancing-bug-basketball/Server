package exam.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional

public class RecordService {

    @Autowired
    private RecordDao recordDao;
    @Autowired
    private Question_ownershipService question_ownershipService;

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

    public List<Record> findAll (){
        return recordDao.findAll();
    }

    //获取参加某次测试 test_id 的人
    public List<String> testStudents(int test_id){

        ArrayList<String> list = new ArrayList<String>();
        for(Record a : recordDao.findAll()) {
            if(a.getRecordMultiKeys().getTest_id()==test_id&&!list.contains(a.getRecordMultiKeys().getStudent_id())){
                 list.add(a.getRecordMultiKeys().getStudent_id());
            }
        }
        return list;
    }

    // 参加某次测试的学生得分
    public int testStudentScore(int test_id,String student_id){

        int sum = 0;

        for(Record a : recordDao.findAll()) {

         if(a.getRecordMultiKeys().getStudent_id().equals(student_id)&&a.getRecordMultiKeys().getTest_id()==test_id){

            if(a.getSelection_id()==a.getQuestion().getSelection_id()){

                QuestionOwnerMultiKeys  questionOwnerMultiKeys = new QuestionOwnerMultiKeys();

                questionOwnerMultiKeys.setQuestion_id(a.getRecordMultiKeys().getQuestion_id());
                questionOwnerMultiKeys.setTest_paper_id(a.getTest().getTest_paper().getId());

                sum = sum + question_ownershipService.findQuestion_ownershipById(questionOwnerMultiKeys).getScore();
            }
         }
        }
        return sum;
    }



    public Boolean existStuTest(String student_id,int test_id){

        for(Record a : recordDao.findAll()){
            if(a.getRecordMultiKeys().getStudent_id().equals(student_id)&&a.getRecordMultiKeys().getTest_id()==test_id){
                return true;
            }
        }
       return false;
    }






}
