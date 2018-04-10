package com.litsoft.evaluateserver.controllerTest.userTest;

import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.util.MdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAddTest {


    @Test
    public void getString() throws Exception {

        //
        //e341acc5e59887de724163e068d86405
        String salt = "ddd006a6357-e7b9-4de7-bca5-22130fd2a7c9";
        User s =  MdUtil.md5Password("wise", "666666");
        System.out.println(salt);
       System.out.println(s);
    }
}
