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
            // 저장
            Team team = new Team();
            team.setName("TeamA");
            // 아래로 들어가면 TEAM_ID가 null
//            team.getMembers().add(member);
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");

            /**
             * changeTeam 또는 addMember 둘 중 하나를 선택해야됨
             */

            // ** 요기 중요. 연관관계의 주인에다가 값을 세팅
            // 뭔가를 해주었기 때문에 이름을 set -> change로 해줌.
//            member.changeTeam(team);
            em.persist(member);

            team.addMember(member);

            // ** 읽기 전용이라서 JPA에서 안 씀
            // ** 역방향(주인이 아닌 방향)에만 연관관계 설정
            // 이거 안 넣어주면? flush, clear을 안 해주면 아래 em.find에서 1차 캐시에 있음.(in 메모리)
            //      그럼 select 쿼리가 안 나감. team이 영속성 컨텍스트에 들어가니까 그대로 값이 안 나옴
            //      이렇게 **양쪽으로 값을 세팅해주는 게 맞음
            // team.getMembers().add(member); -> setTeam에 넣어줌

            // DB에서 다시 조회
            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            System.out.println("===============");
            // 양쪽으로 계속 호출함. toString으로 무한루프!!
            System.out.println("members = " + findTeam);
            System.out.println("===============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
