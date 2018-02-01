/*
 * Copyright (C) 2017 Open Universiteit Nederland
 *
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */

package nl.ou.dlwo.test;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jal
 *
 */
public class Constants {

	/**
	 * Hide constructor, this class should not be instantiated.
	 */
	private Constants() {

	}

	public static final List<Integer> ANNOUNCEMENT_CATEGORIES = new ArrayList<Integer>(Arrays.asList(0, 1));
	public static final int ANNOUNCEMENT_CATEGORY_GENERAL = 0;
	public static final int ANNOUNCEMENT_CATEGORY_SERVICE = 1;

	public static final int ANNOUNCEMENT_TEXT_MAX_LENGTH = GetterUtil.getInteger(
			PropsUtil.get("announcement.max.key.length"), 100);

	// grace period, default 15 minutes
	public static final int ANNOUNCEMENT_GRACE_PERIOD = GetterUtil.getInteger(
			PropsUtil.get("announcement.grace.period.in.minutes"), 15);

	public static final String FRIENDLY_URL_ANNOUNCEMENTS_VIEWER = "/-/announcements/";

	public static final int PUBLICATION_MOMENT_UNDEFINED = 0;
	public static final int PUBLICATION_MOMENT_IMMEDIATE = 1;
	public static final int PUBLICATION_MOMENT_GRACE = 2;
	public static final int PUBLICATION_MOMENT_DATE = 3;

	public static final String EXPIRATION_MOMENT_NONE = "none";
	public static final String EXPIRATION_MOMENT_DATE = "date";

	public static final String TEAM_PREFIX = "__";

	private static final String WAR = "_WAR_nloudlwoannouncementsportlet";
	public static final String AUTHOR_PORTLET = "nl_ou_dlwo_announcements_author" + WAR;
	
	public static final String CUD_ANNOUNCEMENT_ACTION = "CUD_ANNOUNCEMENTS";
	
	public static final String OPTION_STUDENTS_TUTORS = "all";
	public static final String OPTION_TEAMS = "teams";
	public static final String OPTION_ROLES = "roles";
	
	public static final int SIGNIFICANCE_LEVEL_INITIAL_VERSION = 0;
	public static final int SIGNIFICANCE_LEVEL_MINOR_VERSION = 1;
	public static final int SIGNIFICANCE_LEVEL_MAJOR_VERSION = 9;

}
