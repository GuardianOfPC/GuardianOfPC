package dao;

import Models.Cat;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class CatDao
{
    public Cat findById(int id)
    {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Cat.class, id);
    }

    public void save(Cat cat)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(cat);
        tx1.commit();
        session.close();
    }

    public void update(Cat cat)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(cat);
        tx1.commit();
        session.close();
    }

    public void delete(Cat cat)
    {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(cat);
        tx1.commit();
        session.close();
    }

    public List<Cat> findAll()
    {
        return (List<Cat>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Cat").list();
    }
}
