package dao;

import models.News;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oNewsDaoTest {
    private Connection conn;
    private Sql2oDepartmentDao departmentDao;
    private Sql2oNewsDao newsDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingNewsSetsId() throws Exception{
        News testNews = setupNews();
        int originalNewsId = testNews.getId();
        newsDao.add(testNews);
        assertNotEquals(originalNewsId, testNews.getId());;
    }

    @Test
    public void addedNewsAreReturnedFromGetAll() throws Exception {
        News testNews = new News("Ceo, Bob Collimore, dies");
        newsDao.add(testNews);
        assertEquals(1, newsDao.getAll().size());
    }

    @Test
    public void noNewsReturnsEmptyList() throws Exception {
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectNews() throws Exception {
        News news = new News("Ceo, Bob Collimore, dies");
        newsDao.add(news);
        newsDao.deleteById(news.getId());
        assertEquals(0, newsDao.getAll().size());
    }


    @Test
    public void clearAll() throws Exception {
        News testNews = setupNews();
        News otherNews = setupNews();
        newsDao.clearAll();
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectNews() throws Exception {
        News testNews = setupNews();
        News otherNews = setupNews();
        assertEquals(testNews, newsDao.findById(testNews.getId()));
    }


    public News setupNews(){
        News news = new News("Ceo, Bob Collimore, dies");
        newsDao.add(news);
        return news;
    }
}