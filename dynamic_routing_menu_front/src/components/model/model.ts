export interface IMODEL {
    modelId: number;
    modelVersion: string;
    modelName: string;
    loss: number;
    accuracy: number;
    createDate: Date;
    updateTime: Date;
    modelPath: string;
    isApply: boolean;
}


export const tmpIModel: IMODEL = {
    modelId: 0,
    modelVersion: "",
    modelName: "",
    loss: 0,
    accuracy: 0,
    createDate: new Date(2024, 0, 0),
    updateTime: new Date(2024, 0, 0),
    modelPath: "",
    isApply: false
}

export const setTmpModelList = (row: any) => {
    tmpIModel.modelId = row.modelId;
    tmpIModel.modelVersion = row.modelVersion;
    tmpIModel.modelName = row.modelName,
    tmpIModel.loss = row.loss;
    tmpIModel.accuracy = row.accuracy;
    tmpIModel.createDate = row.createDate;
    tmpIModel.updateTime = row.updateModel;
    tmpIModel.modelPath = row.modelPath;
    tmpIModel.isApply = row.isApply;
}