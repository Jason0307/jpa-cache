package org.zhubao.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SimplePageableImpl implements Pageable {
	
	private int pageNumber;
	private int pageSize;
	
	public static Pageable getInstance(int pageNumber) {
		return new SimplePageableImpl(pageNumber);
	}
	
	/**
	 * @param pageNumber2
	 * @param pagesize
	 * @return
	 */
	public static Pageable getInstance(int pageNumber, int pagesize) {
		return new SimplePageableImpl(pageNumber,pagesize);
	}
	
	private SimplePageableImpl(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	private SimplePageableImpl(int pageNumber,int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	@Override
	public int getPageNumber() {
		return this.pageNumber;
	}

	@Override
	public int getPageSize() {
		if(0 < pageSize){
			return pageSize;
		}
		return Constants.DEFAULT_PAGE_SIZE;
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public Sort getSort() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#first()
	 */
	@Override
	public Pageable first() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#hasPrevious()
	 */
	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#next()
	 */
	@Override
	public Pageable next() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#previousOrFirst()
	 */
	@Override
	public Pageable previousOrFirst() {
		// TODO Auto-generated method stub
		return null;
	}

}
