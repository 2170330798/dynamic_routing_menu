<template>
    <div class="train-container">
        <div class="header-container">
             <h1>训练模型</h1>
        </div>
        <div class="btn-container">
            <el-upload ref="upload" class="upload-btn" :show-file-list="false" action="" :limit="1" :on-exceed="handleExceed" :on-change="handleFileChange" :auto-upload="false">
                <template #trigger>
                    <el-input class="input-btn" v-model="filename"   size="small" placeholder="请选择上传数据集" readonly/>
                </template>
                <el-button class="ml-3" size="small" type="primary" @click="submitUpload">上传</el-button>
            </el-upload>
            <el-select class="select-btn1" v-model="value" size="small" placeholder="选则数据库数据集" style="width: 150px">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <el-select class="select-btn2" v-model="value" size="small" placeholder="选择训练模型" style="width: 150px">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <el-button class="train-btn" type="primary" size="small" >训练</el-button>
        </div>
        <div class="table-container1">
             <FrameList />
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { genFileId } from 'element-plus';
import type { UploadFile, UploadInstance, UploadProps, UploadRawFile } from 'element-plus';
import FrameList from './FrameList.vue';
const filename = ref('');
const upload = ref<UploadInstance>();
const handleExceed: UploadProps['onExceed'] = (files) => {
  upload.value!.clearFiles();
  const file = files[0] as UploadRawFile;
  file.uid = genFileId();
  upload.value!.handleStart(file);
}

// 新增：文件变化时更新 filename
const handleFileChange: UploadProps["onChange"] = (file: UploadFile) => {
  filename.value = file.name;  // 更新文件名
};

const submitUpload = () => {
  upload.value!.submit();
}

const value = ref('');
const options = [
  {
    value: 'Option1',
    label: 'CNN',
  },
  {
    value: 'Option2',
    label: 'RNN',
  },
  {
    value: 'Option3',
    label: 'DCNN',
  },
  {
    value: 'Option4',
    label: 'SelfAttentionCNN',
  }
]
</script>

<style lang="css" scoped>
.train-container{
    width: 800px;
    height: 90vh;
    background-color: rgba(241, 245, 244, 0.613);
}
.header-container{
    width: 800px;
    height: 30px;
    text-align: center;
    background-color: aquamarine;
}
.btn-container{
     width: 100%;
     height: 50px;
     display: flex;
     justify-content: center;
     background-color: rgb(204, 196, 226);
}
.upload-btn{
    margin-right: 9.5%;
}
.select-btn1{
    margin-right: 10%;
}
.train-btn{
    margin-left: 10%;
}
</style>