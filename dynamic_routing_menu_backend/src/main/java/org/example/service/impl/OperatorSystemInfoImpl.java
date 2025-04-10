package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Menu;
import org.example.entity.OperatorSystemInfo;
import org.example.mapper.OperatorSystemInfoMapper;
import org.example.service.OperatorSystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public  class OperatorSystemInfoImpl extends ServiceImpl<OperatorSystemInfoMapper, OperatorSystemInfo> implements OperatorSystemInfoService {


    @Autowired
    private OperatorSystemInfoMapper operatorSystemInfoMapper;

    @Override
    public List<OperatorSystemInfo> getOperatorSystemInfo() {

        double cpuUsage = statisticCpu();
        double memoryUsage = statisticMemory();
        double networkUsage = statisticNetwork();

        List <OperatorSystemInfo> info = operatorSystemInfoMapper.selectList(null);

        return info;
    }

    private double statisticNetwork() {

        return  0.0;
    }

    private double statisticMemory() {

        return  0.0;
    }

    public static  double statisticCpu() {
        return  0.0;
    }
}
