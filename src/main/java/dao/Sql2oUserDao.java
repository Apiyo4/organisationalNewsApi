package dao;

import org.sql2o.Sql2o;

public class Sql2oUserDao {
    private final Sql2o sql2o;
    public Sql2oUserDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

}
