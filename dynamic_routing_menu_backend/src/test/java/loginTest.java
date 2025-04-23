import org.example.Application;
import org.example.entity.User;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class loginTest {
      @Autowired
      private UserService userService;

      @Test
      public void login() {

           User user = new User();
           user.setPassword("123456");
           user.setUserName("ArcticFox");
           System.out.println(userService.login(user));
      }

      @Test
      public void getUser() {
          System.out.println(userService.findUserById("ArcticFox").toString());
      }
}
