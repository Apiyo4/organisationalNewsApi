import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUserDao;
import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import exceptions.ApiException;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class App {

    public static void main(String[] args) {
        Sql2oUserDao userDao;
        Sql2oDepartmentDao departmentDao;
        Sql2oNewsDao newsDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/organisational-news-api.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();

        //CREATE

        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            res.type("application/json");
            return gson.toJson(department);
        });

        post("/departments/:departmentId/news/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            News news = gson.fromJson(req.body(), News.class);
            news.setDepartmentId(departmentId);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        post("/departments/:departmentId/users/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            User users = gson.fromJson(req.body(), User.class);
            users.setDepartmentId(departmentId);
            userDao.add(users);
            res.status(201);
            return gson.toJson(users);
        });

        post("/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        post("/news/new", "application/json", (req, res) -> {
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        //Read


        get("/departments", "application/json", (req, res) -> {
            System.out.println(departmentDao.getAll());

            if(departmentDao.getAll().size() > 0){
                return gson.toJson(departmentDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });

        get("/departments/:id", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(departmentToFind);
        });

        get("/departments/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<News> allNews;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allNews = newsDao.getNews(departmentId);

            return gson.toJson(allNews);
        });
        get("/departments/:id/users", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<News> allNews;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allUsers = userDao.getUsers(departmentId);

            return gson.toJson(allNews);
        });
        get("/news", "application/json", (req, res) -> {
            return gson.toJson(newsDao.getAll());
        });

        get("/users", "application/json", (req, res) -> {
            return gson.toJson(userDao.getAll());
        });
    }
}