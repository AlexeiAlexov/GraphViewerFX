package application.model;

import application.controller.MatrixOperator;

public class Matrix {

	public static final int REFLEX              = 0b1;
	public static final int ANTIREFLEX          = 0b10;
	public static final int SYMETRIC            = 0b100;
	public static final int ASYMETRIC           = 0b1000;
	public static final int ANTISYMETRIC        = 0b10000;
	public static final int TRANSITIVE          = 0b100000;
	public static final int NEGATIVE_TRANSITIVE = 0b1000000;
	public static final int STRONG_TRANSITIVE   = 0b10000000;
	public static final int CONNECTED           = 0b100000000;
	
	private int[][] body;
	
	private String name;
	
	private long properties;
	
	public Matrix(String name, int size){
		
		body = new int[size][size];
		
		this.name = name;
	}
	
	public void set(int x, int y, int d){
		body[x][y] = d;
	}
	
	public int get(int x, int y){
		return body[x][y];
	}
	
	public void refreshProperties(){
		properties = 0;
		if (getAntiReflex())properties |= ANTIREFLEX;
		if (getReflex())properties |= REFLEX;
		if (getSymetric())properties |= SYMETRIC;
		if (getASymetric())properties |= ASYMETRIC;
		if (getAntiSymetric())properties |= ANTISYMETRIC;
		if (getTransitivity())properties |= TRANSITIVE;
		if (getNegativeTransitive())properties |= NEGATIVE_TRANSITIVE;
		if (getStrongTransitive())properties |= STRONG_TRANSITIVE;
		if (getConnected())properties |= CONNECTED;
	}
	
	public String getName(){return name;}
	
	public void setName(String name){this.name = name;} 
	
	public int getSize(){return body.length;}
	
	public boolean getProperty(int id){
		return (properties & id) == id;
	}
	
	public void setProperty(int id, boolean b){
		if (b)
			properties = properties | id;
		else 
			if (getProperty(id))properties -= id;
	}
	
	
	public boolean getAntiReflex(){ 
		for(int i = 0; i < getSize(); i++){
			if (body[i][i] != 0)return false;
		}
		return true;
	}
	
	private boolean getReflex(){
		for(int i = 0; i < getSize(); i++){
			if (body[i][i] != 1)return false;
		}
		return true;
	}
	
	public boolean getSymetric(){
		for(int y = 0; y < getSize(); y++){
			for(int x = 0; x < getSize(); x++){
				if (body[x][y] != body[y][x])return false;
			}
		}
		return true;
	}
	
	public boolean getASymetric(){
		boolean symetric = false;
		boolean nonsymetric = false;
		for(int y = 0; y < getSize(); y++){
			for(int x = 0; x < getSize(); x++){
				if (body[x][y] != body[y][x])nonsymetric = true;
				if (body[x][y] == body[y][x])symetric = true;
				if (symetric && nonsymetric)return true;
			}
		}
		return false;
	}
	
	public boolean getAntiSymetric(){
		for(int y = 0; y < getSize(); y++){
			for(int x = 0; x < getSize(); x++){
				if (x == y)
					if(body[x][y] == 0)return false;
				else
					if (body[x][y] == body[y][x])return false;
			}
		}
		return true;
	}
	
	public boolean getTransitivity(){
		return getTransitivity(this);
	}
	
	public boolean getNegativeTransitive(){
		return getTransitivity(MatrixOperator.inverse(this));
	}

	private static boolean getTransitivity(Matrix m){
		if (MatrixOperator.product(m, m).isBelong(m))
			return true;
		else
			return false;
	}
	
	public boolean isBelong(Matrix m){
		for(int y = 0; y < m.getSize(); y++){
			for(int x = 0; x < m.getSize(); x++){
				if (body[x][y] == 1)if (m.get(x, y) != 1)return false;
			}
		}
		return true;
	}
	
	public boolean getStrongTransitive(){
		return getTransitivity() && getNegativeTransitive();
	}
	
	public boolean getConnected(){
		for(int y = 0; y < getSize(); y++){
			for(int x = 0; x < getSize(); x++){
				if ((body[x][y] | body[y][x] | (body[x][y] & body[y][x])) == 0)return false;
			}
		}
		return true;
	}
	
	public String toString(){
		String result = "";
		for(int y = 0; y < getSize(); y++){
			for(int x = 0; x < getSize(); x++){
				result += get(x, y) + " ";
			}	
			result += "\n";
		}
		return result;
	}

}
