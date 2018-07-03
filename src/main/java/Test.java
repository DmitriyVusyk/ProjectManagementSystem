import controller.MainController;
import controller.SQLFunctions;
import model.Project;
import model.Skill;

public class Test {

    public static void main(String[] args) {

        SQLFunctions sqlFunctions = new SQLFunctions();
        Skill skill = new Skill("Java","middle");

        Project project = sqlFunctions.getProjectById(1);

        System.out.println(project);

        MainController mc = new MainController();
        System.out.println("Sum of salary of all developers of projects: ");
        System.out.println(mc.getSalaryOfAllDevelopersOnProject(project));

        System.out.println("All developers of project: ");
        System.out.println(mc.getListOfDevelopersOnProject(project));

        System.out.println("developer with skill Java: ");
        System.out.println(mc.getListOfJavaDevelopers(skill));

        System.out.println("developers with skill grade middle: ");
        System.out.println(mc.getListOfAllMiddleDevelopers(skill));

        System.out.println(" show project data with format Date - Project Name - Count of devs on project: ");
        System.out.println(mc.getListOfProjectWithFormat(1));
    }

}
