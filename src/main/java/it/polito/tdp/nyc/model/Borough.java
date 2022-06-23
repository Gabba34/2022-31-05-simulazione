package it.polito.tdp.nyc.model;

import java.util.Objects;

import com.javadocmd.simplelatlng.LatLng;

public class Borough {
	
	String borough;
	String boroName;
	LatLng position;
	int num;

	public Borough(String borough, String boroName, LatLng position, int num) {
		super();
		this.borough = borough;
		this.boroName = boroName;
		this.position = position;
		this.num = num;
	}

	public String getBorough() {
		return borough;
	}

	public void setBorough(String borough) {
		this.borough = borough;
	}

	public String getBoroName() {
		return boroName;
	}

	public void setBoroName(String boroName) {
		this.boroName = boroName;
	}

	public LatLng getPosition() {
		return position;
	}

	public void setPosition(LatLng position) {
		this.position = position;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boroName, borough, num, position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borough other = (Borough) obj;
		return Objects.equals(boroName, other.boroName) && Objects.equals(borough, other.borough) && num == other.num
				&& Objects.equals(position, other.position);
	}

	@Override
	public String toString() {
		return boroName;
	}

	
}
