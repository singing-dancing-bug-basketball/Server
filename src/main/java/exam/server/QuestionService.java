package exam.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import exam.server.Question;
import exam.server.QuestionDao;
import java.util.List;

@Service
@Transactional

public class QuestionService {
    @Autowired
    private  QuestionDao QuestionDao;

    //增加一道题目
    public void addQustion(Question question){
        QuestionDao.save(question);
    }

    //修改一个学生(jpa是根据id来修改的)
    public void  updateStudent(Question question){
        QuestionDao.save(question);
    }

    //根据id删除一条数据
    public void deleteQuestionById(Integer id){
        QuestionDao.deleteById(id);
    }

    //根据id查询一条数据
    public Question findStudentById(Integer id){
        return QuestionDao.findById(id).get();
    }

    //查询所有
    public List<Question> findAll(){
        return QuestionDao.findAll();
    }


}
