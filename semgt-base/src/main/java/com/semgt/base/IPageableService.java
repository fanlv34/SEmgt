package com.semgt.base;

import com.semgt.exception.SeException;

public interface IPageableService {
	Object qryPageableData(Object condition) throws SeException;

	int qryPagebleCount(Object condition) throws SeException;
}
