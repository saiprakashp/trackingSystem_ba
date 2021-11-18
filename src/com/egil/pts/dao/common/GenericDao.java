package com.egil.pts.dao.common;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Criterion;

import com.egil.pts.dao.domain.UserWeekOff;
import com.egil.pts.modal.MonthHolidays;
import com.egil.pts.modal.PtsHolidays;

public interface GenericDao<T, ID extends Serializable> {

	T get(ID id);

	T get(T entity);

	T load(ID id, boolean lock);

	void lock(T item);

	T merge(T entity);

	void initialize(Object o);

	int count();

	List<T> listById(Set<ID> set);

	List<T> list(Criterion... criterion);

	List<T> list(int from, int size, Criterion... criterion);

	List<T> list(T exampleInstance, String... excludeProperty);

	List<T> listAndPerform(Actionable<T> doThis, T exampleInstance, String... excludeProperty);

	List<T> listAndPerformWithSize(Actionable<T> doThis, T exampleInstance, int size, String... excludeProperty);

	List<T> listAndLock(T exampleInstance, String... excludeProperty);

	List<T> listAndLockWithSize(T exampleInstance, int size, String... excludeProperty);

	T save(T entity);

	T update(T entity);

	void delete(T entity);

	void flush();

	void commit();

	void clear();

	void initializeSet(Set<T> c);

	T getByName(String name);

	void evict(T entity);

	void setFlushModeToCommit();

	void setFlushModeToAuto();

	void setFlushModeToManual();

	@SuppressWarnings("hiding")
	public <T> void refresh(T entity);

	T getWithNoWaitLock(ID id);

	public List<PtsHolidays> getEGIHolidayList();

	public List<PtsHolidays> getMANAHolidayList();

	public List<MonthHolidays> getMonthHolidayCount(int year);

	

}