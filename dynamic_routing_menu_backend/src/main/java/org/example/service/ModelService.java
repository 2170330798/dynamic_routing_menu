package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Model;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModelService extends IService<Model> {

    List<Model> getModelList();

    boolean addModel(Model model);

    boolean deleteModel(Long modeId);

    boolean updateModel(Model model);
}
