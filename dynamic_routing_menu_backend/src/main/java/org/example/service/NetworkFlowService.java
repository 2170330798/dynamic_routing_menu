package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.NetworkFlow;
import org.example.entity.PageResult;
import org.example.service.impl.NetworkFlowServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface NetworkFlowService extends IService<NetworkFlow> {
    List<NetworkFlow> findNetworkFlow();

    // 新增分页查询方法
    PageResult<NetworkFlow> findNetworkFlowByPage(int pageNum, int pageSize);
}
