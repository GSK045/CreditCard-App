package com.sm.card_app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PageBean<T> {

	private int pageNo;
	private int pageSize;
	private boolean last;
	private boolean first;
	private int totalPages;
	private Long totalRecords;
	private T records;

}
