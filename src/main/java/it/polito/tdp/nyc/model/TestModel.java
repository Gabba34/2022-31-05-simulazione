package it.polito.tdp.nyc.model;

public class TestModel {
	
	
	public static void main(String[] args) {
//		Model m = new Model();
//		m.getProviders();
//		System.out.println(m.getProviders().size());
//		List<Borough> b = new ArrayList<>(m.getBoroughs("LinkNYC - Citybridge"));
//		System.out.println(b.size());
		
		Model model = new Model();
		model.getProviders();
		model.getBoroughs("LinkNYC - Citybridge");
		System.out.println(model.createGraph());
		
		
		
	}
}
