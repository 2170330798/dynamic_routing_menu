import axios from "axios";
import type { TRAIN } from "../components/train/train";
import { TRAIN_MODEL_REQUEST_API } from "../vite-env.d";


export const trainModel = async(data :TRAIN) => {


      const request = await axios.post(TRAIN_MODEL_REQUEST_API, data);
      console.log('提交训练数据', data);
      if (request.data.code === 200){
            console.log('提交训练数据成功', request.data.code);
            return 200;
      }else if(request.data.code == 500){
            console.log('训练数据违规，请重新提交', request.data.code);
            return 500;
      }else {
            console.log('提交训练数据失败', request.data.code);
            return request.data.code;
      }
} 
