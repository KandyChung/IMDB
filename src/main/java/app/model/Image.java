package app.model;

import java.util.List;

public class Image {
    private String folder;
    private String name;

    private List<Person> personsTagged;
    private List<Show> showsTagged;

    private Image(int id, boolean person) {
        folder = (person) ? "/img/people/" : "/img/shows";
        name = id + ".jpg";
    }




}
