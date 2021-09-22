package hellojpa;


import javax.persistence.*;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // ~ToOne은 꼭 아래처럼 LAZY 해줘야됨
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

//    public void changeTeam(Team team) {
//        // 양쪽으로 세팅해줌. 편의 메서드
//        this.team = team;
//        team.getMembers().add(this);
//    }
//
    public void setTeam(Team team) {
        this.team = team;
    }

}