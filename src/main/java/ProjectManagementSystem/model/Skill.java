package ProjectManagementSystem.model;

import javax.persistence.*;

@Entity
@Table(name = "skill")
public class Skill {
    @Column(name = "skill")
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Skill( ) {
    }

    public Skill(String skill, int id) {
        this.name = skill;
        this.id = id;
    }

    public Skill(String skill) {
        this.name = skill;
    }

    public String getName( ) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId( ) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString( ) {
        return "Skill{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
