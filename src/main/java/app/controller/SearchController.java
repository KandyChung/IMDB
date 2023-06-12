package app.controller;

import app.dao.PersonDAO;
import app.dao.ShowDAO;
import app.model.Person;
import app.model.Show;



public class SearchController {

	public static boolean checkActor(String actorName) {
        if (actorName == null) {
            return false;
        }
        Person person = PersonDAO.searchPersonByName(actorName);
        if (person == null) {
            return false;
        }
        return true;
        
    }
	
	public static boolean checkShow(String showTitle)
	{
		if (showTitle == null)
		{
			return false;
		}
		Show show = ShowDAO.searchShowByShowTitle(showTitle);
		
		if (show == null)
		{
			return false;
		}
		return true;
	}
}
