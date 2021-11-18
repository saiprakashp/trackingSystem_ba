package com.egil.pts.modal;

import java.util.Comparator;

public class UserComparator implements Comparator<User> {

	private String sortType;

	private String sortField;

	public UserComparator(String sortType, String sortField) {
		this.sortType = sortType;
		this.sortField = sortField;
	}

	public int compare(User user1, User user2) {

		if (user1 == null || user1 == null) {
			return 0;
		}

		String type1 = "";
		String type2 = "";
		if (sortField.equalsIgnoreCase("projectName")) {
			type1 = user1.getProjectName();
			type2 = user2.getProjectName();
		} else if (sortField.equalsIgnoreCase("skillName")) {
			type1 = user1.getSkillName();
			type2 = user2.getSkillName();
		}

		if (type1 == null || type2 == null) {
			return 0;
		}

		if (sortType.equalsIgnoreCase("ASC")) {
			if (type1.compareTo(type2) > 0) {
				return 1;
			} else if (type1.compareTo(type2) < 0) {
				return -1;
			} else {
				return 0;
			}
		} else if (sortType.equalsIgnoreCase("DESC")) {
			if (type1.compareTo(type2) > 0) {
				return -1;
			} else if (type1.compareTo(type2) < 0) {
				return 1;
			} else {
				return 0;
			}
		}

		return 0;

	}
}