package org.example.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PreprocessConfig {
    private Map<String, Integer> label_mapping;//标签映射
    private List<String> columns_to_drop;//去掉的特征列
    private String label_column;//标签列

    public void load(String configPath) throws IOException {
        // 这里实现从JSON文件加载配置
        // 可以使用Jackson或Gson等库
        // 简化示例:
        ObjectMapper mapper = new ObjectMapper();
        mapper.readValue(new File(configPath), PreprocessConfig.class);
    }

    // getters
    public Map<String, Integer> getLabelMapping() { return label_mapping; }
    public List<String> getColumnsToDrop() { return columns_to_drop; }
    public String getLabelColumn() { return label_column; }

}
