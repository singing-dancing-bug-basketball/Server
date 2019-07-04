package exam.server.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ExamController {
    @RequestMapping("/index")
    public String getExam(Model model){
        Exam exam = new Exam("test",100,90,"2017-6-20","2017-6-20",90,100);
        List<Grade> students = new ArrayList<>();
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
