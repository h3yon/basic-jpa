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
            member.getAddressHistory().add(new Address("old1", "street", "1000"));
            member.getAddressHistory().add(new Address("old2", "street", "1000"));

            em.persist(member);

            // ======== 값 타입 조회 예제 =========

            em.flush();
            em.clear();

            System.out.println("============ START =============");
            Member findMember = em.find(Member.class, member.getId());
            // 지연로딩 homeAddress와 같이 select

            List<Address> addressHistory = findMember.getAddressHistory();
            for(Address address : addressHistory){
                System.out.println("address = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for(String favoriteFood: favoriteFoods){
                System.out.println("favoriteFood = " + favoriteFood);
            }


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
