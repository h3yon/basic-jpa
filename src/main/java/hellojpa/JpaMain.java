package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // MEMBER에 MEMBER_ID, CITY/STREET/ZIPCODE, USERNAME, TEAM_ID
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "1000"));

            // FAVORITE_FOOD에 잘 들어가 있음
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            // ADDRESS에 잘 들어가 있음(다른 테이블인데도 persist 안 해도 들어가 있음)
            // 값 타입이기 때문
            // member에 의존! -> 자동
            // 일대다로 진행
            member.getAddressHistory().add(new AddressEntity("old1", "street", "1000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "1000"));

            em.persist(member);

            // ======== 값 타입 조회 예제 =========

            em.flush();
            em.clear();

            System.out.println("============ START =============");
            Member findMember = em.find(Member.class, member.getId());

            // homeCity -> newCity
//            findMember.getAddressHistory().setCity("newCity");
            // 값 타입 -> 사이드 이펙트 발생 가능

            // 통으로 갈아끼워야 함
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 치킨 -> 한식
            // 알아서 바꿔짐
            // 값 타입 컬렉션들은 멤버에 의존
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // old1 -> new
            // equals로 찾아서 삭제
            // **** 값 타입 컬렉션 변경 사항 발생 시 주인 entity 관련 모든 데이터 삭제 후 현재 값 다시 저장
//             findMember.getAddressHistory().remove(new Address("old1", "street", "1000"));
//             findMember.getAddressHistory().add(new Address("newCity1", "street", "1000"));

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
