package br.com.clminfo.southsystem.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.clminfo.southsystem.exceptions.BusinessException;
import br.com.clminfo.southsystem.models.Customer;
import br.com.clminfo.southsystem.models.Item;
import br.com.clminfo.southsystem.models.ProcessResult;
import br.com.clminfo.southsystem.models.Sales;
import br.com.clminfo.southsystem.models.Seller;

@Service
public class BatchFilesService {

	private static final String IN_FOLDER = "/data/in";
	private static final String OUT_FOLDER = "/data/out/";

	@Value("${HOMEPATH}")
	private String homePath;
	
	private Set<Seller> sellers;
	private Set<Customer> customers;
	private Set<Sales> sales;
	
	
	public void processBatch() {
		this.initLists();
		List<File> files = getFileListFromDir();

		for (File file : files) {
			this.processFile(file);
		}
		
		processResult();
	}
	
	private void initLists() {
		this.sellers = new HashSet<Seller>();
		this.customers = new HashSet<Customer>();
		this.sales = new HashSet<Sales>();
	}
	
	private List<File> getFileListFromDir(){
		File dir = new File(homePath + IN_FOLDER);

		File[] files = dir.listFiles(this.getFilter());
		
		if(null == files) {
			throw new BusinessException("Diretório não encontrado");
		}
		
		return Arrays.asList(files);
	}
	
	private FilenameFilter getFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".dat");
			}
		};
	}
	
	private void processFile(File file) {
		for (String line : this.getFileContentAsString(file)) {
			String[] lineContent = line.split("ç");
			switch(lineContent[0]) {
			case "001":
				processSeller(lineContent);
				break;
			case "002":
				processCustomer(lineContent);
				break;
			case "003":
				processSales(lineContent);
				break;
			default:
				break;
			}
		};
	}
	
	private List<String> getFileContentAsString(File file){
		List<String> lines = new ArrayList<String>();
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(file.getAbsolutePath()))){
			lines = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	private void processSeller(String[] sellerLine) {
		this.sellers.add(new Seller(sellerLine[0], sellerLine[1], sellerLine[2], Double.valueOf(sellerLine[3])));
	}
	
	private void processCustomer(String[] customerLine) {
		this.customers.add(new Customer(customerLine[0], customerLine[1], customerLine[2], customerLine[3]));
	}
	
	private void processSales(String[] salesLine) {
		Sales sale = new Sales(salesLine[0], Long.valueOf(salesLine[1]), getSellerByName(salesLine[3]));
		processSalesItems(sale, salesLine[2]);
		this.sales.add(sale);
	}
	
	private Seller getSellerByName(String sellerName) {
		return this.sellers.stream().filter(seller -> seller.getName().equals(sellerName)).findFirst().get();
	}
	
	private void processSalesItems(Sales sale, String saleItems) {
		String[] itemsAsString = saleItems.replaceAll("^\\[|]$", "").split(",");
		for (String itemString : itemsAsString) {
			String[] itemDetail = itemString.split("-");
			for(int i = 0; i < Integer.valueOf(itemDetail[1]); i++) {
				sale.addItem(new Item(Long.valueOf(itemDetail[0]), Double.valueOf(itemDetail[2])));
			}
		}
	}
	
	private void processResult() {
		ProcessResult pr = new ProcessResult(this.customers.size(), this.sellers.size(), getMostValueSale(), getWorstSeller().getName());
		this.createResultFile(pr);
	}
	
	private Long getMostValueSale() {
		
		Sales mostValue = null;
		Double mostValueTotal = 0.0;
		
		for (Sales sale : sales) {
			Double total = getTotal(sale);
			
			if(mostValue != null) {
				if(total > mostValueTotal) {
					mostValue = sale;
					mostValueTotal = total;
				}
			} else {
				mostValue = sale;
				mostValueTotal = total;
			}
		}
		
		return mostValue.getSaleId();
	}
	
	private Seller getWorstSeller() {
		Map<Seller, List<Sales>> salesBySeller = getSalesBySeller(); 
		Seller worstSeller = null;
		
		Double worstAmount = 0.0;
		
		for (Seller seller : salesBySeller.keySet()) {
			Double total = 0.0;
			for (Sales sale : salesBySeller.get(seller)) {
				total += getTotal(sale);
			}
			
			if(worstSeller != null) {
				if(total < worstAmount) {
					worstSeller = seller;
					worstAmount = total;
				}
			} else {
				worstSeller = seller;
				worstAmount = total;
			}
		}
		
		return worstSeller;
	}
	
	private Double getTotal(Sales sale) {
		Double total = 0.0;
		for (Item item : sale.getItems().keySet()) {
			total = total + item.getPrice() * sale.getItems().get(item);
		}
		return total;
	}
	
	private Map<Seller, List<Sales>> getSalesBySeller(){
		Map<Seller, List<Sales>> salesBySeller = new HashMap<Seller, List<Sales>>();
		for (Sales sale : sales) {
			if(!salesBySeller.containsKey(sale.getSeller())) {
				salesBySeller.put(sale.getSeller(), new ArrayList<Sales>());
			}
			List<Sales> salesBySellerList = salesBySeller.get(sale.getSeller());
			salesBySellerList.add(sale);
			salesBySeller.put(sale.getSeller(), salesBySellerList);
		}
		
		return salesBySeller;
	}
	
	private void createResultFile(ProcessResult pr) {
		try {
			FileOutputStream os = new FileOutputStream(new File(homePath + OUT_FOLDER + new Date().getTime() + ".done.dat"));
			byte[] toBytes = pr.toString().getBytes();
			os.write(toBytes);
			
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("Ocorreu um erro ao criar o arquivo de resultados");
		}
	}

}
