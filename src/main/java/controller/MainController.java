package controller;

import dao.DAO;
import jdbc.Connector;
import model.Developer;
import model.Project;
import model.Skill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainController {

    private Connector connector = new Connector();
    private Statement statement = connector.getStatement();
    private DAO dao = new DAO();

    /*
    зарплату(сумму) всех разработчиков отдельного проекта;
     */
    public int getSalaryOfAllDevelopersOnProject(Project project) {

        int result = -1;

        String sqlQuarry = "SELECT project_id, SUM(salary) as sum FROM DEVELOPERS_has_PROJECTS " +
                "  LEFT JOIN developers ON DEVELOPERS_has_PROJECTS.developer_id = developers.id " +
                "  LEFT JOIN projects ON DEVELOPERS_has_PROJECTS.project_id = projects.id " +
                "  WHERE PROJECTS.id LIKE " + project.getId() +
                " GROUP BY project_id " +
                "ORDER BY project_id ";

        ResultSet rs = dao.read(sqlQuarry);

        if (rs != null) {
            try {
                result = rs.getInt("sum");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


        return result;
    }

    /*
    список разработчиков отдельного проекта
     */
    public ArrayList<Developer> getListOfDevelopersOnProject(Project project) {
        ArrayList<Developer> result = new ArrayList<>();
        ArrayList<Integer> devIds = getListOfDevIdsOnProject(project);

       // int[]ids = devIds.stream().mapToInt(i -> i ).toArray();

        for (int id:
             devIds) {
            result.add(getDeveloperById(id));
        }

        return result;
    }

    private ArrayList<Integer> getListOfDevIdsOnProject(Project project){
        ArrayList devIds = new ArrayList<>();

        String getAllDevelopersOnProject = "SELECT developer_id FROM DEVELOPERS_has_PROJECTS WHERE project_id LIKE '"
                + project.getId() + "'";

        ResultSet rs = dao.read(getAllDevelopersOnProject);

        if (rs != null) {
            try {
                do {
                    devIds.add(rs.getInt("developer_id"));
                } while (rs.next());

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return devIds;
    }

    private Developer getDeveloperById(int id) {

            Developer developer = new Developer();

            String sql = "SELECT * FROM DEVELOPERS WHERE id LIKE " + id;

            ResultSet rs = dao.read(sql);

            try {
                if (rs != null) {
                    developer.setId(rs.getInt("id"));
                    developer.setDeveloperName(rs.getString("developer_name"));
                    developer.setAge(rs.getInt("age"));
                    developer.setSex(rs.getString("sex"));
                    developer.setSalary(rs.getInt("salary"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        return developer;
    }

    /*
    список всех Java разработчиков
     */
    public ArrayList<Developer> getListOfJavaDevelopers(Skill skill) {
        ArrayList<Developer> result = new ArrayList<>();
        ArrayList<Integer> devIds = ;

        // int[]ids = devIds.stream().mapToInt(i -> i ).toArray();

        for (int id:
                devIds) {
            result.add(getDeveloperById(id));
        }

        return result;
    }

    private ArrayList<Integer> getListOfDevIdsWithSkill(Skill skill){
        ArrayList devIds = new ArrayList<>();

        String getAllDevelopersOnProject = "SELECT * FROM DEVELOPERS_has_SKILLS " +
        "LEFT JOIN skills ON DEVELOPERS_has_SKILLS.skill_id = skills.id " +
        "LEFT JOIN developers ON DEVELOPERS_has_SKILLS.developer_id = developers.id " +
        "WHERE SKILLS.skill LIKE '" + skill.getName() +"'";

        ResultSet rs = dao.read(getAllDevelopersOnProject);

        if (rs != null) {
            try {
                do {
                    devIds.add(rs.getInt("developer_id"));
                } while (rs.next());

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return devIds;
    }

    /*
    список всех middle разработчиков
     */
    public ArrayList<Developer> getListOfAllMiddleDevelopers(Skill skill) {
        return null;
    }

    /*
    список проектов в следующем формате: дата создания - название проекта - количество разработчиков на этом проекте.
     */
    public ArrayList<Project> getListOfProjectWithFormat() {
        return null;
    }

    /*
    Также: создать заготовки операций(закомментированные query) для создания новых проектов, разработчиков, клиентов.
    ! Не забывать о правильных связях между таблиц !
     */

}
