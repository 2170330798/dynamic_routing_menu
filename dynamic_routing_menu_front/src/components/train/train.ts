
export interface TrainData{
    
    file: string;
    databse: string;
    frameId: number;
    frameName: string;

}

export interface TRAIN{
      csvSourcePath: string;//训练数据集csv文件路径
      csvDestPath: string;//保存打乱数据集csv文件路径
      labelColumn: string;//Label标签列
      batchSize: number;//训练数据批次大小
      rows: number;//读取csv多少行
      numEpochs: number;//训练次数
      lr: number;//学习率
      stepSize: number;//动态更新学习率的步长
      gamma: number;//学习率衰减比率
      modelId: number;//模型ID
      modelName: string;//模型名字
      modelVersion: string;//模型版本
      frameId: number;//框架ID
      modelPath: string;
      modelSavePath: string;//模型保存路径
      trainModelWays: boolean;
      isSaveCsvPath: boolean; //是否保存打乱的数据集
      taskId: string; //任务进程ID
}

export const trainDataItem: TRAIN = {
  csvSourcePath: "",
  csvDestPath: "",
  labelColumn: "",
  batchSize: 0,
  rows: 0,
  numEpochs: 0,
  lr: 0,
  stepSize: 0,
  gamma: 0,
  modelName: "",
  frameId: 0,
  modelSavePath: "",
  isSaveCsvPath: false,
  modelVersion: "",
  modelId: 0,
  taskId: "",
  modelPath: '',
  trainModelWays: false
}

// 在组件外部定义常量
export const INITIAL_TRAIN_DATA: TRAIN = {
  csvSourcePath: "",
  csvDestPath: "",
  labelColumn: "",
  batchSize: 0,
  rows: 0,
  numEpochs: 0,
  lr: 0,
  stepSize: 0,
  gamma: 0,
  modelName: "",
  frameId: 0,
  modelSavePath: "",
  isSaveCsvPath: false,
  modelVersion: "",
  modelId: 0,
  taskId: "",
  modelPath: '',
  trainModelWays: false
};

export const train_data = {
  csvSourcePath: "F:\\PythonWeb\\myDjango\\app\\dataset\\train_dataset_03.csv",//训练数据集csv文件路径
  csvDestPath: "",//保存打乱数据集csv文件路径
  labelColumn: "Label",//Label标签列
  batchSize: 32,//训练数据批次大小
  rows: 3000,//读取csv多少行
  numEpochs: 2,//训练次数
  lr: 0.01,//学习率
  stepSize: 32,//动态更新学习率的步长
  gamma: 0.1,//学习率衰减比率
  modelId: 1,//模型ID
  modelName: "model_test",//模型名字
  modelVersion: "v1.0.0",//模型版本
  frameId: 1,//框架ID
  modelPath: "",
  modelSavePath: "F:\\PythonWeb\\myDjango\\app\\model",//模型保存路径
  trainModelWays: false,
  isSaveCsvPath: false, //是否保存打乱的数据集
  taskId: "task_123" //任务进程ID
}