package dao;

import models.Department;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUserDaoTest {
    private Connection conn;
    private Sql2oDepartmentDao departmentDao;
    private Sql2oUserDao userDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingUserSetsId() throws Exception {
        User testUser = setupNewUser();
        int originalUserId = testUser.getId();
        userDao.add(testUser);
        assertNotEquals(originalUserId, testUser.getId());
    }

    @Test
    public void addedUsersAreReturnedFromGetAll() throws Exception {
        User testUser = setupNewUser();
        userDao.add(testUser);
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    public void noUserReturnsEmptyList() throws Exception {
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectUser() throws Exception {
        User user = setupNewUser();
        userDao.add(user);
        userDao.deleteById(user.getId());
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        User testUser = setupNewUser();
        User otherUser = setupNewUser();
        userDao.clearAll();
        assertEquals(0, userDao.getAll().size());
    }
    @Test
    public void add_addDepartmentIdIntoDB_true() {
        Department testDepartment = new Department("accounting", "handles firm accounting", 6);
        departmentDao.add(testDepartment);
        User testUser = new User("Bubbles", testDepartment.getId(), "auditor");
        userDao.add(testUser);
        assertEquals(testUser.getDepartmentId(), testDepartment.getId());
    }

    @Test
    public void getAllUsersByDepartment() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        User user1 = new User("Apiyo", 1, "accountant");
        userDao.add(user1);
        User user2 = new User("Apiyo", 1, "accountant");
        userDao.add(user2);
        User usersForOtherDepartment = new User("Apiyo", 2, "accountant");
        userDao.add(usersForOtherDepartment);
        assertEquals(2, userDao.getAllUsersByDepartment(testDepartment.getId()).size());
    }

    public Department setupDepartment() {
        Department department = new Department("accounting", "handles firm budget", 6);
                departmentDao.add(department);
        return department;
    }

    public User setupNewUser() {
        return new User("Apiyo", 1, "accountant");
    }
}