package hellojpa;


import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MBR")
public class Member {

    @Id
    private Long id;

    // DDL 생성 기능. 실행 자체에 영향을 주진 않음
    // JPA의 실행 로직에는 영향을 주지 않음
    @Column(unique = true, length = 10)
    private String name;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
