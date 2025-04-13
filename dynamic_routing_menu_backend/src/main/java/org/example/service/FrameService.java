package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Frame;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface FrameService extends IService<Frame> {
    List<Frame> getFrameList();

    boolean addFrame(org.example.entity.Frame frame);

    boolean deleteFrame(Long frameId);

    boolean updateFrame(org.example.entity.Frame frame);
}
