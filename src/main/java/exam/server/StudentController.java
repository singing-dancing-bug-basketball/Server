package exam.server;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import exam.server.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TestService testService;
    @Autowired
    private Question_ownershipService question_ownershipService;



    @RequestMapping("/student")
    public String index_(){
        return "login";
    }

    @PostMapping(value = "student/login",consumes = "application/json;charset=UTF-8;")
    @ResponseBody
    public JSONObject login(@RequestBody JSONObject jsonObject){

        JSONObject re = new JSONObject();
        re.put("cookie",200);
        if(studentService.findStudentById(jsonObject.get("id").toString()).getPassword().equals(jsonObject.get("password"))){
            re.put("status",200);
            return re;
        }
        return re;
    }


    @RequestMapping(value = "/student/test/",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject gettest(@RequestBody JSONObject jsonObject){

        String student_id = jsonObject.get("id").toString();
        int test_id = Integer.valueOf(jsonObject.get("test_id").toString());
        JSONObject re = new JSONObject();
        re.put("cookie",200);
        re.put("title",testService.findTestById(test_id).getTitle());
        re.put("duration",testService.findTestById(test_id).getTest_paper().getDuration());
        re.put("start_time",testService.findTestById(test_id).getStart_time());
        re.put("end_time",testService.findTestById(test_id).getEnd_time());

        List<Question_ownership> list = question_ownershipService.findAll();
        List<Question_ownership> questionlist = new ArrayList<>();
        for(int i =0;i<list.size();i++){
            if(list.get(i).getQuestionOwnerMultiKeys().getTest_paper_id()==test_id){
                questionlist.add(list.get(i));
            }
        }


              return re;





    }


    class studentTestdetail{
        int question_id;
        String stem;
        String selections;
        int answer;
        int score;

        public studentTestdetail(int question_id,String stem,int answer,int score,String selections){
            this.answer = answer;
            this.question_id =question_id;
            this.score = score;
            this.selections = selections;
            this.stem = stem;
        }

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public int getQuestion_id() {
            return question_id;
        }

        public String getSelections() {
            return selections;
        }

        public String getStem() {
            return stem;
        }

        public void setSelections(String selections) {
            this.selections = selections;
        }

        public void setStem(String stem) {
            this.stem = stem;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getAnswer() {
            return answer;
        }

        public void setAnswer(int answer) {
            this.answer = answer;
        }

        public int getScore() {
            return score;
        }
    }


}
