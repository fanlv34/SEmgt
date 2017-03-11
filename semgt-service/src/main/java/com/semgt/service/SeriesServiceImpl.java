package com.semgt.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.semgt.base.IPageableService;
import com.semgt.dao.ISeriesDao;
import com.semgt.exception.SeException;

@Service("seriesService")
public class SeriesServiceImpl implements ISeriesService, IPageableService {
	@Resource(name = "seriesDao")
	private ISeriesDao seriesDao;

	public List qrySeries(Map condition) {
		return this.seriesDao.qryAllSeries(condition);
	}

	public void addSeries(Map series) {
		this.seriesDao.insertSeries(series);
	}

	public void updSeries(Map series) {
		this.seriesDao.updateSeries(series);
	}

	public void delSeries(Map condition) {
		this.seriesDao.delSeries(condition);
	}
	
	public void quickOperation(Map condition) {
		this.seriesDao.quickOperation(condition);
	}

	@Override
	public Object qryPageableData(Object condition) throws SeException {
		return this.seriesDao.qryAllSeriesPageable((Map) condition);
	}

	@Override
	public int qryPagebleCount(Object condition) throws SeException {
		return this.seriesDao.qryAllSeriesPageableCount((Map) condition);
	}

}
