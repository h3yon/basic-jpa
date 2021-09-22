package hellojpa;

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

            Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.getReference(Member.class, member2.getId());

            System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass())); // false
            System.out.println("m1 == m2: " + (m1 instanceof Member)); // true

            //select 쿼리가 안 나감 -> 프록시
            Member refMember = em.getReference(Member.class, member1.getId());
            // 하이버네이트가 만든 가짜 클래스 -> 프록시
            System.out.println("findMember = " + refMember.getClass());
            // select 쿼리 안 나감(위에 member.getId()가 있으니까) -> 프록시
            System.out.println("findMember.id = " + refMember.getId()); // Proxy

            // 얘는 DB에 없으니까 쿼리가 나감 -> 실제 객체
            System.out.println("findMember.username = " + refMember.getUsername());

            // DB에 실제 조회함. 근데 아래에서 프록시로 반환
            // 그 이유? 위에서 getReference에서 프록시를 한번 조회해서 find 하면 프록시로 반환해버림
            Member findMember = em.find(Member.class, member1.getId()); // Member
            // JPA에서는 참을 보장
            System.out.println("refMember == findMember: " + (refMember == findMember));

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
