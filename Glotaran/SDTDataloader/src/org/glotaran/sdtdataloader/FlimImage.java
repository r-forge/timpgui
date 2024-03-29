package org.glotaran.sdtdataloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import org.glotaran.core.interfaces.TGDatasetInterface;
import org.glotaran.sdtdataloader.sdtstructures.BHFileBlockHeader;
import org.glotaran.sdtdataloader.sdtstructures.FileHeader;
import org.glotaran.sdtdataloader.sdtstructures.MeasureInfo;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.NotifyDescriptor.Confirmation;
import org.openide.util.NbBundle;
import static java.lang.Math.sqrt;
import static java.lang.Math.floor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sergey
 */
public class FlimImage implements TGDatasetInterface {

    private int[] data;
    private int[] binnedData;
    private int[] intmap;
    private int x, y;
    private int curvenum;
    private short cannelN;
    private short increm;
    private double cannelW;
    private double time;
    private short measmod;
    private int amplmax, amplmin;
    private int binned;

    public int getBinned() {
        return binned;
    }

    public void setBinned(int binned) {
        this.binned = binned;
    }

    public FlimImage() {
        cannelN = 1;
        x = y = 0;
        time = 0;
        curvenum = 0;
        increm = 0;
        cannelW = 0;
        amplmax = amplmin = 0;
    }

    public FlimImage(File file) throws IOException, IllegalAccessException, InstantiationException {
        ImageInputStream f = new FileImageInputStream(new RandomAccessFile(file, "r"));
        f.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        FileHeader header = new FileHeader();
        MeasureInfo measinf = new MeasureInfo();
        BHFileBlockHeader blhead = new BHFileBlockHeader();

        header.fread(f);
//        System.out.println(f.getStreamPosition());
        f.seek(header.meas_desc_block_offs);
//        System.out.println("meas "+f.getStreamPosition());
        measinf.fread(f);
//        System.out.println("blhead "+f.getStreamPosition());
        f.seek(header.data_block_offs);
        blhead.fread(f);
//        System.out.println("a blhead "+f.getStreamPosition());
        f.seek(blhead.data_offs);

        long size = blhead.block_length / 2;

        data = new int[(int) size];
        char[] tmpData = new char[(int) size];

//        System.out.println("data"+f.getStreamPosition());
        f.readFully(tmpData, 0, (int) size);

        f.close();

        curvenum = (int) blhead.block_length / measinf.adc_re / 2;
        x = (int) sqrt(curvenum);
        y = (int) sqrt(curvenum);
        time = measinf.tac_r / measinf.tac_g * 1e9;
        cannelN = measinf.adc_re;
        cannelW = time / cannelN;
        increm = measinf.incr;

        for (int i = 0; i < size; ++i) {
            data[i] = (int) tmpData[i] / increm;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCurveNum() {
        return curvenum;
    }

    public int getMaxIntens() {
        return amplmax;
    }

    public int getMinIntens() {
        return amplmin;
    }

    public double getTime() {
        return time;
    }

    public int getCannelN() {
        return cannelN;
    }

    public double getCannelW() {
        return cannelW;
    }

    public int[] getData() {
        return binned == 1 ? binnedData : data;
//        if (binned==1) {
//            return binnedData;
//        }
//        else {
//            return data;
//        }
    }

    public int[] getIntMap() {
        return intmap;
    }

    public int[] getPixTrace(int i, int j) {
        int[] result = new int[cannelN];
        for (int ind = 0; ind < cannelN; ind++) {
            result[ind] = data[(i * x + j) * cannelN + ind];
        }
        return result;//data+(i*x+j)*cannelN;
    }

    public int[] getPixTrace(int i) {
        int[] result = new int[cannelN];
        for (int ind = 0; ind < cannelN; ind++) {
            result[ind] = data[i * cannelN + ind];
        }
        return result;//data+(i*x+j)*cannelN;
    }

    public void setTime(float t) {
        time = t;
        cannelW = time / cannelN;
    }

    public void setCannelN(short n) {
        cannelN = n;
        cannelW = time / n;
    }

    public void setCurveNum(int n) {
        curvenum = n;
    }

    public void setX(int n) {
        x = n;
    }

    public void setY(int n) {
        y = n;
    }

    public void setIncrem(short n) {
        increm = n;
    }

    public void setData(int[] datnew) {
        data = datnew;
    }

    public void setIntMap(int[] intmapnew) {
        intmap = intmapnew;
    }

    public int findCan(double t) {
        return (int) floor(t / cannelW);
    }

    public void buildIntMap(int mode) {
// mode = 1 - integral intensyty (number of photons in decay) 
// mode !=1 - amplitude of sygnal (number of photons in max)

        intmap = new int[curvenum];
        int tmp;
        amplmin = 65000;
        amplmax = tmp = 0;
        for (int i = 0; i < curvenum; i++) {
            tmp = this.getDataPoint(i * cannelN);
            for (int j = 1; j < cannelN; j++) {
                if (mode == 1) {
                    tmp += this.getDataPoint(i * cannelN + j);
                } else if (this.getDataPoint(i * cannelN + j) > tmp) {
                    tmp = this.getDataPoint(i * cannelN + j);
                }
            }
            intmap[i] = tmp;
            if (amplmin > tmp) {
                amplmin = tmp;
            }
            if (amplmax < tmp) {
                amplmax = tmp;
            }
        }
    }

    public void saveDataToASCIIFile(String fileName) throws IOException {
        File f = new File(fileName);
        if (f.exists()) {
            f.delete();
        }
        PrintWriter w = new PrintWriter(f);
        for (int i = 0; i < curvenum; ++i) {
            for (int j = 0; j < cannelN; ++j) {
                int ind = i * cannelN + j;
                w.print(data[ind] + " ");
            }
            w.println();
        }
        w.close();
    }

    public void pixTraceToFile(int i, int j, String fileName) {
    }

    public int getDataPoint(int index) {
        if (binned == 1) {
            return binnedData[index];
        } else {
            return data[index];
        }
    }

    public void makeBinnedImage(int bin) {
        binnedData = new int[curvenum * cannelN];
        int temp;
        for (int i = bin; i < y - bin; i++) {
            for (int j = bin; j < x - bin; j++) {
                for (int t = 0; t < cannelN; t++) {
                    temp = 0;
                    for (int m = i - bin; m <= i + bin; m++) {
                        for (int n = j - bin; n <= j + bin; n++) {
                            temp += data[(m * x + n) * cannelN + t];
                        }
                    }
                    binnedData[(i * x + j) * cannelN + t] = temp;
                }
            }
        }
    }

    public String getExtention() {
        return "sdt";
    }

    public String getType(File file) {
        return "FLIM";
    }

    public String getFilterString() {
        return ".sdt Backer&Hickl FLIM Image";
    }

    public boolean Validator(File file) throws FileNotFoundException, IOException, IllegalAccessException, InstantiationException {
        ImageInputStream f = new FileImageInputStream(new RandomAccessFile(file, "r"));
        f.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        FileHeader header = new FileHeader();
//        MeasureInfo measinf = new MeasureInfo();
        header.fread(f);

        if (header.header_valid == 0X5555) {
            return true;
        } else {
            Confirmation msg = new NotifyDescriptor.Confirmation(
                    NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("headerNotValid"),
                    NbBundle.getBundle("org/glotaran/core/main/Bundle").getString("err"),
                    NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(msg);
            return false;
        }
    }
}
