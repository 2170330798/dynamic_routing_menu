package org.example.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PreprocessMap {
    // 预处理配置类

    private Map<String, Integer> label_mapping;
    private List<String> columns_to_drop;
    private String label_column;

    public void load(String configPath) throws IOException {
        // 这里实现从JSON文件加载配置
        // 可以使用Jackson或Gson等库
        // 简化示例:
        ObjectMapper mapper = new ObjectMapper();
        mapper.readValue(new File(configPath), PreprocessMap.class);
    }

    // getters
    public Map<String, Integer> getLabelMapping() { return label_mapping; }
    public List<String> getColumnsToDrop() { return columns_to_drop; }
    public String getLabelColumn() { return label_column; }

}
