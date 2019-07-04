package exam.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


// Controller注解用于指示该类是一个控制器
@Controller
public class IndexController {

    @RequestMapping("/inddex")
    public String index() {
        return "index";
    }

    @RequestMapping("/")
    public String index_(){
        return "login";
    }

}