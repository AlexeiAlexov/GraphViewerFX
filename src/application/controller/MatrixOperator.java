package application.controller;

import application.model.Matrix;

public class MatrixOperator {

	public static final int ADDITION = 1;
	public static final int CROSSING = 2;
	public static final int ASSOCIATION = 3;
	public static final int DIFFERENCE = 4;
	public static final int SIMETRIC_DIFFERENCE = 5;
	public static final int INVERSE = 6;
	public static final int PRODUCT = 7;
	
	public static Matrix makeOperation(int id, Matrix m1, Matrix m2){
		
		switch(id){
			case ADDITION:
				return addition(m1);
			case CROSSING:
				return crossing(m1, m2);
			case ASSOCIATION:
				return association(m1, m2);
			case DIFFERENCE:
				return difference(m1, m2);
			case SIMETRIC_DIFFERENCE:
				return simetricDifference(m1, m2);
			case INVERSE:
				return inverse(m1);
			case PRODUCT:
				return product(m1, m2);
				default:
					return null;
		}
		
	}
	
	public static Matrix addition(Matrix m){
				
		Matrix result = new Matrix(m.getName() + "_addiction", m.getSize());
		
		for(int y = 0; y < m.getSize(); y++){
			for(int x = 0; x < m.getSize(); x++){
				result.set(x, y, m.get(x, y) == 1 ? 0 : 1);
			}
		}
		
		return result;
	}
	
	public static Matrix crossing(Matrix m1, Matrix m2){
		
		if (m1.getSize() != m2.getSize())
			return null;
		
		Matrix result = new Matrix(m1.getName() + "_crossing_" + m2.getName(), m1.getSize());
		
		for(int y = 0; y < m1.getSize(); y++){
			for(int x = 0; x < m1.getSize(); x++){
				result.set(x, y, m1.get(x, y) == 1 && m2.get(x, y) == 1 ? 1 : 0);
			}
		}
		
		return result;
		
	}
	
	public static Matrix association(Matrix m1, Matrix m2){
		
		if (m1.getSize() != m2.getSize())
			return null;
		
		Matrix result = new Matrix(m1.getName() + "_association_" + m2.getName(), m1.getSize());
		
		for(int y = 0; y < m1.getSize(); y++){
			for(int x = 0; x < m1.getSize(); x++){
				result.set(x, y, m1.get(x, y) == 1 || m2.get(x, y) == 1 ? 1 : 0);
			}
		}
		
		return result;
		
	}
	
	public static Matrix difference(Matrix m1, Matrix m2){
		
		if (m1.getSize() != m2.getSize())
			return null;
		
		Matrix result = new Matrix(m1.getName() + "_difference_" + m2.getName(), m1.getSize());
		
		for(int y = 0; y < m1.getSize(); y++){
			for(int x = 0; x < m1.getSize(); x++){
				result.set(x, y, m2.get(x, y) == 1 ? 0 : m1.get(x, y));
			}
		}
		
		return result;
		
	}
	
	public static Matrix simetricDifference(Matrix m1, Matrix m2){
		
		if (m1.getSize() != m2.getSize())
			return null;
		
		Matrix result = new Matrix(m1.getName() + "_simetricD_" + m2.getName(), m1.getSize());
		
		for(int y = 0; y < m1.getSize(); y++){
			for(int x = 0; x < m1.getSize(); x++){
				result.set(x, y, (m1.get(x, y) & m2.get(x, y)) == 1 ? 0 : (m1.get(x, y) | m2.get(x, y)));
			}
		}
		
		return result;
		
	}
	
	public static Matrix inverse(Matrix m){
		
		Matrix result = new Matrix(m.getName() + "_inversed", m.getSize());
		
		for(int y = 0; y < m.getSize(); y++){
			for(int x = 0; x < m.getSize(); x++){
				result.set(y, x, m.get(x, y));
			}
		}
		
		return result;
		
	}
	
	public static Matrix product(Matrix m1, Matrix m2){
		
		if (m1.getSize() != m2.getSize())
			return null;
		
		Matrix result = new Matrix(m1.getName() + "_product_" + m2.getName(), m1.getSize());
		
		int res;
		
		for(int y = 0; y < m1.getSize(); y++){
			for(int x = 0; x < m1.getSize(); x++){
				
				res = 0;
				
				for(int i = 0; i < m1.getSize(); i++)
					 if (res == 0)res = min(m1.get(i, y), m2.get(x, i));
				
				result.set(x, y, res);
			}
		}
		
		return result;
		
	}
	
	private static int min(int n, int m){
		return n > m ? m : n;
	}
}
