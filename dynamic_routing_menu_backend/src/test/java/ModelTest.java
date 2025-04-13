import org.example.Application;
import org.example.entity.Model;
import org.example.service.ModelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = Application.class)
public class ModelTest {

    @Autowired
    private ModelService modelService;
    @Test
    public void testFindModel() {
        List<Model> models =  modelService.getModelList();
        models.forEach(System.out::println);  // 自动调用 toString()
    }

}
