package com.example.ShoppApp.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class PageResponseAbstract implements Serializable {
    public int pageNo;
    public int pageSize;
    public long totalPages;
    public long totalElements;
}
