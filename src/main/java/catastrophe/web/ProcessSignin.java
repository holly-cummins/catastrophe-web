package catastrophe.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/processSignin")
public class ProcessSignin extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		String from = req.getParameter("from");
		String user = req.getParameter("userName");

		HttpSession session = req.getSession(true);
		session.setAttribute("cat.user", user);
		System.out.println("Signed in " + user);

		resp.sendRedirect(from);

	}
}
