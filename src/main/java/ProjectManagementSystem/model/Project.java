package ProjectManagementSystem.model;

import javax.persistence.*;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idproject")
    private int id;
    @Column(name = "projectName")
    private String name;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcompanie")
    private Company company;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idcostomer")
    private Customer customer;
    @Column(name = "cost")
    private Double cost;


    public Project(int id, String name, Company company, Customer customer, Double cost) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.customer = customer;
        this.cost = cost;
    }

    public Project( ) {
    }

    public Project(String name, Company company, Customer customer, Double cost) {
        this.name = name;
        this.company = company;
        this.customer = customer;
        this.cost = cost;
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

    public Company getCompany( ) {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Customer getCustomer( ) {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getCost( ) {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!name.equals(project.name)) return false;
        if (!company.equals(project.company)) return false;
        if (!customer.equals(project.customer)) return false;
        return cost.equals(project.cost);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + company.hashCode();
        result = 31 * result + customer.hashCode();
        result = 31 * result + cost.hashCode();
        return result;
    }

    @Override
    public String toString( ) {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company=" + company +
                ", customer=" + customer +
                ", cost=" + cost +
                '}';
    }
}
