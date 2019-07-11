package exam.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;


import java.io.IOException;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TeacherController {
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


    @RequestMapping(value = "/teacher",method = RequestMethod.GET)
    public String index_(){
        return "login";
    }

    //登陆
    @RequestMapping(value = "teacher/login",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(@RequestBody JSONObject jsonObject, HttpServletResponse response) throws ParseException {

        JSONObject re = new JSONObject();

        if(jsonObject.get("id").equals("123456")&&jsonObject.get("password").equals("123456")){

            Cookie cookie=new Cookie("123456","123456");
            response.addCookie(cookie);

            re.put("status",200);
            return re;
        }
        re.put("status",404);
        return re;
    }

    //获取某个问题
    @RequestMapping(value="/teacher/student/list/{question_id}")
    public String getQuestion (@PathVariable("question_id") Integer question_id,Model model, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
      Question a = questionService.findQuestionById(question_id);
      model.addAttribute("stem",a.getStem());
      String[] b = a.getContent().split("@caixunkun@");
      List<String> list = Arrays.asList(b);
      model.addAttribute("selections",list);
      model.addAttribute("answer",a.getSelection_id());
      model.addAttribute("question_id",question_id);
      return "question";

    }

    //添加新的问题
    @RequestMapping(value = "/teacher/question/",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addQuestion(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        Question newquestion = new Question();
        newquestion.setStem(jsonObject.get("stem").toString());
        newquestion.setSelection_id(Integer.valueOf(jsonObject.get("answer").toString()));
        List<String> list = (List)jsonObject.get("selections");
        String content = "";
        for(int i =0;i<list.size()-1;i++){
            content = content + list.get(i) + "@caixukun@";
        }
        content = content + list.get(list.size()-1);
        newquestion.setContent(content);
        questionService.addQustion(newquestion);
        JSONObject re = new JSONObject();
        re.put("question_id",newquestion.getId());
        return re;
    }

    //删除问题
    @RequestMapping(value = "/teacher/question/",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject deleteQuestion(@RequestBody JSONObject jsonObject) throws IOException {

        JSONObject re = new JSONObject();
        if(questionService.existsByQustion_id(Integer.valueOf(jsonObject.get("question_id").toString()))){
            questionService.deleteQuestionById(Integer.valueOf(jsonObject.get("question_id").toString()));
            re.put("status",200);
            return  re;
        }
        re.put("status",404);
        return re;


    }

    //修改问题
    @RequestMapping(value = "/teacher/question/",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateQuestion(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();
        if(questionService.existsByQustion_id(Integer.valueOf(jsonObject.get("question_id").toString()))){
            Question newquestion = new Question();
            newquestion.setStem(jsonObject.get("stem").toString());
            newquestion.setSelection_id(Integer.valueOf(jsonObject.get("answer").toString()));
            newquestion.setId(Integer.valueOf(jsonObject.get("question_id").toString()));

            List<String> list = (List)jsonObject.get("selections");

            String content = "";
            for(int i =0;i<list.size()-1;i++){
                content = content + list.get(i) + "@caixukun@";
            }
            content = content+ list.get(list.size()-1);
            newquestion.setContent(content);
            questionService.addQustion(newquestion);
            re.put("status",200);
            return re;

        }
        re.put("status",404);
        return re;
    }

    //获取问题列表
    @RequestMapping(value = "/teacher/question/list/{page}")
    public String getQuestionlist(@PathVariable("page") Integer pagenum ,Model model, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        Page<Question> page = questionService.getPage(pagenum,20);
        List<Questionslei> pageq = new ArrayList<Questionslei>();

        for(int i =0;i<page.getContent().size();i++){
            String[] a = page.getContent().get(i).getContent().split("@caixukun@");
            List<String> b = Arrays.asList(a);
            Questionslei c = new Questionslei(page.getContent().get(i).getId(), page.getContent().get(i).getStem(),b,page.getContent().get(i).getSelection_id());
            pageq.add(c);
        }
        model.addAttribute("page",pagenum);
        model.addAttribute("total_page",page.getTotalPages());
        model.addAttribute("questions",pageq);
        return "questions";
    }

    class Questionslei{

        int question_id;
        String stem;
        List<String> selections;
        int answer;
        int score;

        public Questionslei(int question_id,String stem,List<String> selections,int answer){

            this.question_id =question_id;
            this.stem = stem;
            this.selections = selections;
            this.answer = answer;

        }
        public Questionslei(int question_id,String stem,List<String> selections,int answer,int score){

            this.question_id =question_id;
            this.stem = stem;
            this.selections = selections;
            this.answer = answer;
            this.score = score;

        }

        public void output(){
            System.out.println("question_id:"+question_id+",stem:"+stem+",answer:"+answer);
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public void setStem(String stem) {
            this.stem = stem;
        }

        public String getStem() {
            return stem;
        }

        public void setAnswer(int answer) {
            this.answer = answer;
        }

        public int getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public void setSelections(List<String> selections) {
            this.selections = selections;
        }

        public List<String> getSelections() {
            return selections;
        }


        public int getAnswer() {
            return answer;
        }

    }


    //获取某张试卷
    @RequestMapping(value = "/teacher/test_paper/{test_paper_id}")
    public String getTest_paper(@PathVariable("test_paper_id") Integer test_paper_id ,Model model, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        model.addAttribute("title",test_paperService.findTest_paperById(test_paper_id).getTitle());
        model.addAttribute("duration",test_paperService.findTest_paperById(test_paper_id).getDuration());
        List<Questionslei> a = new ArrayList<Questionslei>();
        List<Integer> b = new ArrayList<>();

        for(Question_ownership c : question_ownershipService.findAll()){

            if(c.getQuestionOwnerMultiKeys().getTest_paper_id()==test_paper_id){
                b.add(c.getQuestionOwnerMultiKeys().getQuestion_id());
            }
        }
        for(Integer d : b){
            String[] f = questionService.findQuestionById(d).getContent().split("@caixukun@");
            List<String> g = Arrays.asList(f);
            QuestionOwnerMultiKeys questionOwnerMultiKeys = new QuestionOwnerMultiKeys();
            questionOwnerMultiKeys.setQuestion_id(d);
            questionOwnerMultiKeys.setTest_paper_id(test_paper_id);
            Questionslei e = new Questionslei(d,questionService.findQuestionById(d).getStem(),g,
                    questionService.findQuestionById(d).getSelection_id(),
                    question_ownershipService.findQuestion_ownershipById(questionOwnerMultiKeys).getScore());
            a.add(e);
        }
        model.addAttribute("questions",a);
        model.addAttribute("test_paper_id",test_paper_id);
        return "paper";
    }

    //添加新的试卷
    @RequestMapping(value = "/teacher/test_paper/",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addTest_paper(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();

        Test_paper  newTest_paper = new Test_paper();
        newTest_paper.setTitle(jsonObject.get("title").toString());
        newTest_paper.setDuration(Integer.valueOf(jsonObject.get("duration").toString()));
        test_paperService.addTest_paper(newTest_paper);
        Question_ownership newQuestion_ownership = new Question_ownership();

        JSONArray a = jsonObject.getJSONArray("questions");

        for(int i =0;i<a.size();i++){
            JSONObject b = a.getJSONObject(i);
            QuestionOwnerMultiKeys questionOwnerMultiKeys = new QuestionOwnerMultiKeys();
            questionOwnerMultiKeys.setTest_paper_id(newTest_paper.getId());
            questionOwnerMultiKeys.setQuestion_id(Integer.valueOf(b.get("question_id").toString()));
            newQuestion_ownership.setQuestionOwnerMultiKeys(questionOwnerMultiKeys);
            newQuestion_ownership.setScore(Integer.valueOf(b.get("score").toString()));
            question_ownershipService.addQuestion_ownership(newQuestion_ownership);
        }
        re.put("test_paper_id",newTest_paper.getId());
        return  re;


    }

    //删除某张试卷
    @RequestMapping(value = "/teacher/test_paper/",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject deleteTest_paper(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();
        int test_paper_id = Integer.valueOf(jsonObject.get("test_paper_id").toString());
        for(Test_paper a : test_paperService.findAll()){

            if(a.getId()==test_paper_id){
               test_paperService.deleteTest_paperById(test_paper_id);
                re.put("status",200);
                return re;
            }
        }
        re.put("status",404);
        return re;
    }


    //修改某张试卷
    @RequestMapping(value = "/teacher/test_paper/",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateTest_paper(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();


        JSONArray a = jsonObject.getJSONArray("delete_questions");

        for(int i =0;i<a.size();i++){
            JSONObject b = a.getJSONObject(i);
            for(Question_ownership c : question_ownershipService.findAll()){
                if(c.getQuestionOwnerMultiKeys().getQuestion_id()==Integer.valueOf(b.get("question_id").toString())){
                    question_ownershipService.deleteQuestion_ownershipById(c.getQuestionOwnerMultiKeys());
                }
            }
        }

        Test_paper test_paper = new Test_paper();
        test_paper.setId(Integer.valueOf(jsonObject.get("test_paper_id").toString()));
        test_paper.setDuration(Integer.valueOf(jsonObject.get("duration").toString()));
        test_paper.setTitle(jsonObject.get("title").toString());
        test_paperService.addTest_paper(test_paper);

        JSONArray d = jsonObject.getJSONArray("questions");

        for(int i =0;i<d.size();i++){
            JSONObject e = d.getJSONObject(i);
            Question_ownership question_ownership = new Question_ownership();
            question_ownership.setScore(Integer.valueOf(e.get("score").toString()));
            QuestionOwnerMultiKeys questionOwnerMultiKeys = new QuestionOwnerMultiKeys();
            questionOwnerMultiKeys.setTest_paper_id(Integer.valueOf(jsonObject.get("test_paper_id").toString()));
            questionOwnerMultiKeys.setQuestion_id(Integer.valueOf(jsonObject.get("question_id").toString()));
            question_ownership.setQuestionOwnerMultiKeys(questionOwnerMultiKeys);
            question_ownershipService.addQuestion_ownership(question_ownership);
        }

        re.put("status",200);





        return  re;
    }




    //获取试卷的列表：
    @RequestMapping(value = "/teacher/test_paper/list/{page}")
    public String getTest_paperlist(@PathVariable("page") Integer pagenum ,Model model, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        Page<Test_paper> page = test_paperService.getPage(pagenum,20);
        List<Test_paperlei> pageq = new ArrayList<Test_paperlei>();

        for(int i =0;i<page.getContent().size();i++){
            Test_paperlei a = new Test_paperlei(page.getContent().get(i).getId(),
                    page.getContent().get(i).getTitle(),page.getContent().get(i).getDuration());
            pageq.add(a);
        }
        model.addAttribute("page",pagenum);
        model.addAttribute("total_page",page.getTotalPages());
        model.addAttribute("test_papers",pageq);
        model.addAttribute( "status",200);
        return "papers";
    }

    class Test_paperlei{

        int test_paper_id;
        String title;
        int duration;

        public Test_paperlei(int test_paper_id,String title,int duration){

            this.duration = duration;
            this.test_paper_id = test_paper_id;
            this.title = title;

        }

        public int getTest_paper_id() {
            return test_paper_id;
        }

        public void setTest_paper_id(int test_paper_id) {
            this.test_paper_id = test_paper_id;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    //获取某个测试题
    @RequestMapping(value = "/teacher/test/{test_id}")
    public String getTest(@PathVariable("test_id") Integer test_id ,Model model, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        model.addAttribute("test_title",testService.findTestById(test_id).getTitle());
        model.addAttribute("test_paper_title",testService.findTestById(test_id).getTest_paper().getTitle());
        model.addAttribute("duration",testService.findTestById(test_id).getTest_paper().getDuration());
        model.addAttribute("start_time",testService.findTestById(test_id).getStart_time());
        model.addAttribute("end_time",testService.findTestById(test_id).getEnd_time());
        model.addAttribute("test_paper_id",testService.findTestById(test_id).getTest_paper().getId());
        model.addAttribute("test_id",test_id);
        return "test";
    }

    //添加新的测试
    @RequestMapping(value = "/teacher/test/",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addTest(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException, ParseException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();
        Test test = new Test();

        Date start_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.get("start_time").toString());
        Date end_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.get("end_time").toString());
        test.setStart_time(start_time);
        test.setEnd_time(end_time);
        test.setTitle(jsonObject.get("start_time").toString());
        test.setTest_paper(test_paperService.findTest_paperById(Integer.valueOf(jsonObject.get("test_paper_id").toString())));
        testService.addTest(test);
        re.put("status",200);
        re.put("test_id",test.getId());
        return re;
    }


    //删除某个测试
    @RequestMapping(value = "/teacher/test/",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject deleteTest(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();
        for(Test a : testService.findAll()){
            if(a.getId()==Integer.valueOf(jsonObject.get("test_id").toString())){
                testService.deleteTestById(Integer.valueOf(jsonObject.get("test_id").toString()));
                re.put("status",200);
                return re;
            }
        }

        re.put("status",404);
        return re;
    }

    //修改某次测试
    @RequestMapping(value = "/teacher/test/",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateTest(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException, ParseException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();
       Test test = new Test();
       test.setId(Integer.valueOf(jsonObject.get("test_id").toString()));
       test.setTest_paper(test_paperService.findTest_paperById(Integer.valueOf(jsonObject.get("test_paper_id").toString())));
       test.setTitle(jsonObject.get("title").toString());
       Date start_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.get("start_time").toString());
       Date end_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.get("end_time").toString());
       test.setStart_time(start_time);
       test.setEnd_time(end_time);
       re.put("status",200);
       return re;

    }


    //获取测试列表
    @RequestMapping(value = "/teacher/test/list/{page}")
    public String getTestlist(@PathVariable("page") Integer pagenum ,Model model, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        Page<Test> page = testService.getPage(pagenum,20);
        List<Testlei> pageq = new ArrayList<Testlei>();

        for(int i =0;i<page.getContent().size();i++){
            Testlei a = new Testlei(page.getContent().get(i).getId(),page.getContent().get(i).getTitle());
            pageq.add(a);
        }
        model.addAttribute("page",pagenum);
        model.addAttribute("total_page",page.getTotalPages());
        model.addAttribute("test_papers",pageq);
        return "tests";
    }

    class Testlei{
        int test_id;
        String title;
        public Testlei(int test_id,String title){

            this.test_id = test_id;
            this.title = title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTest_id(int test_id) {
            this.test_id = test_id;
        }

        public int getTest_id() {
            return test_id;
        }

    }


    //获取某次测试结果
    @RequestMapping(value="/teacher/student/list/{test_id}")
    public String getTestResult(@PathVariable("test_id") Integer test_id,Model model, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        model.addAttribute("title",testService.findTestById(test_id).getTitle());
        model.addAttribute("students_num",recordService.testStudents(test_id).size());
        List<String> testStudents = recordService.testStudents(test_id);
        List<Studentslei2> list = new ArrayList<>();
        for(String a : testStudents){
            Studentslei2 studentslei2 = new Studentslei2(a,studentService.findStudentById(a).getName(),
                    recordService.testStudentScore(test_id,a));
            list.add(studentslei2);
        }
        model.addAttribute("students",list);
        int sum = 0;
        for(Studentslei2 b : list){
            sum = sum + b.score;
        }
        float avg = sum/list.size();
        model.addAttribute("student_average",avg);
        return "testResult";
    }

    class Studentslei2{

        String student_id;
        String student_name;
        int score;

        public Studentslei2(String student_id,String student_name,int score){

            this.student_id=student_id;
            this.student_name=student_name;
            this.score = score;

        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getStudent_id() {
            return student_id;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getStudent_name() {
            return student_name;
        }

        public void setStudent_name(String student_name) {
            this.student_name = student_name;
        }

    }




    //获取某个学生的信息
    @RequestMapping(value = "/teacher/student/",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getStudent(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();
        re.put("student_id",jsonObject.get("student_id").toString());
        re.put("name",studentService.findStudentById(jsonObject.get("student_id").toString()).getName());
        return re;
    }



    //添加一个学生
    @RequestMapping(value = "/teacher/student/",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addStudent(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();
        Student newStudent = new Student();
        for(Student a : studentService.findAll()){
            if(a.getId().equals(jsonObject.get("student_id").toString())){
                re.put("status",404);
                return re;
            }
        }
        newStudent.setId(jsonObject.get("student_id").toString());
        newStudent.setName(jsonObject.get("name").toString());
        newStudent.setPassword(jsonObject.get("password").toString());
        studentService.addStudent(newStudent);
        re.put("status",200);
        return re;
    }

    //修改一个学生
    @RequestMapping(value = "/teacher/student/",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateStudent(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();
        Student newStudent = new Student();

        newStudent.setId(jsonObject.get("student_id").toString());

        newStudent.setName(jsonObject.get("name").toString());

        newStudent.setPassword(jsonObject.get("password").toString());

        studentService.addStudent(newStudent);

        re.put("status",200);

        return re;
    }


    //删除一个学生
    @RequestMapping(value = "/teacher/student/",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject deleteStudent(@RequestBody JSONObject jsonObject, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {
        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }
        JSONObject re = new JSONObject();
        studentService.deleteStudentById(jsonObject.get("student_id").toString());
        re.put("status",200);
        return re;
    }

    //获取学生列表
    @RequestMapping(value="/teacher/student/list/{page}")
    public String getStudentlist(@PathVariable("page") Integer pagenum ,Model model, @CookieValue("123456") String user_name,HttpServletResponse response)throws IOException {


        if(user_name ==null){
            response.sendRedirect("/teacher");
        }

        Page<Student> page = studentService.getPage(pagenum,20);
        List<Studentlei> pageq = new ArrayList<Studentlei>();

        for(int i =0;i<page.getContent().size();i++){
            Studentlei a = new Studentlei(page.getContent().get(i).getId(),
                    page.getContent().get(i).getName());
            pageq.add(a);
        }
        model.addAttribute("page",pagenum);
        model.addAttribute("total_page",page.getTotalPages());
        model.addAttribute("students",pageq);
        return "students";
    }



    class Studentlei{

        String student_id;
        String name;
        public Studentlei(String student_id,String name){
            this.name =name;
            this.student_id=student_id;
        }

        public void setStudent_id(String student_id) {
            this.student_id = student_id;
        }

        public String getStudent_id() {
            return student_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }




    @RequestMapping("/teacher/index")
    public String getExam(Model model, @CookieValue("123456") String user_name,HttpServletResponse response) throws IOException {


        if(!user_name.equals("123456")){
            response.sendRedirect("/teacher");
        }




        Test cur = testService.curentTest();
        String start_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cur.getStart_time());
        String end_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cur.getEnd_time());


        List<String> testStudents = recordService.testStudents(cur.getId());
        List<Grade> students = new ArrayList<>();

        for(String a : testStudents){
            Grade grade = new Grade(studentService.findStudentById(a).getName()
                    ,recordService.testStudentScore(cur.getId(),a),1,studentService.findStudentById(a).getId());
           students.add(grade);
        }
        //依据学生成绩排序
        Collections.sort(students, new Comparator<Grade>() {

            @Override
            public int compare(Grade u1, Grade u2) {
                if (u1.getScore() > u2.getScore()) {
                    return -1;
                }
                if (u1.getScore() == u2.getScore()) {
                    return 0;
                }
                return -1;
            }
        });

        for(int i =0;i<students.size();i++){
            students.get(i).setRanking(i+1);
        }
        int sum = 0;
        int max = 0;
        for(Grade b : students){
            sum = sum + b.score;
            if(b.score>max){
                max = b.score;
            }
        }
        float avg = sum/students.size();

        Exam exam = new Exam(cur.getTitle(),
                question_ownershipService.totalscorebyid(testService.curentTest().getTest_paper().getId()),
                avg,
                start_time,
                end_time,
                cur.getTest_paper().getDuration(),
                max);

        model.addAttribute("exam",exam );
        model.addAttribute("students",students);
        return "index";
    }

    class Exam{
        private String title;
        private int scores;
        private float average;
        private String beginning;
        private String ending;
        private int duration;
        private float max;
        public  Exam(String title,int scores,float average,String beginning,String ending,int duration,float max){
            this.title=title;
            this.average=average;
            this.scores=scores;
            this.beginning=beginning;
            this.ending=ending;
            this.duration=duration;
            this.max=max;
        }

        public String getTitle() {
            return title;
        }

        public float getAverage() {
            return average;
        }

        public int getScores() {
            return scores;
        }

        public float getMax() {
            return max;
        }

        public String getEnding() {
            return ending;
        }

        public String getBeginning() {
            return beginning;
        }

        public int getDuration() {
            return duration;
        }
    }
    class Grade{
        String name;
        int score;
        int ranking;
        String id;

        public Grade(String name,int score,int ranking,String id){
            this.ranking=ranking;
            this.id=id;
            this.score=score;
            this.name=name;
        }

        public int getRanking() {
            return ranking;
        }

        public int getScore() {
            return score;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }
    }




}
