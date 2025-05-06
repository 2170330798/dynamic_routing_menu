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
      frame: number;//框架ID
      modelPath: string;
      modelSavePath: string;//模型保存路径
      isSaveCsvData: boolean; //是否保存打乱的数据集
      taskId: string; //任务进程ID
      patience: number;
      initMode: number;
      factor: number;
      weightDecay: number;
      validateMode: number;
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
  frame: 0,
  modelSavePath: "",
  isSaveCsvData: false,
  modelVersion: "",
  modelId: 0,
  taskId: "",
  modelPath: '',
  patience: 0,
  initMode: 0,
  factor: 0,
  weightDecay: 0,
  validateMode: 0
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
  frame: 0,
  modelSavePath: "",
  isSaveCsvData: false,
  modelVersion: "",
  modelId: 0,
  taskId: "",
  modelPath: "",
  patience: 0,
  initMode: 0,
  factor: 0,
  weightDecay: 0,
  validateMode: 0
};

export const train_data = {
    task_id: "task_123",
    num_epochs: 5,
    batch_size: 32,
    label_column: "Label",
    patience: 5,
    lr: 0.001,
    model_path: "F:\\PythonWeb\\myDjango\\app\\model\\rnn_models\\model-01.pth",
    model_save_path: "F:\\PythonWeb\\myDjango\\app\\model\\rnn_models",
    csv_source_path: 'F:\\PythonWeb\\myDjango\\app\\dataset\\train_dataset_03.csv',
    model_id: 14, 
    model_version: "v1.0.0", 
    model_name: "model-02",
    frame_id: 2,
    init_mode: 0,
    factor: 0.5,
    weight_decay: 1e-5,
    validate_mode: 0,
    csv_dest_file: "",
    is_save_data: false
}