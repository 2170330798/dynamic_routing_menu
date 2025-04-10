package org.example.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.NetworkFlow;
import org.example.entity.PageResult;
import org.example.mapper.NetworkFlowMapper;
import org.example.service.NetworkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NetworkFlowServiceImpl extends ServiceImpl<NetworkFlowMapper, NetworkFlow> implements NetworkFlowService {

      @Autowired
      private NetworkFlowMapper networkFlowMapper;

      @Override
      public List<NetworkFlow> findNetworkFlow(){
          //从数据查询一维向量数据集合
          return networkFlowMapper.selectList(null);
      }


    // 新增分页查询方法
     @Override
     public PageResult<NetworkFlow> findNetworkFlowByPage(int pageNum, int pageSize) {
         // 创建分页构造器
         Page<NetworkFlow> page = new Page<>(pageNum, pageSize);

         // 执行分页查询
         Page<NetworkFlow> result = networkFlowMapper.selectPage(page, null);

         // 转换为自定义分页结果
         return new PageResult<>(
                 (int)result.getCurrent(),
                 (int)result.getSize(),
                 result.getTotal(),
                 result.getRecords()
         );
     }
}
