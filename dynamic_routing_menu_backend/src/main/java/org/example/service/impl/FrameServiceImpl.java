package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Frame;
import org.example.mapper.FrameMapper;
import org.example.service.FrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
public class FrameServiceImpl extends ServiceImpl<FrameMapper, Frame> implements FrameService {

    @Autowired
    private FrameMapper frameMapper;

    @Override
    public List<Frame> getFrameList(){
        //从数据查询一维向量数据集合
        return frameMapper.selectList(null);
    }

    @Override
    public boolean addFrame(Frame frame) {
        // 1. 验证ID必须存在
        if (frame.getFrameId() == null) {
            throw new IllegalArgumentException("框架ID不能为空");
        }

        // 2. 检查ID是否已存在
        if (this.getById(frame.getFrameId()) != null  || frame.getFrameId() <= 0) {
           throw new IllegalArgumentException("框架ID已存在");
        }

        // 3. 其他业务验证
        if (!StringUtils.hasText(frame.getFrameName())) {
            throw new IllegalArgumentException("框架名称或者菜单标题不能为空");
        }

        // 4. 保存到数据库
        return this.save(frame);
    }

    @Override
    public boolean deleteFrame(Long frameId) {
        // 1. 检查框架是否存在（可选）
        Frame frame = frameMapper.selectById(frameId);
        if (frame == null) {
            return false;
        }

        return frameMapper.deleteById(frameId) > 0;
    }

    @Override
    public boolean updateFrame(Frame frame) {

        // 在更新前检查菜单是否存在
        if (getById(frame.getFrameId()) == null) {
            throw new IllegalArgumentException("菜单不存在");
        }
       // 1. 验证ID必须存在
        if (this.getById(frame.getFrameId())== null) {
           throw new IllegalArgumentException("菜单ID不能为空");
        }

        // 3. 其他业务验证
        if (!StringUtils.hasText(frame.getFrameName())) {
            throw new IllegalArgumentException("框架名称不能为空");
        }

        // 4. 保存到数据库
        return this.updateById(frame);
    }
}
