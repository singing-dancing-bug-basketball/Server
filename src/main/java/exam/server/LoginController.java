package exam.server;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


    @Controller
    public class LoginController {

        @PostMapping(value = "/teacher/login",consumes = "application/json;charset=UTF-8;")
        @ResponseBody
        public String login(@RequestBody JSONObject jsonObject){
            System.out.println(jsonObject);
            return "success";
        }

    }
