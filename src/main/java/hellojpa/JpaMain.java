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

            // 비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            // 영속
            System.out.println("=== BEFORE ===");
            em.persist(member);
            System.out.println("=== AFTER ===");

            // [1차 캐시] entityManager에서 관리되고 있으면 1차 캐시에서 조회. DB까지 안 감
            Member findMember = em.find(Member.class, 101L);
            System.out.println("findMember.id = " + findMember.getId());

            // [영속 엔티티의 동일성 보장] by 1차 캐시, in application 차원
            Member findMember1 = em.find(Member.class, 101L);
            System.out.println(findMember1 == findMember);

            // [변경 감지 Dirty Checking] persist하지 않아도 잘 변경됨
            //      1. flush
            //      2. JPA가 entity와 snapshot(최초 영속성 컨텍스트에 들어온 상태) 비교
            //      3. 바뀌면 UPDATE SQL을 쓰기 지연 SQL 저장소
            //      4. SQL이 DB에 flush(FLUSH? 변경 내용을 데이터베이스에 반영)
            //      5. DB commit
            findMember1.setName("change");

            // [트랜잭션을 지원하는 쓰기 지연] 커밋하는 순간 데이터베이스에 SQL을 보냄(flush)
            //      그 전까지는 쓰기 지연 SQL 저장소에 저장!
            //      batch size는 모아서 한번에 보낼 수 있음 -> 버퍼링을 모아서 라이트?
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
