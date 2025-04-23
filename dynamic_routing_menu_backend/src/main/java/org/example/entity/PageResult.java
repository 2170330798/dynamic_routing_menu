package org.example.entity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class PageResult<T> {
    // getter和setter方法
    private  long pageNumber;     // 当前页
    private  long pageSize;       // 每页数量
    private  long total;         // 总记录数
    private  long totalPages;     // 总页数
    private final List<T> list;       // 数据列表

    public PageResult() {
        this.list = new ArrayList<>();
    }

    public PageResult(long pageNumber, long pageSize, long total, long totalPages, List<T> records) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPages = totalPages;
        this.list = records != null ? records : new ArrayList<>();
    }
}