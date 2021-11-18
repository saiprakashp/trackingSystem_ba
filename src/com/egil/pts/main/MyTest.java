package com.egil.pts.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egil.pts.dao.common.DaoManager;
import com.egil.pts.modal.NetworkCodes;
import com.egil.pts.service.common.ServiceManager;
import com.egil.pts.service.impl.NetworkCodesServiceImpl;
import com.egil.pts.util.Decrypter;
import com.egil.pts.util.GenericExcel;

@Service("myTest")
public class MyTest {

	@Autowired(required = true)
	@Qualifier("daoManager")
	protected DaoManager daoManager;

	@Autowired(required = true)
	@Qualifier("serviceManager")
	protected ServiceManager serviceManager;

	public DaoManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DaoManager daoManager) {
		this.daoManager = daoManager;
	}

	private static Map<String, Integer> monthMap = new HashMap<>();
	static {
		monthMap.put("Jan", 0);
		monthMap.put("Feb", 1);
		monthMap.put("Mar", 2);
		monthMap.put("Apr", 3);
		monthMap.put("May", 4);
		monthMap.put("Jun", 5);
		monthMap.put("Jul", 6);
		monthMap.put("Aug", 7);
		monthMap.put("Sep", 8);
		monthMap.put("Oct", 9);
		monthMap.put("Nov", 10);
		monthMap.put("Dec", 11);
	}

	@Transactional
	public void testDao() {
		try {
			List<NetworkCodes> projectMap = new ArrayList<>();
			try {

				List<NetworkCodes> projectDatas = new ArrayList<>();
				try {
					GenericExcel excel = new GenericExcel(
							"C:/applications/tomcat/project/TFS to PTS Mapping 2142020.xlsx");
					String[][] excelContents = excel.readSheet(0);
					NetworkCodes nw = null;
					String[] excelHeaders = excelContents[0];
					for (int row = 1; row < excelContents.length; row++) {
						try {

							nw = new NetworkCodes();
							int col = 0;
							 
							for (String colStr : excelHeaders) {

								if (colStr.contains("TFS Epic")) {
									if (!excelContents[row][col + 17].equalsIgnoreCase("#N/A")) {
										Long.parseLong(excelContents[row][col]);
										nw.setTFSEpic(excelContents[row][col]);
									}
								}

								if (colStr.contains("Approved Total LOE")) {
									if (!excelContents[row][col + 17].equalsIgnoreCase("#N/A"))
										nw.setTotalLOE(Long.parseLong(excelContents[row][col + 1]));
								}

							}
							projectDatas.add(nw);
						} catch (Exception e) {
							continue;
						}
					}
					 
					  daoManager.getNetworkCodesDao().updateProjectTFData(projectDatas);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*
			 * List<Long> selectedAccountIds = new ArrayList<Long>();
			 * selectedAccountIds.add(1l); selectedAccountIds.add(2l);
			 * List<UserAcctPillarAppContribution> userAcctPillarAppContributionList = new
			 * LinkedList<UserAcctPillarAppContribution>();
			 * 
			 * List<UserPillarAppContribution> userPillarAppContributionList = new
			 * LinkedList<UserPillarAppContribution>(); List<UserAppContribution>
			 * userAppContributionList = null; List<UserContribution> userContributionList =
			 * daoManager.getProjectDao() .getProjectsByAccount(selectedAccountIds, 43l);
			 * 
			 * Long pillarId = 0l; Long accountId = 0l; UserAppContribution
			 * userAppContribution = null; Map<Long, List<UserAppContribution>> pilAppMap =
			 * new LinkedHashMap<Long, List<UserAppContribution>>();
			 * 
			 * Map<Long, String> acctIdNameMap = new LinkedHashMap<Long, String>();
			 * Map<Long, String> pillarIdNameMap = new LinkedHashMap<Long, String>();
			 * Map<Long, Float> pillarIdContributionMap = new LinkedHashMap<Long, Float>();
			 * UserPillarAppContribution userPillarAppContribution = null; Map<Long,
			 * List<Long>> acctPillarMap = new LinkedHashMap<Long, List<Long>>();
			 * 
			 * List<Long> pillarList = null;
			 * 
			 * for (UserContribution uc : userContributionList) {
			 * 
			 * if (accountId != uc.getAccountId()) { accountId = uc.getAccountId(); if
			 * (uc.getPillarId() != pillarId) {
			 * 
			 * if (acctPillarMap.containsKey(uc.getAccountId())) {
			 * acctPillarMap.get(uc.getAccountId()).add(uc.getPillarId()); } else {
			 * 
			 * pillarList = new ArrayList<Long>(); pillarList.add(uc.getPillarId());
			 * 
			 * acctPillarMap.put(uc.getAccountId(), pillarList);
			 * acctIdNameMap.put(uc.getAccountId(), uc.getAccountName()); }
			 * 
			 * pillarId = uc.getPillarId(); userAppContribution = new UserAppContribution();
			 * userAppContribution.setAppId(uc.getAppId());
			 * userAppContribution.setAppName(uc.getAppName());
			 * userAppContribution.setAppContribution(uc.getContribution());
			 * 
			 * if (pilAppMap.containsKey(pillarId)) {
			 * pilAppMap.get(pillarId).add(userAppContribution); } else {
			 * userAppContributionList = new ArrayList<UserAppContribution>();
			 * userAppContributionList.add(userAppContribution); }
			 * 
			 * pilAppMap.put(pillarId, userAppContributionList);
			 * pillarIdNameMap.put(pillarId, uc.getPillarName());
			 * pillarIdContributionMap.put(pillarId, uc.getContribution()); } else {
			 * userAppContribution = new UserAppContribution();
			 * userAppContribution.setAppId(uc.getAppId());
			 * userAppContribution.setAppName(uc.getAppName());
			 * userAppContribution.setAppContribution(uc.getContribution());
			 * 
			 * pilAppMap.get(uc.getPillarId()).add(userAppContribution);
			 * pillarIdContributionMap.put(uc.getPillarId(),
			 * (pillarIdContributionMap.get(uc.getPillarId()) + uc.getContribution())); } }
			 * else { if (uc.getPillarId() != pillarId) {
			 * 
			 * if (acctPillarMap.containsKey(uc.getAccountId())) {
			 * acctPillarMap.get(uc.getAccountId()).add(uc.getPillarId()); } else {
			 * pillarList = new ArrayList<Long>(); pillarList.add(uc.getPillarId());
			 * 
			 * acctPillarMap.put(uc.getAccountId(), pillarList);
			 * acctIdNameMap.put(uc.getAccountId(), uc.getAccountName()); }
			 * 
			 * pillarId = uc.getPillarId(); userAppContribution = new UserAppContribution();
			 * userAppContribution.setAppId(uc.getAppId());
			 * userAppContribution.setAppName(uc.getAppName());
			 * userAppContribution.setAppContribution(uc.getContribution());
			 * 
			 * if (pilAppMap.containsKey(pillarId)) {
			 * pilAppMap.get(pillarId).add(userAppContribution); } else {
			 * userAppContributionList = new ArrayList<UserAppContribution>();
			 * userAppContributionList.add(userAppContribution); }
			 * 
			 * pilAppMap.put(pillarId, userAppContributionList);
			 * pillarIdNameMap.put(pillarId, uc.getPillarName());
			 * pillarIdContributionMap.put(pillarId, uc.getContribution()); } else {
			 * userAppContribution = new UserAppContribution();
			 * userAppContribution.setAppId(uc.getAppId());
			 * userAppContribution.setAppName(uc.getAppName());
			 * userAppContribution.setAppContribution(uc.getContribution());
			 * 
			 * pilAppMap.get(uc.getPillarId()).add(userAppContribution);
			 * pillarIdContributionMap.put(uc.getPillarId(),
			 * (pillarIdContributionMap.get(uc.getPillarId()) + uc.getContribution())); } }
			 * 
			 * }
			 * 
			 * if (pillarIdNameMap != null) { for (Long acctId : acctPillarMap.keySet()) {
			 * UserAcctPillarAppContribution userAcctPillarAppContribution = new
			 * UserAcctPillarAppContribution(); userPillarAppContributionList = new
			 * LinkedList<UserPillarAppContribution>();
			 * userAcctPillarAppContribution.setAccountId(acctId);
			 * userAcctPillarAppContribution.setAccountName(acctIdNameMap.get(acctId)); for
			 * (Long pillId : acctPillarMap.get(acctId)) { userPillarAppContribution = new
			 * UserPillarAppContribution();
			 * 
			 * userPillarAppContribution.setPillarId(pillId);
			 * userPillarAppContribution.setPillarName(pillarIdNameMap.get(pillId));
			 * userPillarAppContribution.setPillarContribution(pillarIdContributionMap.get(
			 * pillId));
			 * 
			 * userPillarAppContributionList.add(userPillarAppContribution);
			 * 
			 * } userAcctPillarAppContributionList.add(userAcctPillarAppContribution); } }
			 */
		} catch (

		Throwable th) {
			th.printStackTrace();
		}

	}

	public static void main(String[] args) {
		//new MyTest().testDao();
		Decrypter d=	new Decrypter();
		d.setEncryptedPassword("QzF0UBGqzszCk8CCpeNSSQ==");
		//QzF0UBGqzszCk8CCpeNSSQ==
		d.decrypt();
		System.out.println(d.getDecryptedPassword());
		
	}
}
