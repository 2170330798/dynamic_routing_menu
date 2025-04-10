package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.OperatorSystemInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperatorSystemInfoService extends IService<OperatorSystemInfo> {

    List<OperatorSystemInfo> getOperatorSystemInfo();
}
