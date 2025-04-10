import { createApp } from 'vue';
import App from './App.vue';
import 'element-plus/dist/index.css';
import ElementPlus from 'element-plus'
import router  from './router';
import pinia from './store';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//引入echarts
import * as echarts from 'echarts';

const app = createApp(App);

// 全局挂载和注册 element-plus 的所有 icon
// app.config.globalProperties.$icons = []
// for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
//   app.config.globalProperties.$icons.push(key)  // 存储图标名称
//   app.component(key, component)  // 注册组件
// }

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.config.globalProperties.$echarts = echarts;
app.use(pinia);
app.use(ElementPlus);
app.use(router);
app.mount('#app');
