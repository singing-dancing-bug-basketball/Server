package exam.server;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private Question_ownershipService question_ownershipService;
    @Autowired
    private Test_paperService test_paperService;


    @RequestMapping(value = "/teacher",method = RequestMethod.GET)
    public String index_(){
        return "login";
    }

    //登陆
    @RequestMapping(value = "teacher/login",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(@RequestBody JSONObject jsonObject,HttpSession session){
        JSONObject re = new JSONObject();
        if(jsonObject.get("id").equals("123456")&&jsonObject.get("password").equals("123456")){
            session.setAttribute("user_name",jsonObject.get("id"));
            re.put("status",200);
            return re;
        }
        re.put("status",404);
        return re;
    }

    //获取某个问题
    @RequestMapping(value = "/teacher/question/",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getQuestion(@RequestBody JSONObject jsonObject){

      Question a = questionService.findQuestionById(Integer.valueOf(jsonObject.get("question_id").toString()));
      JSONObject re = new JSONObject();
      re.put("stem",a.getStem());
      String[] b = a.getContent().split("@caixunkun@");
      List<String> list = Arrays.asList(b);
      re.put("selections",list);
      re.put("answer",a.getSelection_id());
      return re;
    }

    //添加新的问题
    @RequestMapping(value = "/teacher/question/",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addQuestion(@RequestBody JSONObject jsonObject){
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
    public JSONObject deleteQuestion(@RequestBody JSONObject jsonObject){

        JSONObject re = new JSONObject();
        if(questionService.existsByQustion_id(Integer.valueOf(jsonObject.get("question_id").toString()))){

            re.put("status",200);
            return  re;
        }
        re.put("status",404);
        return re;


    }

    //修改问题
    @RequestMapping(value = "/teacher/question/",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateQuestion(@RequestBody JSONObject jsonObject){

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
    public String getQuestionlist(@PathVariable("page") Integer pagenum ,Model model){

        Page<Question> page = questionService.getPage(pagenum,20);
        List<Questionslei> pageq = new ArrayList<Questionslei>();

        for(int i =0;i<page.getContent().size();i++){
            String[] a = page.getContent().get(i).getContent().split("@caixukun@");
            List<String> b = Arrays.asList(a);
            Questionslei c = new Questionslei(page.getContent().get(i).getQustion_id(), page.getContent().get(i).getStem(),b,page.getContent().get(i).getSelection_id());
            pageq.add(c);
        }

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

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
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
    public String getTest_paper(@PathVariable("test_paper_id") Integer test_paper_id ,Model model){

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
           Questionslei e = new Questionslei(d,questionService.findQuestionById(d).getStem(),g,
                   questionService.findQuestionById(d).getSelection_id());
           a.add(e);
       }
       model.addAttribute("questions",a);
       return "paper";
    }

    //添加新的试卷
    @RequestMapping(value = "/teacher/test_paper/")
    public JSONObject addTest_paper(@RequestBody JSONObject jsonObject){
        JSONObject re = new JSONObject();

        Test_paper  newTest_paper = new Test_paper();
        newTest_paper.setTitle(jsonObject.get("title").toString());
        newTest_paper.setDuration(Integer.valueOf(jsonObject.get("duration").toString()));
        test_paperService.addTest_paper(newTest_paper);
        Question_ownership newQuestion_ownership = new Question_ownership();


        re.put("test_paper_id",newTest_paper.getId());
        return  re;


    }








    @RequestMapping("/teacher/index")
    public String getExam(Model model){

        Exam exam = new Exam("test",100,90,"2017-6-20","2017-6-20",90,100);
        List<TeacherController.Grade> students = new ArrayList<>();
        students.add(new Grade("小明",100,1,"111"));
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

    }




}
