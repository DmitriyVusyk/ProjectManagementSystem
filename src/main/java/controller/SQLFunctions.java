package controller;

import dao.DAO;
import model.Project;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLFunctions {

    private DAO dao = new DAO();

    public Project getProjectById(int id) {

        Project project = new Project();

        String selectSQL =
                "SELECT id, project_name, description, cost " +
                        "FROM PROJECTS " +
                        "WHERE id=" + id;

        ResultSet rs = dao.read(selectSQL);

        if (rs != null) {
            try {
                project.setId(rs.getInt("id"));
                project.setCost(rs.getInt("cost"));
                project.setName(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
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

        return project;
    }

}
