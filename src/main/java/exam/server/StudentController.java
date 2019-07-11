package exam.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import exam.server.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private Question_ownershipService question_ownershipService;
    @Autowired
    private Test_paperService test_paperService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private TestService  testService;
    @Autowired
    private StudentService studentService;



    @RequestMapping("/student")
    public String index_(){
        return "login";
    }




    @PostMapping(value = "student/login",consumes = "application/json;charset=UTF-8;")
    @ResponseBody
    public JSONObject login(@RequestBody JSONObject jsonObject, HttpServletResponse response){
        JSONObject re = new JSONObject();

        if(studentService.findStudentById(jsonObject.get("id").toString()).getPassword().equals(jsonObject.get("password"))){
            Cookie cookie=new Cookie("cookie","CookieTestInfo");
            response.addCookie(cookie);
            re.put("status",200);
            return re;
        }
        return re;
    }



    //获取某一次测试
    @RequestMapping(value = "/student/test/{test_id}")
    @ResponseBody
    public JSONObject getTest(@PathVariable("test_id") int  test_id,@CookieValue("cookie") String user_name,HttpServletResponse response) throws IOException {

        if(!user_name.equals("CookieTestInfo")){
            response.sendRedirect("/teacher/login/");
        }

        JSONObject re = new JSONObject();

        re.put("title",testService.findTestById(test_id).getTitle());
        re.put("duration",testService.findTestById(test_id).getTest_paper().getDuration());
        re.put("start_time",testService.findTestById(test_id).getStart_time());
        re.put("end_time",testService.findTestById(test_id).getEnd_time());

        List<Integer> qlist = question_ownershipService.getTestquestion(test_id);
        List<JSONObject> jsonList = new ArrayList<JSONObject>();

        for(Integer a : qlist){
            JSONObject reNei = new JSONObject();
            reNei.put("question_id",a);
            reNei.put("stem",questionService.findQuestionById(a).getStem());

            String[] b = questionService.findQuestionById(a).getContent().split("@caixukun@");
            List<String> list = Arrays.asList(b);
            reNei.put("selections",list);
            reNei.put("answer",questionService.findQuestionById(a).getSelection_id());
            QuestionOwnerMultiKeys questionOwnerMultiKeys = new QuestionOwnerMultiKeys();

            questionOwnerMultiKeys.setTest_paper_id(testService.findTestById(test_id).getTest_paper().getId());
            questionOwnerMultiKeys.setQuestion_id(a);
            reNei.put("selections",list);
            reNei.put("score",question_ownershipService.findQuestion_ownershipById(questionOwnerMultiKeys).getScore());
            jsonList.add(reNei);
        }

        re.put("questions",jsonList);
        return re;
    }



    //获取学生的测试列表
    @RequestMapping(value = "/student/test/list/{id}")
    @ResponseBody
    public JSONObject getTestlist(@PathVariable("id") String student_id,@CookieValue("cookie") String user_name,HttpServletResponse response) throws IOException {

        if(!user_name.equals("CookieTestInfo")){
            response.sendRedirect("/teacher/login/");
        }

        List<JSONObject> jlist = new ArrayList<>();
        List<Integer> testList = testService.getTestidList();


        for(int i =0;i<testList.size();i++){

            if(!recordService.existStuTest(student_id,testList.get(i))){

                JSONObject reNei = new JSONObject();
                reNei.put("test_id",testService.findTestById(testList.get(i)).getId());
                reNei.put("title",testService.findTestById(testList.get(i)).getTitle());
                reNei.put("total_score",question_ownershipService.totalscorebyid(testService.findTestById(testList.get(i)).getTest_paper().getId()));
                reNei.put("num_questions",question_ownershipService.getTestquestion(testService.findTestById(testList.get(i)).getTest_paper().getId()).size());
                reNei.put("duration",testService.findTestById(testList.get(i)).getTest_paper().getDuration());
                reNei.put("start_time",testService.findTestById(testList.get(i)).getStart_time());
                reNei.put("end_time",testService.findTestById(testList.get(i)).getEnd_time());
                jlist.add(reNei);
            }
        }
        JSONObject re = new JSONObject();
        re.put("tests",jlist);

        return re;
    }




    //获取某次测试结果
    @RequestMapping(value = "/student/record/{student_id}/{test_id}")
    @ResponseBody
    public JSONObject getTestStu(@PathVariable("student_id") String student_id,@PathVariable("test_id") int test_id,@CookieValue("cookie") String user_name,HttpServletResponse response) throws IOException {

        if(!user_name.equals("CookieTestInfo")){
            response.sendRedirect("/teacher/login/");
        }

        JSONObject re = new JSONObject();
        List<Integer> qlist = question_ownershipService.getTestquestion(testService.findTestById(test_id).getTest_paper().getId());
        List<JSONObject> jList = new ArrayList<>();
        for(Integer a : qlist){
            JSONObject reNei = new JSONObject();
            reNei.put("question_id",a);
            reNei.put("stem",questionService.findQuestionById(a).getStem());
            String[] b = questionService.findQuestionById(a).getContent().split("@caixukun@");
            List<String> list = Arrays.asList(b);
            reNei.put("selections",list);
            RecordMultiKeys recordMultiKeys = new RecordMultiKeys();
            recordMultiKeys.setQuestion_id(a);
            recordMultiKeys.setTest_id(test_id);
            recordMultiKeys.setStudent_id(student_id);

            reNei.put("selection",recordService.findRecordById(recordMultiKeys).getSelection_id());
            reNei.put("answer",questionService.findQuestionById(a).getSelection_id());

            QuestionOwnerMultiKeys questionOwnerMultiKeys = new QuestionOwnerMultiKeys();
            questionOwnerMultiKeys.setTest_paper_id(test_id);
            questionOwnerMultiKeys.setQuestion_id(a);
            reNei.put("score",question_ownershipService.findQuestion_ownershipById(questionOwnerMultiKeys).getScore());
            jList.add(reNei);
        }
        re.put("title",testService.findTestById(test_id).getTitle());
        re.put("duration",testService.findTestById(test_id).getTest_paper().getDuration());
        re.put("start_time",testService.findTestById(test_id).getStart_time());
        re.put("end_time",testService.findTestById(test_id).getEnd_time());
        re.put("questions",jList);
        return re;
    }





    //学生提交测试结果
    @RequestMapping(value = "student/record/",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject postTestStu(@RequestBody JSONObject jsonObject,@CookieValue("cookie") String user_name,HttpServletResponse response) throws IOException {

        if(!user_name.equals("CookieTestInfo")){
            response.sendRedirect("/teacher/login/");
        }


        JSONObject re = new JSONObject();
        String student_id = jsonObject.get("student_id").toString();
        int test_id = Integer.valueOf(jsonObject.get("test_id").toString());



        JSONArray jlist = jsonObject.getJSONArray("selections");

        for(int i =0;i<jlist.size();i++){
            JSONObject j = jlist.getJSONObject(i);
            RecordMultiKeys recordMultiKeys = new RecordMultiKeys();
            recordMultiKeys.setStudent_id(student_id);
            recordMultiKeys.setTest_id(test_id);
            recordMultiKeys.setQuestion_id(Integer.valueOf(j.get("question_id").toString()));
            Record record = new Record();
            record.setRecordMultiKeys(recordMultiKeys);
            record.setSelection_id(Integer.valueOf(j.get("selection").toString()));
            recordService.addRecord(record);
        }
        re.put("status",200);
        return re;

    }





    //:获取测试结果列表
    @RequestMapping(value = "/student/record/list/{student_id}")
    @ResponseBody
    public JSONObject getNnTestlist(@PathVariable("student_id") String student_id,@CookieValue("cookie") String user_name,HttpServletResponse response) throws IOException {

        if(!user_name.equals("CookieTestInfo")){
            response.sendRedirect("/teacher/login/");
        }

        JSONObject re = new JSONObject();

        List<Integer> list = testService.getTestidList();
        List<JSONObject> jlist = new ArrayList<JSONObject>();
        for (Integer a : list) {
            if (recordService.existStuTest(student_id, a)) {

                JSONObject reNei = new JSONObject();
                reNei.put("test_id", a);
                reNei.put("title", testService.findTestById(a).getTitle());
                reNei.put("start_time", testService.findTestById(a).getStart_time());
                reNei.put("end_time", testService.findTestById(a).getEnd_time());
                reNei.put("duration", testService.findTestById(a).getTest_paper().getDuration());
                jlist.add(reNei);
            }
        }
        re.put("tests", jlist);
        return re;
    }


}
