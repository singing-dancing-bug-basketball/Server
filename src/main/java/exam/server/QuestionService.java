package exam.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import exam.server.Question;
import exam.server.QuestionDao;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional

public class QuestionService {
    @Autowired
    private  QuestionDao questionDao;

    //增加一道题目
    public void addQustion(Question question){
        questionDao.save(question);
    }


    //根据id删除一条数据
    public void deleteQuestionById(Integer id){
        questionDao.deleteById(id);
    }

    //根据id查询一条数据
    public Question findQuestionById(Integer id){
        return questionDao.findById(id).get();
    }

    //查询所有
    public List<Question> findAll(){
        return questionDao.findAll();

    }
    public  Boolean existsByQustion_id(int question_id){
        for(Question a : questionDao.findAll()){

            if(a.getId()==question_id){
                return true;
            }

        }
        return false;
    }

    public int totalpages(){
       if(questionDao.findAll().size()%20 ==0){
           return questionDao.findAll().size()/20;
       }
       else {
           return 1+questionDao.findAll().size()/20;
       }
    }

    public Page<Question> getPage(Integer pageNum, Integer pageLimit) {
        Pageable pageable =PageRequest.of(pageNum-1, pageLimit);
        return questionDao.findAll(pageable);
    }





}
