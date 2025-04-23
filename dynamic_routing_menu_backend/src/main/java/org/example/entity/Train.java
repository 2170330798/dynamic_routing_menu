package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Train {

    @TableId(type = IdType.NONE)
    private Integer id;
    private String taskId; //训练任务进程ID
    private String csvSourcePath;//训练数据集csv文件路径
    private String csvDestPath;//保存打乱数据集csv文件路径
    private String modelVersion;
    private String labelColumn;//Label标签列
    private Long batchSize;//训练数据批次大小
    private Long rows;//读取csv多少行
    private Long numEpochs;//训练次数
    private double lr;//学习率
    private Long stepSize;//动态更新学习率的步长
    private double gamma;//学习率衰减比率
    private Long modelId;
    private String modelName;//模型名字
    private Long frameId;//框架ID
    private String modelSavePath;//模型保存路径

    private boolean isSaveCsvPath; //是否保存打乱的数据集

    public void setCsvSourcePath(String csvSourcePath) {
        this.csvSourcePath = csvSourcePath;
    }


    public void setCsvDestPath(String csvDestPath) {
        this.csvDestPath = csvDestPath;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public void setLabelColumn(String labelColumn) {
        this.labelColumn = labelColumn;
    }

    public void setBatchSize(Long batchSize) {
        this.batchSize = batchSize;
    }


    public void setRows(Long rows) {
        this.rows = rows;
    }



    public void setNumEpochs(Long numEpochs) {
        this.numEpochs = numEpochs;
    }


    public void setLr(double lr) {
        this.lr = lr;
    }


    public void setStepSize(Long stepSize) {
        this.stepSize = stepSize;
    }


    public void setGamma(double gamma) {
        this.gamma = gamma;
    }



    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }


    public void setModelName(String modelName) {
        this.modelName = modelName;
    }


    public void setFrameId(Long frameId) {
        this.frameId = frameId;
    }


    public void setModelSavePath(String modelSavePath) {
        this.modelSavePath = modelSavePath;
    }

    public boolean isSaveCsvPath() {
        return isSaveCsvPath;
    }

    public void setSaveCsvPath(boolean saveCsvPath) {
        isSaveCsvPath = saveCsvPath;
    }

    @Override
    public String toString() {
        return "Train{" +
                "csvSourcePath='" + csvSourcePath + '\'' +
                ", csvDestPath='" + csvDestPath + '\'' +
                ", modelVersion='" + modelVersion + '\'' +
                ", labelColumn='" + labelColumn + '\'' +
                ", batchSize=" + batchSize +
                ", rows=" + rows +
                ", numEpochs=" + numEpochs +
                ", lr=" + lr +
                ", stepSize=" + stepSize +
                ", gamma=" + gamma +
                ", modelId=" + modelId +
                ", modelName='" + modelName + '\'' +
                ", frameId=" + frameId +
                ", modelSavePath='" + modelSavePath + '\'' +
                ", isSaveCsvPath=" + isSaveCsvPath +
                '}';
    }
}
