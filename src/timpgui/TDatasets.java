/*
 * TDatasets.java
 *
 * Created on September 20, 2007, 2:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package timpgui;

import Jama.Matrix;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

/**
 *
 * @author Joris
 */
public class TDatasets {
    
    /**
     * Creates a new instance of TDatasets
     */
    public TDatasets() {
       XYZDataset completeDataset;
       XYDataset subDataset;
    }

    public XYDataset createSpectra(String object) {
        
        // final Matrix psidf = TIMPGUI.getRCLPList(object);
        final Matrix con = TIMPGUI.getRCLPList(object);
        //final double rDim1[] = TIMPGUI.getRDim1List(object);
        final double alpha = 0.2;
        final double mu = TIMPGUI.R.eval("parEst("+object+")$irfpar[[1]][1]").asDouble();
        final double dim2[] = TIMPGUI.getRDim2List(object);
        //final double dim2Label[] = TIMPGUI.getRDim2Labels(object,alpha,mu);
        final int seriesCount = TIMPGUI.R.eval("dim(getCLPList("+object+")[[1]])[[2]]").asInt();
        final int itemCount = TIMPGUI.R.eval("dim(getCLPList("+object+")[[1]])[[1]]").asInt();
        
        return new XYDataset() {
            public int getSeriesCount() {
                return seriesCount;
            }
            public int getItemCount(int series) {
                return itemCount;
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return dim2[item];
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return con.get(item,series);
            }
            
            public void addChangeListener(DatasetChangeListener listener) {
                // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
                // ignore
            }
            public DatasetGroup getGroup() {
                return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "Series "+series;
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }
        };
    }

    public XYDataset createConcentrations(String object) {
        
        // final Matrix psidf = TIMPGUI.getRCLPList(object);
        final Matrix con = TIMPGUI.getRXList(object);
        //final double rDim1[] = TIMPGUI.getRDim1List(object);
        final double alpha = 0.1;
        final double mu = TIMPGUI.R.eval("parEst("+object+")$irfpar[[1]][1]").asDouble();
        final double dim1[] = TIMPGUI.getRDim1List(object);
        final double dim1Label[] = TIMPGUI.getRDim1Labels(object,mu,alpha);
        final int seriesCount = TIMPGUI.R.eval("dim(getXList("+object+")[[1]])[[2]]").asInt();
        final int itemCount = TIMPGUI.R.eval("dim(getXList("+object+")[[1]])[[1]]").asInt();
        
        return new XYDataset() {
            public int getSeriesCount() {
                return seriesCount;
            }
            public int getItemCount(int series) {
                return itemCount;
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return dim1Label[item];
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return con.get(item,series);
            }
            
            public void addChangeListener(DatasetChangeListener listener) {
                // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
                // ignore
            }
            public DatasetGroup getGroup() {
                return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "Series "+series;
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }
        };
    }    
    
    public XYZDataset createDatasetXYZ(String object) {
        
        final Matrix psidf = TIMPGUI.getRMatrix(object);
        final double x[] = TIMPGUI.getRDouble(object,"x");
        final double x2[] = TIMPGUI.getRDouble(object,"x2");
        final double xlabel[] = TIMPGUI.getRLinLogLabels(object,"x");
        
        return new XYZDataset() {
            
            public int getSeriesCount() {
                // System.out.println( psidf.getRowDimension());
                return psidf.getRowDimension(); // wavenumber
            }
            public int getItemCount(int series) {
                // return 10000;
                return psidf.getColumnDimension(); //timepoints
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return xlabel[series];
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return x2[item];
            }
            public Number getZ(int series, int item) {
                return new Double(getZValue(series, item));
            }
            public double getZValue(int series, int item) {
                //double x = getXValue(series, item);
                //double y = getYValue(series, item);
                //return psidf.get(series,item);
                return psidf.get(series,item);
            }
            public void addChangeListener(DatasetChangeListener listener) {
                // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
                // ignore
            }
            public DatasetGroup getGroup() {
                return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "psi.df";
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }
        };
    }
    
