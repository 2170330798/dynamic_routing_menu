package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Train;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface TrainService extends IService<Train>{

    boolean trainModel(Train train) throws IOException;
}
