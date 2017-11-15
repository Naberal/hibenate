package ProjectManagementSystem.model;

import javax.persistence.*;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idcompanies")
    private int id;
    @Column(name = "companieName")
    private String name;

    public Company( ) {
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(int id) {
        this.id = id;
    }

    public int getId( ) {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName( ) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString( ) {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
