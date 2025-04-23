import org.example.Application;

import org.example.service.IdentifyTrafficService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.IOException;


@SpringBootTest(classes = Application.class)
public class IdentifyTrafficTest {

    @Autowired
    private  IdentifyTrafficService identifyTrafficService;
    @Test
    public void testIdentifyTrafficServiceImpl() throws IOException {
           identifyTrafficService.identifyTraffic();
    }
}