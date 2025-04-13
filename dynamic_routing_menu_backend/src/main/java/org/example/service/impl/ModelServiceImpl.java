package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Model;
import org.example.mapper.ModelMapper;
import org.example.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

        // 2. 删除菜单（返回影响的行数 > 0 表示成功）
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

        // 4. 保存到数据库
        return this.updateById(model);
    }

}
