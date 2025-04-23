import type { TRAFFIC } from "../traffic/traffic";

export interface PAGE{
     pageNumber: number;     // 当前页
     pageSize: number;       // 每页数量
     total: number;         // 总记录数
     totalPages: number;     // 总页数
     list: TRAFFIC[];       // 数据列表
} 


export const initPage = (page: PAGE) => {
     page.list = [] as TRAFFIC[];
     page.pageNumber = 1;
     page.pageSize = 10;
     page.total = 0;
     page.totalPages = 0;
}