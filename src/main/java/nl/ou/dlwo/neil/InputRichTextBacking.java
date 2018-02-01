package nl.ou.dlwo.neil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

public class InputRichTextBacking {
	private static final Logger logger = LoggerFactory.getLogger(InputRichTextBacking.class);

	private Applicant applicant;
	private boolean resizable = true;

	public Applicant getApplicant() {
		return applicant;
	}

	@PostConstruct
	public void init() {

		applicant = new Applicant();

		/* if (ViewParamUtil.getUsage().equals("default-value")) { */
		applicant.setComments("<p>This is some <strong>bold</strong> text\nand this is some <em>italic</em> text.</p>");
		/* } */
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public void submit() {
		logger.info("You entered comments: " + applicant.getComments());
	}

	public void valueChangeListener(ValueChangeEvent valueChangeEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("valueChangeListener: phaseId=[{0}]", phaseId.toString());

		String phaseName = phaseId.toString();
		FacesMessage facesMessage = new FacesMessage(
				"The valueChangeListener method was called during the " + phaseName + " phase of the JSF lifecycle.");
		facesContext.addMessage(null, facesMessage);
	}
}
