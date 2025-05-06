//package org.example.entity;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class Train {
//
//    @TableId(type = IdType.NONE)
//    private Integer id;
//    private String taskId; //训练任务进程ID
//    private String csvSourcePath;//训练数据集csv文件路径
//    private String csvDestPath;//保存打乱数据集csv文件路径
//    private String modelVersion;
//    private String labelColumn;//Label标签列
//    private Long batchSize;//训练数据批次大小
//    private Long rows;//读取csv多少行
//    private Long numEpochs;//训练次数
//    private double lr;//学习率
//    private Long stepSize;//动态更新学习率的步长
//    private double gamma;//学习率衰减比率
//    private Long modelId;
//    private String modelName;//模型名字
//    private Long frameId;//框架ID
//    private String modelPath;//模型路径
//    private String modelSavePath;//模型保存路径
//    private boolean isSaveCsvPath; //是否保存打乱的数据集
//    private Long patience;//早停逻辑的损失下降容忍次数
//    private Long initMode;//模型初始化模式
//    private Long factor;//RNN学习率下降指数
//    private float weightDecay;//RNN权重比
//    private Long validateMode;//验证模式
//}
