package com.pac4j.client;

import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.redirect.RedirectAction;
import org.pac4j.core.util.CommonHelper;

/**
 * 
 * 类功能说明
 * IndirectClient的getRedirectAction方法会报401错误，在这个类里重写getRedirectAction方法，并屏蔽掉异常代码
 * 
 * @see org.pac4j.core.client.IndirectClient#getRedirectAction(org.pac4j.core.context.WebContext)
 *      <p>
 *      Title: MyCasClient.java
 *      </p>
 * @author hxm
 * @date 2018年2月1日 上午10:58:27 类修改者 修改日期 修改说明
 * @version V1.0
 */
public class MyCasClient extends CasClient {

	public MyCasClient() {
		super();
	}

	public MyCasClient(final CasConfiguration configuration) {
		super(configuration);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.pac4j.core.client.IndirectClient#getRedirectAction(org.pac4j.core.context
	 * .WebContext)
	 */
	@Override
	public RedirectAction getRedirectAction(WebContext context) throws HttpAction {
		init(context);
		// it's an AJAX request -> unauthorized (with redirection url in header)
		if (getAjaxRequestResolver().isAjax(context)) {
			logger.info("AJAX request detected -> returning 401");
			RedirectAction action = getRedirectActionBuilder().redirect(context);
			cleanRequestedUrl(context);
			throw HttpAction.unauthorized("AJAX request -> 401", context, null, action.getLocation());
		} else {
			final String attemptedAuth = (String) context.getSessionStore().get(context,
					getName() + ATTEMPTED_AUTHENTICATION_SUFFIX);
			if (CommonHelper.isNotBlank(attemptedAuth)) {
				cleanAttemptedAuthentication(context);
				cleanRequestedUrl(context);
				// 这里按自己需求处理，默认是返回了401，我在这边改为跳转到cas登录页面
				// throw HttpAction.unauthorized(context);
				return getRedirectActionBuilder().redirect(context);
			} else {
				return getRedirectActionBuilder().redirect(context);
			}
		}
		// authentication has already been tried -> unauthorized
		// FIXME 以下这段代码在org.pac4j.cas.client.CasClient中会出现401错误，所以在这里屏蔽掉。以后寻求更好的解决办法。
		// final String attemptedAuth = (String) context.getSessionAttribute(getName() +
		// ATTEMPTED_AUTHENTICATION_SUFFIX);
		// if (CommonHelper.isNotBlank(attemptedAuth)) {
		// cleanAttemptedAuthentication(context);
		// cleanRequestedUrl(context);
		// throw HttpAction.unauthorized("authentication already tried -> forbidden",
		// context, null, null);
		// }

		// return getRedirectActionBuilder().redirect(context);
	}

	private void cleanAttemptedAuthentication(WebContext context) {
		SessionStore<WebContext> sessionStore = context.getSessionStore();
		if (sessionStore.get(context, this.getName() + ATTEMPTED_AUTHENTICATION_SUFFIX) != null) {
			sessionStore.set(context, this.getName() + ATTEMPTED_AUTHENTICATION_SUFFIX, "");
		}

	}

	private void cleanRequestedUrl(final WebContext context) {
		context.setSessionAttribute(Pac4jConstants.REQUESTED_URL, "");
		SessionStore<WebContext> sessionStore = context.getSessionStore();
		if (sessionStore.get(context, Pac4jConstants.REQUESTED_URL) != null) {
			sessionStore.set(context, Pac4jConstants.REQUESTED_URL, "");
		}
	}

}
