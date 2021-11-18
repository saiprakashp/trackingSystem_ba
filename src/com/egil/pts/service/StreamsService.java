package com.egil.pts.service;

import java.util.List;
import java.util.Map;

import com.egil.pts.dao.domain.Streams;

public interface StreamsService {
	
	public List<Streams> getStreams() throws Throwable;
	
	public String getJSONStringOfStreams() throws Throwable;
	
	public void getStreamsMap(Map<Long, String> streamsMap) throws Throwable;

	public void getJobDescrMap(Map<String, String> jobDescriptionMap) throws Throwable;
}
