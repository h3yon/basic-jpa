package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 영속(없으니까 가져올 것)
            Member member = em.find(Member.class, 101L);
            member.setName("AAAAA");

            // 준영속
            // 1. 이걸 해서 준영속 상태가 되어서 변경감지가 진행되지 않음
            em.detach(member);
            // 2. 영속성 컨텍스트를 다 지워버림
            em.clear();
            // 3. 영속성 컨텍스트 종료
            em.close();

            // FLUSH
            // 이걸 하면 바로 SQL 진행됨.
            // 사용방법: 1. 위처럼 flush() 2. 트랜잭션 사용 시 자동 3. JPQL 사용 시 자동
            // 영속성 상태를 비우지 않음
            System.out.println("=== AFTER ===");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
