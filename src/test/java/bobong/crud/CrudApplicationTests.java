package bobong.crud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CrudApplicationTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void pwn(){
        String pwd = "jh07bg02";
        String encoded = passwordEncoder.encode(pwd);
        System.out.println("encoded = " + encoded);
    }

}
