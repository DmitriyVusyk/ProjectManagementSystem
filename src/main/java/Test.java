import controller.MainController;
import controller.SQLFunctions;
import model.Project;

public class Test {

    public static void main(String[] args) {

        SQLFunctions sqlFunctions = new SQLFunctions();

        Project project = sqlFunctions.getProjectById(1);

        System.out.println(project);

        MainController mc = new MainController();
       // System.out.println(mc.getSalaryOfAllDevelopersOnProject(project));
        System.out.println(mc.getListOfDevelopersOnProject(project));
    }

}
