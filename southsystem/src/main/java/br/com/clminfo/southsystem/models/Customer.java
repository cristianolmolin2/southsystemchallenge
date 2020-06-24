package br.com.clminfo.southsystem.models;

public class Customer {
	
	private String id;
	private String cnpj;
	private String name;
	private String businessArea;
	
	public Customer() {
	}
	
	public Customer(final String id, final String cnpj, final String name, final String businessArea) {
		this.id = id;
		this.cnpj = cnpj;
		this.name = name;
		this.businessArea = businessArea;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBusinessArea() {
		return businessArea;
	}
	
	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", cnpj=" + cnpj + ", name=" + name + ", businessArea=" + businessArea + "]";
	}

}
