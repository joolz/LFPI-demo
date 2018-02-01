package nl.ou.dlwo.test;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

@ManagedBean
@ViewScoped
public class AuthorAddBean extends AuthorBaseBean implements Serializable {
	private static final long serialVersionUID = 42L;
	private static final Log LOG = LogFactoryUtil.getLog(AuthorAddBean.class);

	@PostConstruct
	private void init() {
	}

	public String doAdd() {
		LOG.debug("Add announcement");
		String result = "add";

		return result + REDIRECT_POSTFIX;
	}

}
