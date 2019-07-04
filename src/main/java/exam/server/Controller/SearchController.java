package exam.server.Controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @RequestMapping(value = "/index/studentName",method = RequestMethod.GET,consumes = "application/json;charset=UTF-8;")
    public List<Grade> searchStudents(@RequestParam JSONObject name){
        List<Grade> students = new ArrayList<>();
        students.add(new Grade("小明",100,1,"111"));
        System.out.println("err");
        return students;
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
