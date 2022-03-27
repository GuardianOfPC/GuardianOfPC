package dao;

import Models.Owner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class OwnerDao
{
    public Owner findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Owner.class, id);
    }

    public void save(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(owner);
        tx.commit();
        session.close();
    }

    public void update(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(owner);
        tx.commit();
        session.close();
    }

    public void delete(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(owner);
        tx.commit();
        session.close();
    }

    public List<Owner> findAll() {
        return (List<Owner>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Owner").list();
    }
}
