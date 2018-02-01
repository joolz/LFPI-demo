/*
 * Copyright (C) 2016 Open Universiteit Nederland
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.portal.context.LiferayPortletHelperUtil;
import com.liferay.faces.util.context.FacesContextHelperUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.TeamLocalServiceUtil;

@ManagedBean
@ViewScoped
public class AuthorBaseBean implements Serializable {
	private static final long serialVersionUID = 42L;

	private static final Log LOG = LogFactoryUtil.getLog(AuthorBaseBean.class);
	protected static final String REDIRECT_POSTFIX = "?faces-redirect=true&fu=bar";

	protected int publicationMoment = Constants.PUBLICATION_MOMENT_GRACE;
	protected List<SelectItem> publicationMomentOptions;

	protected String expirationMoment = Constants.EXPIRATION_MOMENT_NONE;
	protected List<SelectItem> expirationMomentOptions;

	protected transient List<SelectItem> selectableTeams;
	protected transient List<String> selectedTeams = new ArrayList<String>();
	protected transient List<SelectItem> selectableRoles;
	protected transient List<SelectItem> availableRoles;
	protected transient List<String> selectedRoles = new ArrayList<String>();
	protected transient List<SelectItem> selectableCategories;
	protected transient boolean selectAllStudentsTutors;
	protected transient String selectedOption;
	protected transient List<SelectItem> filterOptions;

	public List<SelectItem> getAvailableRoles() {
		if (availableRoles == null) {
			try {
				availableRoles = new ArrayList<SelectItem>();
				Locale locale = LiferayPortletHelperUtil.getThemeDisplay().getLocale();
				for (final Role role : RoleLocalServiceUtil.getRoles(LiferayPortletHelperUtil.getCompanyId())) {
					// do not include teamroles
					if (!role.getTitle(locale).startsWith(Constants.TEAM_PREFIX)) {
						final SelectItem item = new SelectItem(role.getRoleId(), role.getTitle(locale));
						availableRoles.add(item);
					}
				}
			} catch (final SystemException e) {
				LOG.error(e);
			}
		}
		return availableRoles;
	}

	public List<String> getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(final List<String> selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public int getPublicationMoment() {
		return publicationMoment;
	}

	public void setPublicationMoment(final int publicationMoment) {
	}

	public List<SelectItem> getPublicationMomentOptions() {
		if (publicationMomentOptions == null) {
			final Locale locale = LiferayPortletHelperUtil.getThemeDisplay().getLocale();

			publicationMomentOptions = new ArrayList<SelectItem>(2);
			publicationMomentOptions.add(new SelectItem(Constants.PUBLICATION_MOMENT_IMMEDIATE,
					LanguageUtil.get(locale, "publicationmoment-immediate")));
			publicationMomentOptions.add(new SelectItem(Constants.PUBLICATION_MOMENT_GRACE, LanguageUtil.format(locale,
					"publicationmoment-grace-x", new Object[] { Constants.ANNOUNCEMENT_GRACE_PERIOD })));
			publicationMomentOptions.add(new SelectItem(Constants.PUBLICATION_MOMENT_DATE,
					LanguageUtil.get(locale, "publicationmoment-date")));
		}
		return publicationMomentOptions;
	}

	public String getExpirationMoment() {
		return expirationMoment;
	}

	public void setExpirationMoment(final String expirationMoment) {
		this.expirationMoment = expirationMoment;
	}

	public List<SelectItem> getExpirationMomentOptions() {
		if (expirationMomentOptions == null) {
			final Locale locale = LiferayPortletHelperUtil.getThemeDisplay().getLocale();

			expirationMomentOptions = new ArrayList<SelectItem>(2);
			expirationMomentOptions.add(new SelectItem(Constants.EXPIRATION_MOMENT_NONE,
					LanguageUtil.get(locale, "expirationmoment-none")));
			expirationMomentOptions.add(new SelectItem(Constants.EXPIRATION_MOMENT_DATE,
					LanguageUtil.get(locale, "expirationmoment-date")));
		}
		return expirationMomentOptions;
	}

	public List<SelectItem> getSelectableRoles() {
		if (selectableRoles == null) {
			selectableRoles = new ArrayList<SelectItem>();
			final Locale locale = LiferayPortletHelperUtil.getThemeDisplay().getLocale();
			for (Role role : RoleLocalServiceUtil.getRoles(0, 5)) {
				selectableRoles.add(new SelectItem(role.getRoleId(),
						LanguageUtil.format(locale, "send-to-global-roles-label", role.getTitle(locale))));
			}
		}
		return selectableRoles;
	}

	public List<SelectItem> getSelectableTeams() {
		if (selectableTeams == null) {
			selectableTeams = new ArrayList<SelectItem>();
			for (final Team team : TeamLocalServiceUtil.getGroupTeams(LiferayPortletHelperUtil.getScopeGroupId())) {
				try {
					selectableTeams.add(new SelectItem(team.getRole().getRoleId(), team.getName()));
				} catch (PortalException e) {
					e.printStackTrace();
				}
			}
		}
		return selectableTeams;

	}

	public List<String> getSelectedTeams() {
		return selectedTeams;
	}

	public void setSelectedTeams(final List<String> selectedTeams) {
		this.selectedTeams = selectedTeams;
	}

	public void validate(final ComponentSystemEvent event) {
		final Date now = new Date();
		final UIComponent form = event.getComponent();
		String msg = null;
		String clientId = null;

		final UIInput pmComponent = (UIInput) form.findComponent("pubmoment");
		final UIInput pdComponent = (UIInput) form.findComponent("pubdate");
		final UIInput edComponent = (UIInput) form.findComponent("expdate");

		final Integer pmValue = pmComponent == null ? null : (Integer) pmComponent.getValue();
		final Date pdValue = pdComponent == null ? null : (Date) pdComponent.getValue();
		final Date edValue = pdComponent == null ? null : (Date) edComponent.getValue();

		if (pmValue == Constants.PUBLICATION_MOMENT_DATE && pdValue == null) {
			msg = "publication-date-has-no-value";
			pdComponent.setValid(false);
			clientId = pdComponent.getClientId();
		} else if (pdValue != null && pdValue.before(now)) {
			msg = "publication-date-in-past";
			pdComponent.setValid(false);
			clientId = pdComponent.getClientId();
		} else if (edValue != null && edValue.before(now)) {
			msg = "expiration-date-in-past";
			edComponent.setValid(false);
			clientId = edComponent.getClientId();
		} else if (pdValue != null && edValue != null && edValue.before(pdValue)) {
			msg = "expiration-date-before-publication-date";
			pdComponent.setValid(false);
			clientId = pdComponent.getClientId();
		}

		if (msg != null) {
			LOG.debug("Validation failed " + msg);
			final FacesMessage facesMessage = new FacesMessage(
					LanguageUtil.get(LiferayPortletHelperUtil.getThemeDisplay().getLocale(), msg));
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContextHelperUtil.addMessage(clientId, FacesMessage.SEVERITY_ERROR, facesMessage.getSummary());
			final LiferayFacesContext lfc = LiferayFacesContext.getInstance();
			lfc.renderResponse();
		}

	}

	public void validateRoles(final ComponentSystemEvent event) {
		final UIComponent form = event.getComponent();
		String msg = null;
		String clientId = null;

		final UIInput rolesComponent = (UIInput) form.findComponent("roles");
		final UIInput teamsComponent = (UIInput) form.findComponent("teams");
		final UIInput comboBoxComponent = (UIInput) form.findComponent("som_options");

		@SuppressWarnings("unchecked")
		final List<String> rolesValue = rolesComponent == null ? null : (List<String>) rolesComponent.getValue();
		@SuppressWarnings("unchecked")
		final List<String> teamsValue = teamsComponent == null ? null : (List<String>) teamsComponent.getValue();

		if ((selectedOption == null || selectedOption.isEmpty())
				|| (selectedOption.equals(Constants.OPTION_ROLES) && rolesValue.isEmpty())
				|| (selectedOption.equals(Constants.OPTION_TEAMS) && teamsValue.isEmpty())) {
			msg = "no-role-or-team-selected";
			if (selectedOption.equals(Constants.OPTION_ROLES) && getSelectableRoles().size() > 0) {
				// set focus on selectable roles
				rolesComponent.setValid(false);
				clientId = rolesComponent.getClientId();
			} else if (selectedOption.equals(Constants.OPTION_TEAMS) && getSelectableTeams().size() > 0) {
				// set focus on teams
				teamsComponent.setValid(false);
				clientId = teamsComponent.getClientId();
			} else if (selectedOption.isEmpty()) {
				// set focus on combobox select option
				msg = "no-role-or-team-selectable";
				comboBoxComponent.setValid(false);
				clientId = comboBoxComponent.getClientId();
			} else {
				// NOOP
				LOG.debug("No selectable option");
				msg = "no-role-or-team-selectable";
			}
		}

		if (msg != null) {
			LOG.debug("Validation failed " + msg);
			if ("no-addressee-selected".equals(msg)) {
				FacesContextHelperUtil.addGlobalErrorMessage(msg);
			} else {
				final FacesMessage facesMessage = new FacesMessage(
						LanguageUtil.get(LiferayPortletHelperUtil.getThemeDisplay().getLocale(), msg));
				facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContextHelperUtil.addMessage(clientId, FacesMessage.SEVERITY_ERROR, facesMessage.getSummary());
				final LiferayFacesContext lfc = LiferayFacesContext.getInstance();
				lfc.renderResponse();
			}
		}

	}

	public String getGraceMessage() {
		return LanguageUtil.format(LiferayPortletHelperUtil.getThemeDisplay().getLocale(), "grace-message",
				Constants.ANNOUNCEMENT_GRACE_PERIOD);
	}

	public List<SelectItem> getSelectableCategories() {
		if (selectableCategories == null) {
			selectableCategories = new ArrayList<SelectItem>();
			final SelectItem item = new SelectItem("some category", "some category");
			selectableCategories.add(item);
		}
		return selectableCategories;
	}

	public String getSelectedOption() {
		if (selectedOption == null || selectedOption.isEmpty()) {
			if (getSelectableTeams().size() > 0) {
				selectedOption = Constants.OPTION_TEAMS;
			} else if (getSelectableRoles().size() > 0) {
				selectedOption = Constants.OPTION_ROLES;
			} else {
				selectedOption = "";
			}
		}
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public boolean isSelectAllStudentsTutors() {
		return selectAllStudentsTutors;
	}

	public void setSelectAllStudentsTutors(boolean selectAllStudentsTutors) {
		this.selectAllStudentsTutors = selectAllStudentsTutors;
	}

	public List<SelectItem> getFilterOptions() {
		if (filterOptions == null) {
			Locale locale = LiferayPortletHelperUtil.getThemeDisplay().getLocale();
			filterOptions = new ArrayList<SelectItem>();
			filterOptions.add(
					new SelectItem(Constants.OPTION_STUDENTS_TUTORS, LanguageUtil.get(locale, "send-to-all-teams")));
			filterOptions.add(new SelectItem(Constants.OPTION_TEAMS, LanguageUtil.get(locale, "send-to-team")));
			filterOptions.add(new SelectItem(Constants.OPTION_ROLES, LanguageUtil.get(locale, "send-to-roles")));
		}
		return filterOptions;
	}

}
