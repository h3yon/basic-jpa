package hellojpa;


import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

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
//
//    public Team getTeam() {
//        return team;
//    }

//    public void changeTeam(Team team) {
//        // 양쪽으로 세팅해줌. 편의 메서드
//        this.team = team;
//        team.getMembers().add(this);
//    }
//
//    public void setTeam(Team team) {
//        this.team = team;
//    }

}