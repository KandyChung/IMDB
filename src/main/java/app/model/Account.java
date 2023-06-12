package app.model;

import java.util.List;

public class Account {
    private String firstName;
    private String lastName;
    private String username;

    /**
     * Only stores hashed passwords.
     */
    private String password;

    private String country;
    private String gender;
    private String email;
    private String type;

    private List<UserReview> userReviews;

    public Account(String un, String p) {
        username = un;
        password = p;
    }


    public Account(String username, String fn, String ln,String c, String g, String email, String type) {
        // TODO fill in here
        /* You should use this constructor when you are showing the account page,
        hence, the user is already logged in. Therefore, the username Should be used
        to fetch this information from the database. You may have to tweek some stuff
        here and there.
        You should NEVER show the current password in the form. NEVER!
        And if you want to change the password, you need to ask for current password as well.
         */
    	this.username = username;
    	firstName = fn;
    	lastName = ln;
    	country = c;
    	gender = g;
    	this.email = email;
    	this.type = type;
    	
    }




    public void updateDetails() {
        // TODO
    }



    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }
    
    public String getFirstName() {
    	return firstName;
    }
    
    public String getLastName() {
    	return lastName;
    }
    
    public String getType() {
		return type;
	}


	public String getCountry() {
    	return country;
    }
    
    public String getGender() {
    	return gender;
    }
    
    public String getEmail() {
    	return email;
    }
}
