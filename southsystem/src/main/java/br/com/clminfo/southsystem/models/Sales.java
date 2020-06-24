package br.com.clminfo.southsystem.models;

import java.util.HashMap;
import java.util.Map;

public class Sales {
	
	private String id;
	private Long saleId;
	private Map<Item, Integer> items;
	private Seller seller;
	
	public Sales() {
	}
	
	public Sales(final String id, final Long saleId, final Seller seller) {
		this.id = id;
		this.saleId = saleId;
		this.seller = seller;
		this.items = new HashMap<Item, Integer>();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Long getSaleId() {
		return saleId;
	}
	
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	
	public Map<Item, Integer> getItems() {
		return items;
	}
	
	public void setItems(Map<Item, Integer> items) {
		this.items = items;
	}
	
	public Seller getSeller() {
		return seller;
	}
	
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	public void addItem(final Item item){
		if(this.items.containsKey(item)) {
			this.items.put(item, this.items.get(item) + 1);
		} else {
			this.items.put(item, 1);
		}
	}

	@Override
	public String toString() {
		return "Sales [id=" + id + ", saleId=" + saleId + ", items=" + items + ", seller=" + seller + "]";
	}

}
