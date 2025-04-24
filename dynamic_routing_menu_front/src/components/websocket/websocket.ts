// src/utils/websocket.ts

export interface ParsedTrainingData {
  type: string;
  progress: { current: number; total: number };
  epoch: number;
  epochs: number;
  loss: number;
  accuracy: number;
  timestamp: string;
}

export interface TrainingMessage {
  id: number;
  // type: string;
  // message: ParsedTrainingData | {  // 可以是原始数据或解析后的数据
  //   type: string;
  //   progress: string;
  //   epoch: string;
  //   epochs: string;
  //   loss: string;
  //   accuracy: string;
  //   timestamp: string;
  // };
}
