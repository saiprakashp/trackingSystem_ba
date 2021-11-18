package com.egil.pts.dao.inventory;

import java.util.List;

import com.egil.pts.dao.common.GenericDao;
import com.egil.pts.dao.domain.UserWeekOff;

public interface UserWeekOffDao extends GenericDao<UserWeekOff, Long> {

	public void saveUserWeekOff(List<UserWeekOff> userWeekOff) throws Throwable;

	public UserWeekOff getUserWeekOff(Long userId, String weekendingDate) throws Throwable;

	public void delete(List<Long> removedIds);

	public List<Long> getUserWeekOff(String from,String to) throws Throwable;

}
