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
            Address address = new Address("city", "street", "1000");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            em.persist(member);

            // 값 타입의 부작용 때문에
            // 값 타입의 복사 필요
            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            Member member2 = new Member();
            member2.setUsername("member2");
            // 기본 타입이 아니라 객체 타입이기 때문에
            // 객체의 공유 참조는 피할 수 없다,,
            // 그래서 setter 지우기
            member2.setHomeAddress(copyAddress);
            em.persist(member2);

            // 값 타입의 부작용 -> 수정 불가능하도록하기(불변 객체)
            member.getHomeAddress().setCity("newCity");



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
