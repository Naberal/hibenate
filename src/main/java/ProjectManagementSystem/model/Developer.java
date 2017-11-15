package ProjectManagementSystem.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "developer")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iddeveloper")
    private int id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "experianse")
    private int experience;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Project project;
    @Column(name = "selery")
    private Double salary;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "developersskills", joinColumns = @JoinColumn(name = "developer"),
            inverseJoinColumns = @JoinColumn(name = "Skill"))
    private List<Skill> skills = new LinkedList<>();

    public Developer( ) {
    }

    public Developer(int id, String firstName, String lastName, int experience, Project project, Double selery) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experience;
        this.project = project;
        this.salary = selery;
    }

    public Developer(String firstName, String lastName, int experiance, Project project, Double selery,
                     List<Skill> skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.experience = experiance;
        this.project = project;
        this.salary = selery;
        this.skills = skills;
    }

    public int getId( ) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName( ) {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName( ) {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getExperience( ) {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Project getProject( ) {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Double getSalary( ) {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public List<Skill> getSkills( ) {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString( ) {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", experience=" + experience +
                ", project=" + project +
                ", salary=" + salary +
                ", skills=" + skills +
                '}';
    }
}


