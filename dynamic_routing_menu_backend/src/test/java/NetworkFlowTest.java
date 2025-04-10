import org.example.Application;
import org.example.entity.Menu;
import org.example.entity.NetworkFlow;
import org.example.service.MenuService;
import org.example.service.NetworkFlowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = Application.class)
public class NetworkFlowTest {

        @Autowired
        private NetworkFlowService networkFlowService;
        @Test
        public void testFindNetworkFlow() {
            List<NetworkFlow> networkflow =  networkFlowService.findNetworkFlow();
            networkflow.forEach(System.out::println);  // 自动调用 toString()
        }

}
