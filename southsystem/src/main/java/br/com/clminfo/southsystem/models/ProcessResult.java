package br.com.clminfo.southsystem.models;

public class ProcessResult {
	
	private Integer customerTotal;
	private Integer sellerTotal;
	private Long mostValueSaleId;
	private String worstSeller;
	
	public ProcessResult(final Integer customerTotal, final Integer sellerTotal, final Long mostValueSaleId, final String worstSeller) {
		this.customerTotal = customerTotal;
		this.sellerTotal = sellerTotal;
		this.mostValueSaleId = mostValueSaleId;
		this.worstSeller = worstSeller;
	}
	
	public Integer getCustomerTotal() {
		return customerTotal;
	}
	
	public Integer getSellerTotal() {
		return sellerTotal;
	}
	
	public Long getMostValueSaleId() {
		return mostValueSaleId;
	}
	
	public String getWorstSeller() {
		return worstSeller;
	}

	@Override
	public String toString() {
		return "Quantidade de Clientes: " + customerTotal + "\nQuantidade de Vendedores: " + sellerTotal + "\nId da venda mais cara: "
				+ mostValueSaleId + "\nPior Vendedor: " + worstSeller;
	}

}
