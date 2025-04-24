package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.example.entity.Train;
import org.example.mapper.FrameMapper;
import org.example.mapper.ModelMapper;
import org.example.mapper.TrainMapper;
import org.example.service.TrainService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train> implements TrainService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FrameMapper frameMapper;
    @Override
    public boolean trainModel(Train train) throws IOException {
        // 检查参数是否合规
        checkParams(train);

        // 全局单例配置（推荐）
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // 连接超时
                .readTimeout(0, TimeUnit.MILLISECONDS) // 读取不限制‌
                .writeTimeout(60, TimeUnit.SECONDS) // 写入超时
                .build();

        // 构造 JSON
        Map<String, Object> data = new HashMap<>();
        data.put("taskId", train.getTaskId());
        data.put("csvSourcePath", train.getCsvSourcePath());
        data.put("csvDestPath", train.getCsvDestPath());
        data.put("labelColumn", train.getLabelColumn());
        data.put("batchSize", train.getBatchSize());
        data.put("rows", train.getRows());
        data.put("modelPath", train.getModelPath());
        data.put("numEpochs", train.getNumEpochs());
        data.put("lr", train.getLr());
        data.put("stepSize", train.getStepSize());
        data.put("gamma", train.getGamma());
        data.put("modelId", train.getModelId());
        data.put("modelVersion", train.getModelVersion());
        data.put("modelName", train.getModelName());
        data.put("frameName", frameMapper.selectById(train.getFrameId()).getFrameName());
        data.put("modelSavePath", train.getModelSavePath());
        data.put("isSaveCsvPath", train.isSaveCsvPath());

        // 转换为 JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        System.out.println(json);

        // 构造请求体
        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));

        // 向 Python 提供的 API 发送请求
        Request request = new Request.Builder()
                .url("http://127.0.0.1:8000/training_model/")
                .header("Host", "127.0.0.1")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            System.out.println(response.body().string());
        }
        return true;
    }

    public void checkParams(Train train) {
        if (train.getTaskId() == null) {
            throw new IllegalArgumentException("训练任务ID不能为空");
        }
        if (train.getModelId() == null){
            throw new IllegalArgumentException("模型ID不能为空");
        }
        if (modelMapper.selectById(train.getModelId()) != null){
            throw new IllegalArgumentException("模型ID已经存在");
        }
        if (train.getCsvSourcePath() == null) {
            throw new IllegalArgumentException("数据集路径不存在");
        }
        if(train.getBatchSize() == null) {
            throw new IllegalArgumentException("请输入训练集批次大小");
        }
        if(train.getGamma() <= 0) {
            throw new IllegalArgumentException("请输入学习率衰减比率");
        }
        if(train.getModelSavePath() == null){
            throw new IllegalArgumentException("模型保存路径为空");
        }
        if(train.getFrameId() == null) {
            throw new IllegalArgumentException("框架ID不能为空");
        }
        if(train.getLabelColumn() == null) {
            throw new IllegalArgumentException("标签列不能为空");
        }
        if(train.getStepSize() == null) {
            throw new IllegalArgumentException("学习率衰步长不能为空");
        }
        if(train.getLr() <= 0) {
            throw new IllegalArgumentException("学习率太小了");
        }
    }
}
