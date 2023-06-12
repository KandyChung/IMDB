package app.dao;

import app.dao.utils.DatabaseUtils;
import app.model.Show;
import app.model.UserReview;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;



public class UserReviewDAO {

//	List<UserReview> userReviews = new ArrayList<>();

	public static final List <UserReview> getRatingByShowID(int showid) {
		
		List<UserReview> userReviews = new ArrayList<>();
				
		try {
			
			String sql = "SELECT * FROM imbd.user_review WHERE show_id ='" + showid + "'";
			
			Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()) {
                // 2) Add it to the list we have prepared
                userReviews.add(
                  
                  new UserReview(
                		  result.getString("review"),
                		  result.getInt("rating"),
                          result.getInt("show_id"),
                          result.getInt("reviewId")
                          )
                );
            
            }
			
            DatabaseUtils.closeConnection(connection);
            
		}
		
		catch (Exception e) {
            e.printStackTrace();
        }
		
		if(!userReviews.isEmpty()) return userReviews;
        return null;
	}
	
	public static final void createRatingIntoDatabase(Show show, int rating) {
		
//		Map<String, Object> model = ViewUtil.baseModel(ctx);
		
//		Random random = new Random();
//		int number = random.nextInt();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate=dateFormat.format(date);
		
		
		try {
			
			//String sql = "SELECT * FROM imbd.user_review WHERE show_id ='" + showid + "'";
			
			String sql = "insert into imbd.user_review (reviewid, show_id, user_id, rating, review, date)\n"
					+"values ('" + getLastIDFromUserReview() + "', '" + show.getShowid() + "','caramel6', '" + rating + "', 'null', '" + formattedDate + "')";
			
			Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
//            ResultSet result = statement.executeQuery(sql);
            statement.executeUpdate(sql);
            
            DatabaseUtils.closeConnection(connection);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	public List<UserReview> getUserReviewList(){
//    	return userReviews; 
//    }
	
	public static final List <UserReview> getReviewByShowID(int showid) {
		List<UserReview> userReviews = new ArrayList<>();
		
		try {
			String sql = "SELECT * FROM imbd.user_review WHERE show_id ='" + showid + "'";
			
			Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
        
            while(result.next()) {
                userReviews.add(
                  new UserReview(
                		  result.getString("review"),
                		  result.getString("user_id"),
                          result.getInt("show_id"),
                          result.getInt("reviewId")
                          )
                );
            }
            DatabaseUtils.closeConnection(connection);
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		if(!userReviews.isEmpty()) return userReviews;
        return null;
	}
	
	public static final void addingReviewIntoDatabase(Show show, String review) {
//		Random random = new Random();
//		int number = random.nextInt();
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate=dateFormat.format(date);
		UserReview ur = new UserReview();
		
		try {
			String sql = "insert into imbd.user_review (reviewid, show_id, user_id, rating, review, date)\n"
					+"values ('" + getLastIDFromUserReview() + "', '" + show.getShowid() + "','caramel6', '" + ur.getRating() + "', '" + review + "', '" + formattedDate + "')";
			
			Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            
            DatabaseUtils.closeConnection(connection);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getLastIDFromUserReview()
	{
		int last_id = 0;
		
		try
		{
			String sql = "SELECT MAX(reviewId)\n"
						+ "FROM imbd.user_review";
			
			Connection connection = DatabaseUtils.connectToDatabase();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next())
			{
				last_id = result.getInt("max(reviewId)");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		last_id = last_id + 1;
		
		return last_id;
	}
}
	

