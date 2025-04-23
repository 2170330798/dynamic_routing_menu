export interface IFrame {

    frameId: number;
    frameName: string;
    frameDescribe: string;
    frameCharacter: string;
    
}

export const tmpFrame: IFrame = {
    frameId: 0,
    frameName: "",
    frameDescribe: "",
    frameCharacter: ""
}

export const initTmpFrameList = (data: IFrame):IFrame =>{
    data.frameId = 0;
    data.frameName = "";
    data.frameDescribe = "";
    data.frameCharacter = "";
    return data
}