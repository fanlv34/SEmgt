package com.semgt.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.semgt.model.Series;

@Repository("seriesDao")
public interface ISeriesDao {
	List qryAllSeries(Map condition);
	List qryAllSeriesPageable(Map condition);
	int qryAllSeriesPageableCount(Map condition);
	void insertSeries(Map series);
	int updateSeries(Map series);
	int delSeries(Map condition);
	void quickOperation(Map condition);
}