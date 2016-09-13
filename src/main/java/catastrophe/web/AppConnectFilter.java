package catastrophe.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "/*" })
public class AppConnectFilter implements Filter {

	private FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig config) throws ServletException {

		filterConfig = config;

	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		// This would only happen if a thread is processing a request as the
		// servlet is being
		// stopped. As this is not a valid filter anymore, the chain can't be
		// trusted either,
		// so do nothing (don't progress the chain, either)
		if (filterConfig == null) {
			return;
		}

		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;

		// Allow rest to go through without interceptoion
		String pathInfo = httpReq.getPathInfo();

		// Only redirect html requests
		// Many requests will have a ?from with a URL, so that makes excluding
		// tricker
		// If this is to a web page and isn't an in-process signin

		if (pathInfo != null && (pathInfo.equals("/")
				|| pathInfo.contains(".html") && !pathInfo.contains("?from") && !pathInfo.contains("/signin.html"))) {

			// Only check sessions for html pages

			boolean signedIn = httpReq.getSession().getAttribute("cat.user") != null;

			if (!signedIn) {

				httpResp.sendRedirect(
						httpReq.getContextPath() + "/signin.html" + "?from=" + httpReq.getContextPath() + pathInfo);
			} else {
				chain.doFilter(req, resp);
			}

		} else {
			chain.doFilter(req, resp);

		}
	}
}
