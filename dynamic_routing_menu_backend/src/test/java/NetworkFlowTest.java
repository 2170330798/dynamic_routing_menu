import org.example.Application;
import org.example.entity.NetworkFlow;
import org.example.entity.PageResult;
import org.example.service.NetworkFlowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class NetworkFlowTest {

        @Autowired
        private NetworkFlowService networkFlowService;

        @Test
        public void testFindNetworkFlowByPage() {
            PageResult<NetworkFlow> networkFlows = networkFlowService.findNetworkFlowByPage(1,10);
            System.out.println(networkFlows);
        }

}
