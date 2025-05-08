//package org.example.service.impl;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import org.example.entity.NetworkFlow;
//import org.example.entity.PageResult;
//import org.example.mapper.NetworkFlowMapper;
//import org.example.service.NetworkFlowService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class NetworkFlowServiceImpl extends ServiceImpl<NetworkFlowMapper, NetworkFlow> implements NetworkFlowService {
//
//    @Autowired
//    private NetworkFlowMapper networkFlowMapper;
//
//    @Override
//    public PageResult<NetworkFlow> findNetworkFlowByPage(long pageNum, long pageSize) {
//        // MyBatis-Plus 分页查询
//        Page<NetworkFlow> page = new Page<>(pageNum, pageSize);
//        networkFlowMapper.selectPage(page, null); // 第二个参数是查询条件（null 表示查全部）
//
//        // 封装返回结果
//        return new PageResult<>(
//                page.getCurrent(),
//                page.getSize(),
//                page.getTotal(),
//                page.getPages(),
//                page.getRecords());
//    }
//
//
//}
