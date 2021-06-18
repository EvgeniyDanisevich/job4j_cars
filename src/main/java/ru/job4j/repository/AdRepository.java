package ru.job4j.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.model.Ad;

import java.util.Date;
import java.util.List;

public class AdRepository {
    private final SessionFactory factory;

    public AdRepository(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Ad> allAdsForDay() {
        Date date = new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            List result = session.createQuery(
                    "select a from Ad a "
                            + " where a.created > :date")
                    .setParameter("date", date)
                    .list();
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (Exception e) {
            factory.getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }

    public List<Ad> allAdsWithPhoto() {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            List result = session.createQuery("select a from Ad a "
                    + "join fetch a.car c join fetch a.user u "
                    + " join fetch a.photo p").list();
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (Exception e) {
            factory.getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }

    public List<Ad> allAdsForMark(String mark) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            List result = session.createQuery("select a from Ad a join fetch a.car c"
                    + " where c.mark = :mark")
                    .setParameter("mark", mark)
                    .list();
            session.getTransaction().commit();
            session.close();
            return result;
        } catch (Exception e) {
            factory.getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }
}