import { createApp } from 'vue';
import App from './App.vue';

import router  from './router';
import pinia from './store';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//引入echarts
import * as echarts from 'echarts';

import 'element-plus/dist/index.css';
import ElementPlus from 'element-plus'

const app = createApp(App);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.config.globalProperties.$echarts = echarts;
app.use(pinia);
app.use(ElementPlus);
app.use(router);
app.mount('#app');
