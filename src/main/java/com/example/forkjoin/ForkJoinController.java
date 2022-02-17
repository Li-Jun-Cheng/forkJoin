package com.example.forkjoin;

import com.example.forkjoin.service.ForkJoinServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Controller
public class ForkJoinController {
@Resource
ForkJoinServiceImpl forkJoinService;
    @RequestMapping("/index")
    @ResponseBody
    public String forkJoin(){
        System.out.println("hello world");
        forkJoinService.forkJoin();
        return "hello world";
    }
}
