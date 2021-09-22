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

            Member refMember = em.getReference(Member.class, member2.getId());

            // 준영속 상태일 때 프록시를 초기화할 경우(close / detach / clear)
            em.detach(refMember);

            refMember.getUsername();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            // 프록시를 초기화하지 못한다는 에러 발생. 더이상 영속성 컨텍스트로 관리 X
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
