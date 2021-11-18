package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.JobDesctription;
import com.egil.pts.dao.domain.Streams;

public interface StreamsDao extends GenericDao<Streams, Long>{

	public List<Streams> getStreams();

	public List<JobDesctription> getJobDesctriptions();
}
