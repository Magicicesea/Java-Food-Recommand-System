package rpc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.MySQLConnection;
import entity.Item;
import external.YelpApi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//		response.setContentType("text/html");
//		
//		PrintWriter out = response.getWriter();
//		out.println("<html><body>");
//		out.println("<h1>Hello World</h1>");
//		out.println("</body></html>");
//		
//		out.close();
//		response.setContentType("text/html");
//		
//		PrintWriter out = response.getWriter();
//		
//		if (request.getParameter("username") != null) {
//			String username = request.getParameter("username");
//			
//			out.println("<html><body>");
//			out.println("<h1>Hello " + username + "</h1>");
//			out.println("</body></html>");	
//		}
//		
//		out.close();
//		response.setContentType("application/json");
//		
//		PrintWriter out = response.getWriter();
//		
//		if (request.getParameter("username") != null) {
//			String username = request.getParameter("username");
//			
//			JSONObject obj = new JSONObject();
//			
//			try {
//				obj.put("username", username);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			
//			out.print(obj);
//		}		
//		
//		out.close();
//		response.setContentType("application/json");
//		PrintWriter out = response.getWriter();
//		JSONArray array = new JSONArray();
//
//		try {
//			array.put(new JSONObject().put("username", "abcd"));
//			array.put(new JSONObject().put("username", "1234"));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		out.print(array);
//		out.close();
//		double lat = Double.parseDouble(request.getParameter("lat"));
//		double lon = Double.parseDouble(request.getParameter("lon"));
//		
//		YelpApi yelpAPI = new YelpApi();
//		List<Item> items = yelpAPI.search(lat, lon, null);
//		
//		// DB operations
//		
//		JSONArray array = new JSONArray();
//		for (Item item : items) {
//			array.put(item.toJSONObject());
//		}
//		RpcHelper.writeJsonArray(response, array);
		// allow access only if session exists
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
		
		
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		String userId = request.getParameter("user_id");
		// Term can be empty or null.
		String term = request.getParameter("term");
		MySQLConnection connection = new MySQLConnection();
		try {
			List<Item> items = connection.searchItems(lat, lon, term);
			Set<String> favoriteItemIds = connection.getFavoriteItemIds(userId);

			JSONArray array = new JSONArray();
			for (Item item : items) {
				JSONObject obj = item.toJSONObject();
				obj.put("favorite", favoriteItemIds.contains(item.getItemId()));
				array.put(obj);
			}
			RpcHelper.writeJsonArray(response, array);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
