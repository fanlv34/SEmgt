package com.semgt.service;

import java.util.List;
import java.util.Map;

import com.semgt.model.Series;

public interface ISeriesService {
	List qrySeries(Map condition);

	void addSeries(Map series);

	void updSeries(Map series);

	void delSeries(Map condition);

	void quickOperation(Map condition);
}
