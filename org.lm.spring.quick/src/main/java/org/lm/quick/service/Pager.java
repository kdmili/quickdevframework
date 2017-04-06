package org.lm.quick.service;

import java.util.Collection;

public class Pager {
	private int pageSize;
	private int page;
	private int totalPage;
	private int recordCount;

	private Collection<?> data;

	public Pager() {
		this.page = 0;
		this.pageSize = 10;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public Collection<?> getData() {
		return data;
	}

	public void setData(Collection<?> data) {
		this.data = data;
	}

}
