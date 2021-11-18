package com.egil.pts.dao.common.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.Actionable;
import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.modal.MonthHolidays;
import com.egil.pts.modal.Pagination;
import com.egil.pts.modal.PtsHolidays;

public abstract class HibernateGenericDao<T, ID extends Serializable> implements GenericDao<T, ID> {
	protected Logger logger = LogManager.getLogger("PTSCRE");

	protected static int JDBC_BATCH_SIZE = 2;
	private Class<T> persistentClass;
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public HibernateGenericDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	@Autowired
	protected void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Class<T> getPersistentClass() {
		return persistentClass;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public T get(ID id) {
		T instance = (T) getSession().get(getPersistentClass(), id);
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public T getWithNoWaitLock(ID id) {
		LockOptions lockOptions = new LockOptions(LockMode.UPGRADE_NOWAIT);
		T instance = (T) getSession().get(getPersistentClass(), id, lockOptions);
		return instance;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public T get(T entity) {
		List<T> result = list(entity);

		if (result.size() == 1) {
			return result.get(0);
		} else {
			if (result.size() > 1) {
				logger.warn("Query returned multiple (" + result.size() + ") entries!");
			}
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public T getByName(String name) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		crit.add(Restrictions.eq("name", name));
		List<T> result = crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		if (result.size() == 1) {
			return result.get(0);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public T load(ID id, boolean lock) {
		T entity;
		if (lock)
			entity = (T) getSession().load(getPersistentClass(), id, LockOptions.UPGRADE);
		else
			entity = (T) getSession().load(getPersistentClass(), id);

		return entity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void lock(T item) {
		getSession().buildLockRequest(LockOptions.UPGRADE).lock(item);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void initialize(Object o) {
		if (!Hibernate.isInitialized(getPersistentClass().cast(o))) {
			Hibernate.initialize(getPersistentClass().cast(o));
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void initializeSet(Set<T> c) {
		if (!Hibernate.isInitialized(c.getClass())) {
			Hibernate.initialize(c);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public T merge(T entity) {
		return (T) getSession().merge(entity);

	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	@Override
	public int count() {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		return (Integer) crit.setProjection(Projections.rowCount()).uniqueResult();

	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<T> list(Criterion... criterion) {
		return findByCriteria(criterion);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<T> list(int from, int size, Criterion... criterion) {
		return findByCriteria(from, size, criterion);
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<T> list(T exampleInstance, String... excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		if (excludeProperty != null) {
			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
		}
		crit.add(example);
		return crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<T> listById(Set<ID> set) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		if (set != null) {
			crit.add(Restrictions.in("id", set));
		}
		return crit.list();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<T> listAndPerform(Actionable<T> action, T exampleInstance, String... excludeProperty) {
		List<T> affectedList = new ArrayList<T>();
		if (excludeProperty != null) {
			List<T> itemList = null;
			try {
				itemList = listAndLock(exampleInstance, excludeProperty);
			} catch (Throwable th) {
				// Ignore lock related exceptions
			}
			if (itemList != null) {
				for (T item : itemList) {
					if (action.doThis(item)) {
						save(item);
						affectedList.add(item);
					}
				}
			}
		}
		return affectedList;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public List<T> listAndPerformWithSize(Actionable<T> action, T exampleInstance, int size,
			String... excludeProperty) {
		List<T> affectedList = new ArrayList<T>();
		if (excludeProperty != null) {
			for (T item : listAndLockWithSize(exampleInstance, size, excludeProperty)) {
				if (action.doThis(item)) {
					save(item);
					affectedList.add(item);
				}
			}
		}
		return affectedList;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<T> listAndLock(T exampleInstance, String... excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		if (excludeProperty != null) {
			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
		}
		crit.add(example);
		return crit.setLockMode(LockMode.UPGRADE_NOWAIT).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<T> listAndLockWithSize(T exampleInstance, int size, String... excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		if (excludeProperty != null) {
			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
		}
		crit.add(example);
		return crit.setFirstResult(0).add(Restrictions.sqlRestriction("rownum <= " + size))
				.setLockMode(LockMode.PESSIMISTIC_WRITE).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public T save(T entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public T update(T entity) {
		getSession().update(entity);
		return entity;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void flush() {
		getSession().flush();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void commit() {
		getSession().getTransaction().commit();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void clear() {
		getSession().clear();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	protected List<T> findAndLockByCriteria(Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		if (criterion != null) {
			for (Criterion c : criterion) {
				crit.add(c);
			}
		}
		return crit.setLockMode(LockMode.PESSIMISTIC_WRITE).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	protected List<T> findByCriteria(Criterion... criterion) {

		Criteria crit = getSession().createCriteria(getPersistentClass());
		if (criterion != null) {
			for (Criterion c : criterion) {
				crit.add(c);
			}

		}
		return crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	protected List<T> findByCriteria(int from, int size, Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		if (criterion != null) {
			for (Criterion c : criterion) {
				crit.add(c);
			}
		}
		return crit.setFirstResult(from).setMaxResults(size).list();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void evict(T entity) {
		getSession().evict(entity);
	}

	/**
	 * @author chandrashekar.sudars This method set the Pagination for the Query
	 *         Object
	 * @param pagination
	 * @param query
	 */
	protected void setPaginationForQuery(Pagination pagination, Query query) {
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				query.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				query.setMaxResults(pagination.getSize());
		}
	}

	/**
	 * @author chandrashekar.sudars This method set the Pagination for the Criteria
	 *         Object
	 * @param pagination
	 * @param query
	 */
	protected void setPaginationForCriteria(Pagination pagination, Criteria criteria) {
		if (pagination != null) {
			if (pagination.getOffset() > 0)
				criteria.setFirstResult(pagination.getOffset());
			if (pagination.getSize() > 0)
				criteria.setMaxResults(pagination.getSize());
		}
	}

	@Override
	public void setFlushModeToCommit() {
		getSession().setFlushMode(FlushMode.COMMIT);
	}

	@Override
	public void setFlushModeToAuto() {
		getSession().setFlushMode(FlushMode.AUTO);
	}

	@Override
	public void setFlushModeToManual() {
		getSession().setFlushMode(FlushMode.MANUAL);
	}

	@SuppressWarnings("hiding")
	public <T> void refresh(T entity) {
		getSession().refresh(entity);
	}

	@SuppressWarnings("unchecked")
	public List<PtsHolidays> getEGIHolidayList() {
		Calendar c = Calendar.getInstance();
		int selectedYear = c.get(Calendar.YEAR);
		String finalQryStr = "SELECT H.id,L.NAME locationName,H.HOLIDAY_DATE date,H.HOLIDAY_DESC holiday,DAYNAME(H.HOLIDAY_DATE) day,L.COUNTRY from PTS_HOLIDAYS H, PTS_LOCATION L WHERE H.location_id=L.ID AND L.region='EGIL' AND H.YEAR="
				+ selectedYear + " ORDER BY locationName";

		Query query = getSession().createSQLQuery(finalQryStr).addScalar("id", new LongType())
				.addScalar("locationName", new StringType()).addScalar("date", new StringType())
				.addScalar("holiday", new StringType()).addScalar("day", new StringType());

		List<PtsHolidays> holidayList = query.setResultTransformer(Transformers.aliasToBean(PtsHolidays.class)).list();
		return holidayList;
	}

	@SuppressWarnings("unchecked")
	public List<PtsHolidays> getMANAHolidayList() {
		Calendar c = Calendar.getInstance();
		int selectedYear = c.get(Calendar.YEAR);
		String finalQryStr = "SELECT H.id,L.NAME locationName,H.HOLIDAY_DATE date,H.HOLIDAY_DESC holiday,DAYNAME(H.HOLIDAY_DATE) day,L.COUNTRY from PTS_HOLIDAYS H, PTS_LOCATION L WHERE H.location_id=L.ID AND L.region='MANA' AND H.YEAR="
				+ selectedYear + " ORDER BY locationName";

		Query query = getSession().createSQLQuery(finalQryStr).addScalar("id", new LongType())
				.addScalar("locationName", new StringType()).addScalar("date", new StringType())
				.addScalar("holiday", new StringType()).addScalar("day", new StringType());

		List<PtsHolidays> holidayList = query.setResultTransformer(Transformers.aliasToBean(PtsHolidays.class)).list();
		return holidayList;
	}

	@SuppressWarnings("unchecked")
	public List<MonthHolidays> getMonthHolidayCount(int year) {
		String qryStr = "select * from (select left(monthname(HOLIDAY_DATE), 3) month, location_id, count(*) count from PTS_HOLIDAYS "
				+ " where year=:year group by location_id, year,monthname(HOLIDAY_DATE)) tmp order by  location_id";
		Query query = getSession().createSQLQuery(qryStr).addScalar("month", new StringType())
				.addScalar("location_id", new LongType()).addScalar("count", new IntegerType());
		query.setInteger("year", year);
		List<MonthHolidays> holidayList = query.setResultTransformer(Transformers.aliasToBean(MonthHolidays.class))
				.list();
		return holidayList;
	}

}