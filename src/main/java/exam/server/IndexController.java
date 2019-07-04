package exam.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


// Controller注解用于指示该类是一个控制器
@Controller
public class IndexController {


    @RequestMapping("/teacher")
    public String index_(){
        return "login";
    }

}