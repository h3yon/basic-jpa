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

            Member member1 = new Member();
            member1.setUsername("hello");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("hello");
            em.persist(member2);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass()); // Proxy

            // 초기화(이거 없으면 아래 isLoaded false)
            refMember.getUsername();
            // 로딩되는지 여부 확인 -> false. 초기화 안 해서.
            // 위에 초기화한 걸로 바꿨으니까 true
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

            // 강제 초기화 / member.getName();
            Hibernate.initialize(refMember);

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
