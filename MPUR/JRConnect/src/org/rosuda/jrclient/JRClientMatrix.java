package org.rosuda.jrclient;


import org.rosuda.JRclient.REXP;
import org.rosuda.irconnect.AREXP;
import org.rosuda.irconnect.IREXP;
import org.rosuda.irconnect.IRMatrix;

@Deprecated
public class JRClientMatrix implements IRMatrix {

	private final String[] columnNames;
	private final String[] rowNames;
	private final int columns;
	private final int rows;
	private final IREXP[][] values;
	
	JRClientMatrix(final int[] dim, final REXP delegate) {
		columns = dim[1];
		rows = dim[0];
		values = new IREXP[rows][columns];
		if (delegate.getType()==REXP.XT_ARRAY_DOUBLE) {
			final double[] darray = delegate.asDoubleArray();
			for (int r=0;r<rows;r++) {
				for (int c=0;c<columns;c++) {
					final int idx = r+c*rows;
					if (darray.length>idx) {
						final double dval = darray[idx];
						values[r][c] = new AREXP(){
                            @Override
							public double asDouble() {
								return dval;
							}
                            @Override
							public int getType() {
								return IREXP.XT_DOUBLE;
							}
						};						
					} else {
						values[r][c] = new AREXP() {
                            @Override
							public int getType() {
								return IREXP.XT_NULL;
							}
						};
					}
				}
			}
		}
		String[] rNames = null;
		String[] cNames = null;
		/*
        if (delegate.getAttribute()!=null&&delegate.getAttribute().getType()==REXP.XT_LIST) {
			final RList rList = delegate.getAttribute().asList();
			if (rList.getBody()!=null&&rList.getBody().getType()==REXP.XT_LIST) {
				final RList dimNames = rList.getBody().asList();
				if (dimNames.getTag()!=null&&dimNames.getTag().toString().equalsIgnoreCase("[SYMBOL dimnames]")&&
					dimNames.getHead()!=null&&dimNames.getHead().getType()==REXP.XT_VECTOR) {
					final Vector namesVector = dimNames.getHead().asVector();
					if (namesVector.size()==2) {
						final REXP r0 = (REXP) namesVector.get(0);
						final REXP r1 = (REXP) namesVector.get(1);
						final Vector rowNamesVec = r0.asVector();
						if (rowNamesVec!=null&&rowNamesVec.size()>0) {
							rNames = new String[rowNamesVec.size()];
							for (int r=0;r<rowNamesVec.size();r++) {
								rNames[r] = ((REXP)rowNamesVec.get(r)).asString();
							}
						}
						final Vector colNamesVec = r1.asVector();
						if (colNamesVec!=null&&colNamesVec.size()>0) {
							cNames = new String[colNamesVec.size()];
							for (int c=0;c<colNamesVec.size();c++) {
								cNames[c] = ((REXP)colNamesVec.get(c)).asString();
							}
						}
					}
				}
			}
		}*/
		rowNames = rNames;
		columnNames = cNames;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	public String getColumnNameAt(final int row) {
		if (columnNames!=null&&columnNames.length>row)
			return columnNames[row];
		return null;
	}

	public String getRowNameAt(final int column) {
		if (rowNames!=null&&rowNames.length>column)
			return rowNames[column];
		return null;
	}

	public IREXP getValueAt(final int row, final int col) {
		return values[row][col];
	}
}
