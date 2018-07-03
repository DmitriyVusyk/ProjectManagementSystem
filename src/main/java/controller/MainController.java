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

        for (int id :
                devIds) {
            result.add(getDeveloperById(id));
        }

        return result;
    }

    private ArrayList<Integer> getListOfDevIdsOnProject(Project project) {
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
        ArrayList<Integer> devIds = getListOfDevIdsWithSkill(skill);

        for (int id :
                devIds) {
            result.add(getDeveloperById(id));
        }

        return result;
    }

    private ArrayList<Integer> getListOfDevIdsWithSkill(Skill skill) {
        ArrayList devIds = new ArrayList<>();

        String getAllDevelopersOnProject = "SELECT DEVELOPERS.id FROM DEVELOPERS_has_SKILLS\n" +
                "  LEFT JOIN skills ON DEVELOPERS_has_SKILLS.skill_id = skills.id\n" +
                "  LEFT JOIN developers ON DEVELOPERS_has_SKILLS.developer_id = developers.id\n" +
                "WHERE SKILLS.skill LIKE '" + skill.getName() + "'";

        ResultSet rs = dao.read(getAllDevelopersOnProject);

        if (rs != null) {
            try {
                do {
                    devIds.add(rs.getInt("id"));
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

    private ArrayList<Integer> getListOfDevIdsWithSkillGrade(Skill skill) {
        ArrayList devIds = new ArrayList<>();

        String listWithSkillAndGrade = "SELECT DEVELOPERS.id FROM DEVELOPERS_has_SKILLS\n" +
                "  LEFT JOIN skills ON DEVELOPERS_has_SKILLS.skill_id = skills.id\n" +
                "  LEFT JOIN developers ON DEVELOPERS_has_SKILLS.developer_id = developers.id\n" +
                "WHERE SKILLS.grade LIKE '" + skill.getGrade() + "'";

        ResultSet rs = dao.read(listWithSkillAndGrade);

        if (rs != null) {
            try {
                do {
                    devIds.add(rs.getInt("id"));
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
        ArrayList<Developer> result = new ArrayList<>();
        ArrayList<Integer> devIds = getListOfDevIdsWithSkillGrade(skill);

        for (int id :
                devIds) {
            result.add(getDeveloperById(id));
        }

        return result;
    }

    /*
    список проектов в следующем формате: дата создания - название проекта - количество разработчиков на этом проекте.
     */
    public String getListOfProjectWithFormat(int projectId) {
        String resultString;
        Project project  = getProjectById(projectId);
        ArrayList devIds = new ArrayList<>();

        String listOfDevelopersOnProject = "SELECT DEVELOPERS.id FROM DEVELOPERS_has_PROJECTS\n" +
                "  LEFT JOIN PROJECTS ON DEVELOPERS_has_PROJECTS.project_id = PROJECTS.id\n" +
                "  LEFT JOIN developers ON DEVELOPERS_has_PROJECTS.developer_id = developers.id\n" +
                "WHERE project_id LIKE '" + projectId + "'";

        ResultSet rs = dao.read(listOfDevelopersOnProject);

        if (rs != null) {
            try {
                do {
                    devIds.add(rs.getInt("id"));
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

        resultString = "[ Date of creation: field not exist; "+
        "Project name: " + project.getName() + "; " +
                "Count of developers working on Project: " + devIds.size() + "; ]";

        return resultString;
    }

    public Project getProjectById(int id){
        Project project = new Project();

        String sql = "SELECT * FROM PROJECTS WHERE id LIKE " + id;

        ResultSet rs = dao.read(sql);

        try {
            if (rs != null) {
                project.setId(rs.getInt("id"));
                project.setName(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                project.setCost(rs.getInt("cost"));
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

        return project;
    }

    /*
    Также: создать заготовки операций(закомментированные query) для создания новых проектов, разработчиков, клиентов.
    ! Не забывать о правильных связях между таблиц !
     */

}
