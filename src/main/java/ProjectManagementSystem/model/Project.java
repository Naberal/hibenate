package ProjectManagementSystem.model;

public class Project {
    private int id;
    private String name;
    private Company company;
    private  Customer customer;
    private Double cost;

    public Project(int id) {
        this.id = id;
    }

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
