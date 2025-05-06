import org.example.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = Application.class)
public  class WebsocketTest {

    @MockBean
    private SimpMessagingTemplate messagingTemplate;

    @Test
    void testScheduledTask() throws InterruptedException {
        // 等待调度执行
        Thread.sleep(1500); // 稍大于1秒的间隔

        // 验证至少调用了一次
        verify(messagingTemplate, atLeastOnce())
                .convertAndSend("/topic/system-metrics", anyMap());
    }

}