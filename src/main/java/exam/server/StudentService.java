package exam.server;

import exam.server.StudentDao;
import exam.server.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {
    @Autowired
    private  StudentDao studentDao;

    //增加一个学生
    public void addStudent(Student student){
        studentDao.save(student);
    }
    //根据id删除一个学生
    public void deleteStudentById(String id){
        studentDao.deleteById(id);
    }

    //根据id查询一条数据
    public Student findStudentById(String id){
        return studentDao.findById(id).get();
    }

    //查询所有
    public List<Student> findAll(){
        return studentDao.findAll();
    }



}
