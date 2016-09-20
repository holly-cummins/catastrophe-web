package catastrophe.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import catastrophe.cats.Score;
import catastrophe.discovery.ServiceFinder;

@Path("cat")
public class RestCatStuff {

	private static final String CAT_PATH = "/rest/cats";
	private static final String USERS_PATH = "/rest/users";
	private static final String SCORING_PATH = "/rest/scoring";

	Client client = ClientBuilder.newClient();

	@PUT
	@Path("score")
	@Produces(MediaType.APPLICATION_JSON)
	public Score score(@QueryParam("encodedImage") String encodedImage, @Context HttpServletRequest request) {
		// Get user from session
		String userName = (String) request.getSession().getAttribute("cat.user");
		System.out.println(userName + " put in a guess of " + encodedImage);

		String host = new ServiceFinder().getHostAndPort(CAT_PATH);
		if (host != null) {

			String scoreHost = new ServiceFinder().getHostAndPort(SCORING_PATH);
			String scorePath = SCORING_PATH + "/score/";
			System.out.println("Requesting " + scoreHost + scorePath);
			WebTarget scoreTarget = client.target("http://" + scoreHost).path(scorePath).queryParam("encodedImage",
					encodedImage);
			Score score = scoreTarget.request(MediaType.APPLICATION_JSON).get(Score.class);

			System.out.println("Going to update " + userName + " with a score of " + score + ".");
			String usersHost = new ServiceFinder().getHostAndPort(USERS_PATH);
			String updateScorePath = USERS_PATH + "/updateScore/";
			System.out.println("Requesting " + usersHost + updateScorePath);
			WebTarget authTarget = client.target("http://" + usersHost).path(updateScorePath)
					.queryParam("userName", userName).queryParam("score", score.getScore())
					.queryParam("image", encodedImage);

			Response response = authTarget.request(MediaType.APPLICATION_JSON).post(null);
			System.out.println("Score update response is " + response.getStatus());

			return score;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@GET
	@Path("scores")
	@Produces(MediaType.APPLICATION_JSON)
	public List getLeaderboard() {

		String host = new ServiceFinder().getHostAndPort(USERS_PATH);
		if (host != null) {
			String authPath = USERS_PATH + "/leaderboard";
			System.out.println("Requesting " + authPath);
			WebTarget target = client.target("http://" + host).path(authPath);
			List response = target.request(MediaType.APPLICATION_JSON).get(new GenericType<List>(List.class));

			return response;
		} else {
			System.out.println("No provider for service " + USERS_PATH);
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@GET
	@Path("cats")
	@Produces(MediaType.APPLICATION_JSON)
	public Set getCats(@Context HttpServletRequest request) {
		// Get user from session
		String userName = (String) request.getSession().getAttribute("cat.user");
		String host = new ServiceFinder().getHostAndPort(USERS_PATH);
		if (host != null) {
			WebTarget target = client.target("http://" + host).path(USERS_PATH + "/" + userName);
			Set response = target.request(MediaType.APPLICATION_JSON).get(new GenericType<Set>(Set.class));

			return response;
		} else {
			System.out.println("No provider for service " + CAT_PATH);
			return null;
		}

	}

}
