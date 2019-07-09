package exam.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.awt.*;

@Service
@Transactional
public class Question_ownershipService {
    @Autowired
    private Question_ownershipDao question_ownershipDao;

    //增加一个试题试卷记录
    public void addQuestion_ownership(Question_ownership question_ownership){
        question_ownershipDao.save(question_ownership);
    }
    //根据id删除一个记录
    public void deleteQuestion_ownershipById(QuestionOwnerMultiKeys questionOwnerMultiKeys){
        question_ownershipDao.deleteById(questionOwnerMultiKeys);
    }
    //根据id查询一条数据
    public Question_ownership findQuestion_ownershipById(QuestionOwnerMultiKeys questionOwnerMultiKeys){
        return question_ownershipDao.findById(questionOwnerMultiKeys).get();
    }


    public List<Question_ownership> findAll (){
        return question_ownershipDao.findAll();
    }

    //求一张卷子的总分
    public long totalscorebyid(int test_paper_id){

        List<Question_ownership>  a = question_ownershipDao.findAll();

        long sum = 0;

        for(Question_ownership b:a){
            if(b.getQuestionOwnerMultiKeys().getTest_paper_id()==test_paper_id) {
                sum = sum + b.getScore();
            }
        }
        return sum;
    }

}
