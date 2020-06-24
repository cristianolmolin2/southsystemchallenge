package br.com.clminfo.southsystem.models;

public class Seller {
	
	private String id;
	private String cpf;
	private String name;
	private Double salary;
	
	public Seller() {
	}
	
	public Seller(final String id, final String cpf, final String name, final Double salary) {
		this.id = id;
		this.cpf = cpf;
		this.name = name;
		this.salary = salary;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getSalary() {
		return salary;
	}
	
	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", cpf=" + cpf + ", name=" + name + ", salary=" + salary + "]";
	}

}
