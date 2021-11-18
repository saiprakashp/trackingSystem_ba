package com.egil.pts.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.egil.pts.dao.domain.JobDesctription;
import com.egil.pts.dao.domain.Streams;
import com.egil.pts.service.StreamsService;
import com.egil.pts.service.common.BaseUIService;

@Service("streamsService")
public class StreamsServiceImpl extends BaseUIService implements StreamsService {

	@Override
	public List<Streams> getStreams() throws Throwable {
		return daoManager.getStreamsDao().getStreams();
	}

	public List<JobDesctription> getJobDesctriptions() throws Throwable {
		return daoManager.getStreamsDao().getJobDesctriptions();
	}

	@Override
	public String getJSONStringOfStreams() throws Throwable {
		String returnValue = "";
		returnValue = -1 + ":" + "Please Select" + ";";
		for (Streams stream : getStreams()) {
			returnValue = returnValue + stream.getId() + ":" + stream.getStreamName()
					+ ";";
		}
		return returnValue.substring(0, returnValue.length() - 1);
	}

	@Override
	public void getStreamsMap(Map<Long, String> streamsMap) throws Throwable {
		for (Streams stream : getStreams()) {
			streamsMap.put(stream.getId(), stream.getStreamName());
		}
	}

	@Override
	public void getJobDescrMap(Map<String, String> jobDescriptionMap) throws Throwable {
		for (JobDesctription stream : getJobDesctriptions()) {
			jobDescriptionMap.put(stream.getJobName(), stream.getJobName());
		}
	}
}
