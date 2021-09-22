package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("hello");
            em.persist(member1);

            member1.setTeam(team);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member1.getId()); // member만 조회
            System.out.println("m = " + m.getTeam().getClass()); // proxy

            System.out.println("==============");
            // 프록시 객체가 초기화되면서 DB에서 가져옴
            m.getTeam().getName();
            System.out.println("==============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