    public XYDataset selectColumFromDatasetXYZ(final XYZDataset data, final int colum) {
        
        return new XYDataset() {
            
            public int getSeriesCount() {
                return 1;
            }
            public int getItemCount(int series) {
                // return 10000;
                return data.getSeriesCount(); //row dimension
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return data.getXValue(item,series);
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return data.getZValue(item,colum);
            }
            public void addChangeListener(DatasetChangeListener listener) {
                // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
                // ignore
            }
            public DatasetGroup getGroup() {
                return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "psi.df[n][x2]";
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }
        };
    }
    
    public XYDataset selectRowFromDatasetXYZ(final XYZDataset data, final int row) {
        return new XYDataset() {
            
            public int getSeriesCount() {
                return 1;
            }
            public int getItemCount(int series) {
                // return 10000;
                return data.getItemCount(series); //GetItemCount(sries)=15
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return data.getYValue(series,item);
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return data.getZValue(row,item);
            }
            public void addChangeListener(DatasetChangeListener listener) {
                // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
                // ignore
            }
            public DatasetGroup getGroup() {
                return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "psi.df[n][x2]";
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }
        };
    }
    
    public XYZDataset createTestDatasetXYZ() {
        return new XYZDataset() {
            public int getSeriesCount() {
                return 1;
            }
            public int getItemCount(int series) {
                return 10000;
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return item / 100 - 50;
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return item - (item / 100) * 100 - 50;
            }
            public Number getZ(int series, int item) {
                return new Double(getZValue(series, item));
            }
            public double getZValue(int series, int item) {
                double x = getXValue(series, item);
                double y = getYValue(series, item);
                return Math.sin(Math.sqrt(x * x + y * y) / 5.0);
            }
            public void addChangeListener(DatasetChangeListener listener) {
                // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
                // ignore
            }
            public DatasetGroup getGroup() {
                return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "sin(sqrt(x + y))";
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }
        };
    }
    
    public XYZDataset createTestDatasetXY() {
        return new XYZDataset() {
            public int getSeriesCount() {
                return 1;
            }
            public int getItemCount(int series) {
                return 10000;
            }
            public Number getX(int series, int item) {
                return new Double(getXValue(series, item));
            }
            public double getXValue(int series, int item) {
                return item / 100 - 50;
            }
            public Number getY(int series, int item) {
                return new Double(getYValue(series, item));
            }
            public double getYValue(int series, int item) {
                return item - (item / 100) * 100 - 50;
            }
            public Number getZ(int series, int item) {
                return new Double(getZValue(series, item));
            }
            public double getZValue(int series, int item) {
                double x = getXValue(series, item);
                double y = getYValue(series, item);
                return Math.sin(Math.sqrt(x * x + y * y) / 5.0);
            }
            public void addChangeListener(DatasetChangeListener listener) {
                // ignore - this dataset never changes
            }
            public void removeChangeListener(DatasetChangeListener listener) {
                // ignore
            }
            public DatasetGroup getGroup() {
                return null;
            }
            public void setGroup(DatasetGroup group) {
                // ignore
            }
            public Comparable getSeriesKey(int series) {
                return "sin(sqrt(x + y))";
            }
            public int indexOf(Comparable seriesKey) {
                return 0;
            }
            public DomainOrder getDomainOrder() {
                return DomainOrder.ASCENDING;
            }
        };
    }
    
    public XYDataset createTestDataset2() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        
        double[] x1 = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        double[] y1 = new double[] {8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0};
        double[][] data1 = new double[][] {x1, y1};
        dataset.addSeries("Series 1", data1);
        
        double[] x2 = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        double[] y2 = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        double[][] data2 = new double[][] {x2, y2};
        dataset.addSeries("Series 2", data2);
        
        return dataset;
    }
    
    public XYDataset createTestDataset3() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        
//            Matrix psidf = TIMPGUI.getRMatrix("psi_1");
//            double x[] = TIMPGUI.getRDouble("psi_1","x");
//            double x2[] = TIMPGUI.getRDouble("psi_1","x2");
        
        double[] x1 = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        double[] y1 = new double[] {8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0};
        double[][] data1 = new double[][] {x1, y1};
        dataset.addSeries("Series 1", data1);
        
        double[] x2 = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        double[] y2 = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        double[][] data2 = new double[][] {x2, y2};
        dataset.addSeries("Series 2", data2);
        
        return dataset;
    }
    
    
}
