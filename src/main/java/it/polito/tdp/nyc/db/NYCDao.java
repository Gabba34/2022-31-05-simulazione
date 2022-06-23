package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.nyc.model.Borough;
import it.polito.tdp.nyc.model.Hotspot;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getProvider (){
		String sql = "SELECT DISTINCT Provider "
				+ "FROM nyc_wifi_hotspot_locations";
		List<String> providers = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				providers.add(result.getString("Provider"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");		
		}
		return providers;
	}
	
	public List<Borough> getBoroughs (String provider){
		String sql = "SELECT DISTINCT Borough, BoroName, AVG(Latitude) AS Lat, AVG(Longitude) AS Lng, COUNT(*) AS NUM "
				+ "FROM nyc_wifi_hotspot_locations "
				+ "WHERE Provider = ? "
				+ "GROUP BY Borough "
				+ "ORDER BY Borough";
		List<Borough> boroughs = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, provider);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				boroughs.add(new Borough(result.getString("Borough"), result.getString("BoroName"), new LatLng(result.getDouble("Lat"), result.getDouble("Lng")),result.getInt("NUM")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");		
		}
		return boroughs;
	}
}
