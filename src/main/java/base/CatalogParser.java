package base;

import base.course.Course;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class CatalogParser {

    private static final String CATALOG_URL = "http://catalog.calpoly.edu/coursesaz/";

    public CatalogParser() {
    }

    public ArrayList<String> getDepartments() {
        ArrayList<String> departments = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(CATALOG_URL).get();
            Elements depElements = doc.select(".sitemaplink");
            for (Element depElement : depElements) {
                String department = depElement.text().split("[\\(\\)]")[1].toLowerCase();
                departments.add(department);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return departments;
    }

    public ArrayList<Course> getCourses(String department) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(CATALOG_URL + department).get();

            Elements courseElements = doc.select(".courseblock");
            for (Element courseElement : courseElements) {
                Course course = new Course();
                course.setName(extractCourseName(courseElement));
                course.setTitle(extractCourseTitle(courseElement));
                course.setUnits(extractCourseUnits(courseElement));
                course.setPrerequisites(extractCoursePrerequisites(courseElement));
                course.setDescription(extractCourseDescription(courseElement));
                course.setTermsOffered(extractCourseTermsOffered(courseElement));

                courses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }

    private String extractCourseName(Element courseElement) {
        Elements titleElement = courseElement.select(".courseblocktitle").select("strong");
        String courseName = titleElement.text().split("[/^(.*?)./]")[0];
        courseName = courseName.replace("\u00a0", "");
        return courseName;
    }

    private String extractCourseTitle(Element courseElement) {
        Elements titleElement = courseElement.select(".courseblocktitle").select("strong");
        String courseTitle = titleElement.text().split("[/^(.*?)./]")[0];
        return courseTitle;
    }

    private String extractCourseUnits(Element courseElement) {
        String courseUnits = "";
        return courseUnits;
    }

    private String extractCoursePrerequisites(Element courseElement) {
        String coursePrerequisites = "";
        return coursePrerequisites;
    }

    private String extractCourseDescription(Element courseElement) {
        String courseDescription = "";
        return courseDescription;
    }

    private String extractCourseTermsOffered(Element courseElement) {
        String courseTermsOffered = "";
        return courseTermsOffered;
    }
}
