package dao;

import models.Department;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {
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
    public void addingDepartmentSetsId() throws Exception {
        Department testDepartment = setupNewDepartment();
        int originalDepartmentId = testDepartment.getId();
        departmentDao.add(testDepartment);
        assertNotEquals(originalDepartmentId, testDepartment.getId());
    }

    @Test
    public void getAll() throws Exception{

    }

    @Test
    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
        Department testDepartment = setupNewDepartment();
        assertEquals(1, departmentDao.getAll().size());
    }
    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectDepartment() throws Exception {
        Department testDepartment = setupNewDepartment();
        Department otherDepartment = setupNewDepartment();
        assertEquals(testDepartment, departmentDao.findById(testDepartment.getId()));
    }

//    @Test
//    public void updateCorrectlyUpdatesAllFields() throws Exception {
//        Department testDepartment = setupNewDepartment();
//        departmentDao.update(testDepartment.getId(), "a", "b", "c");
//        Department foundDepartment = departmentDao.findById(testDepartment.getId());
//        assertEquals("a", foundDepartment.getDepartmentName());
//        assertEquals("b", foundDepartment.getDescription());
//        assertEquals("c", foundDepartment.getNumberOfEmployees());
//    }

    public Department setupNewDepartment(){
        Department department = new Department("accounting", "handles company's finance", 5);
        departmentDao.add(department);
        return department;
    }

    @Test
    public void deleteByIdDeletesCorrectDepartment() throws Exception {
        Department testDepartment = setupNewDepartment();
        departmentDao.deleteById(testDepartment.getId());
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Department testDepartment = setupNewDepartment();
        Department otherDepartment = setupNewDepartment();
        departmentDao.clearAll();
        assertEquals(0, departmentDao.getAll().size());
    }
//
//    @Test
//    public void getAllUsersForADepartmentReturnsUsersCorrectly() throws Exception {
//        User testUser  = new User("Apiyo", 1, "accountant");
//        userDao.add(testUser);
//
//        User otherUser  = new User("Annette", 2, "programmer");
//        userDao.add(otherUser);
//
//        Department testDepartment = setupNewDepartment();
//        departmentDao.add(testDepartment);
//        departmentDao.addDepartmentToUser(testDepartment,testUser);
//        departmentDao.addDepartmentToUser(testDepartment,otherUser);
//
//        User[] users = {testUser, otherUser};
//
//        assertEquals(Arrays.asList(users), departmentDao.getAllUsersByDepartment(testDepartment.getId()));
//    }

}