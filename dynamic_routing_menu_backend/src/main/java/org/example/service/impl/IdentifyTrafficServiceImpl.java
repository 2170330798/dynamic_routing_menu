package org.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import okhttp3.*;
import org.example.entity.IdentifyTraffic;
import org.example.entity.Model;
import org.example.entity.PageResult;
import org.example.mapper.IdentifyTrafficMapper;
import org.example.mapper.ModelMapper;
import org.example.service.IdentifyTrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class IdentifyTrafficServiceImpl extends ServiceImpl<IdentifyTrafficMapper, IdentifyTraffic> implements IdentifyTrafficService {

    @Autowired
    private IdentifyTrafficMapper identifyTrafficMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void identifyTraffic() throws IOException {
        String modelPath = null;
        List<Model> models = modelMapper.selectList(null);
        for(Model model: models) {
            if(model.getIsApply()) {
                modelPath = model.getModelPath();
                break;
            }
        }

        // 全局单例配置（推荐）
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // 连接超时
                .readTimeout(0, TimeUnit.MILLISECONDS) // 读取不限制‌
                .writeTimeout(60, TimeUnit.SECONDS)    // 写入超时
                .build();

        // 构造 JSON
        Map<String, Object> data = new HashMap<>();
        data.put("csvSourcePath", "D:/VueProject/PythonWeb/myDjango/app/dataset/train_dataset_06.csv");
        data.put("labelColumn", "Label");
        data.put("batchSize", 32);
        data.put("startIndex", 0);
        data.put("endIndex", 1000);
        data.put("modelPath", modelPath);

        // 转换为 JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(data);
        System.out.println(json);
        //构造请求体
        RequestBody body = RequestBody.create(String.valueOf(json), MediaType.get("application/json"));
        //向Python提供的API发送请求
        Request request = new Request.Builder()
                .url("http://127.0.0.1:8000/identify_traffic/")
                .header("Host", "127.0.0.1")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            System.out.println(response.body().string());
        }
    }


    @Override
    public PageResult<IdentifyTraffic> findTrafficLogByPage(long pageNum, long pageSize) {
        // MyBatis-Plus 分页查询
        Page<IdentifyTraffic> page = new Page<>(pageNum, pageSize);
        identifyTrafficMapper.selectPage(page, null); // 第二个参数是查询条件（null 表示查全部）

        // 封装返回结果
        return new PageResult<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                page.getPages(),
                page.getRecords());
    }
}

