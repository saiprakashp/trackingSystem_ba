package com.egil.pts.dao.inventory.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.hibernate.HibernateGenericDao;
import com.egil.pts.dao.domain.JobDesctription;
import com.egil.pts.dao.domain.Streams;
import com.egil.pts.dao.inventory.StreamsDao;

@Repository("streamsDao")
public class HibernateStreamsDao extends HibernateGenericDao<Streams, Long>
		implements StreamsDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Streams> getStreams() {
		Query q = getSession().createQuery("from Streams");
		return q.list();
	}

	@Override
	public List<JobDesctription> getJobDesctriptions() {
		Query q = getSession().createQuery("from JobDesctription");
		return q.list();
	}


}
