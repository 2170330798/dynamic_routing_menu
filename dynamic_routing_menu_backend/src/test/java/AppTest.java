import org.example.Application;
import org.example.entity.Menu;
import org.example.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest(classes = Application.class)
public class AppTest {
    @Autowired
    private MenuService menuService;




    @Test
    public void testFindMenuTree() {
        List<Menu> menuTree = menuService.findMenuTree();
        menuTree.forEach(System.out::println);  // 自动调用 toString()
    }
}