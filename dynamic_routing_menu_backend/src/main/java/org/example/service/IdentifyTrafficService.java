package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entity.IdentifyTraffic;
import org.example.entity.PageResult;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public interface IdentifyTrafficService extends IService<IdentifyTraffic> {

    void identifyTraffic() throws IOException;

    PageResult<IdentifyTraffic> findTrafficLogByPage(long pageNum, long pageSize);
}
