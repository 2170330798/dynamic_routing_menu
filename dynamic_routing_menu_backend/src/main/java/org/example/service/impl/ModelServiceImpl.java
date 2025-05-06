package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Model;
import org.example.mapper.ModelMapper;
import org.example.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class ModelServiceImpl extends ServiceImpl<ModelMapper, Model> implements ModelService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Model> getModelList(){
        //从数据查询一维向量数据集合
        List <Model> models = modelMapper.selectList(null);
        return  models;
    }

    @Override
    public boolean addModel(Model model) {
        // 1. 验证ID必须存在
        if (model.getModelId() == null) {
            throw new IllegalArgumentException("模型ID不能为空");
        }

        // 2. 检查ID是否已存在
        if (this.getById(model.getModelId()) != null) {
            throw new IllegalArgumentException("模型ID已存在");
        }

        // 3. 其他业务验证
        if (!StringUtils.hasText(model.getModelName()) || !StringUtils.hasText(model.getModelName())) {
            throw new IllegalArgumentException("模型名称不能为空");
        }

        // 4. 保存到数据库
        return this.save(model);
    }

    @Override
    public boolean deleteModel(Long modeId) {
        // 1. 检查菜单是否存在（可选）
        Model model = modelMapper.selectById(modeId);
        if (model == null) {
            return false;
        }

        if (model.getIsApply()) {
            return false;
        }
        try {
            Path path = Paths.get(model.getModelPath());
            boolean deleted = Files.deleteIfExists(path); // Java 7+
            System.out.println(deleted ? "模型已删除" : "模型不存在");
        } catch (IOException e) {
            System.out.println("模型删除失败: " + e.getMessage());
        }

        return modelMapper.deleteById(modeId) > 0;
    }

    @Override
    public boolean updateModel(Model model) {

        // 在更新前检查菜单是否存在
        if (getById(model.getModelId()) == null) {
            throw new IllegalArgumentException("模型不存在");
        }
        // 1. 验证ID必须存在
        if (this.getById(model.getModelId())== null) {
            throw new IllegalArgumentException("模型ID不能为空");
        }

        // 2. 检查路径是否已存在
        if (model.getModelPath() == null) {
            throw new IllegalArgumentException("模型存储路径不能为空");
        }

        // 3. 其他业务验证
        if (!StringUtils.hasText(model.getModelName())) {
            throw new IllegalArgumentException("模型名字不能为空");
        }

        if (checkModelsIsApply(model)) {
            throw new IllegalArgumentException("有模型已经被使用");
        }

        // 4. 保存到数据库
        return this.updateById(model);
    }

    public boolean checkModelsIsApply(Model model) {
        // 5.保证当前仅有一个模型被使用
        List<Model> models = modelMapper.selectList(null);
        for (Model item : models) {
            if (item.getIsApply() && !Objects.equals(model.getModelId(), item.getModelId())) {
                return  true;
            }
        }
        return false;

    }

}
