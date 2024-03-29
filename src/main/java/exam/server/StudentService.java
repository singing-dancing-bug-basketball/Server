package exam.server;

import exam.server.StudentDao;
import exam.server.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    //分页查询
    public Page<Student> getPage(Integer pageNum, Integer pageLimit) {
        Pageable pageable = PageRequest.of(pageNum-1, pageLimit);
        return studentDao.findAll(pageable);
    }


}
