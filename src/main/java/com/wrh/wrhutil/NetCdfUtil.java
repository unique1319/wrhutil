package com.wrh.wrhutil;

import lombok.extern.slf4j.Slf4j;
import ucar.ma2.ArrayFloat;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.*;
import ucar.nc2.dt.GridDatatype;
import ucar.nc2.dt.grid.CFGridWriter2;
import ucar.nc2.dt.grid.GridDataset;
import ucar.nc2.dt.grid.GridDatasetInfo;
import ucar.nc2.ft.GridDatasetStandardFactory;
import ucar.nc2.grib.collection.Grib2Collection;
import ucar.nc2.grib.collection.GribCdmIndex;
import ucar.nc2.grib.grib2.Grib2Record;
import ucar.nc2.grib.grib2.Grib2Utils;
import ucar.nc2.write.Nc4Chunking;
import ucar.nc2.write.Nc4ChunkingDefault;
import ucar.nc2.write.Nc4ChunkingStrategyGrib;
import ucar.nc2.write.Nc4ChunkingStrategyNone;

import java.io.File;
import java.io.IOException;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/6/18 15:22
 * @describe
 */
@Slf4j
public class NetCdfUtil {


    /**
     * create time: 2019/6/24 16:53
     * description: Todo 输出格点文件
     */
    public static void save(float[] latArray, float[] lonArray, float[][] gridArray, String saveFilePath) {

        final int latSize = latArray.length;
        final int lonSize = lonArray.length;

        NetcdfFileWriter gridFile = null;

        try {
            gridFile = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, saveFilePath);
            Dimension latDim = gridFile.addDimension(null, "latitude", latSize);
            Dimension lonDim = gridFile.addDimension(null, "longitude", lonSize);

            Variable latVar = gridFile.addVariable(null, "latitude", DataType.FLOAT, "latitude");
            Variable lonVar = gridFile.addVariable(null, "longitude", DataType.FLOAT, "longitude");
            Variable rainVar = gridFile.addVariable(null, "rain", DataType.FLOAT, "latitude longitude");
            gridFile.addVariableAttribute(latVar, new Attribute("units", "degrees_north"));
            gridFile.addVariableAttribute(lonVar, new Attribute("units", "degrees_east"));
            gridFile.addVariableAttribute(rainVar, new Attribute("units", "mm"));

            ArrayFloat.D1 lats = new ArrayFloat.D1(latSize);
            ArrayFloat.D1 lons = new ArrayFloat.D1(lonSize);
            ArrayFloat.D2 grid = new ArrayFloat.D2(latSize, lonSize);

            for (int i = 0; i < latSize; i++) {
                lats.set(i, latArray[i]);
                for (int j = 0; j < lonSize; j++) {
                    lons.set(j, lonArray[j]);
                    grid.set(i, j, gridArray[i][j]);
                }
            }

            File dir = new File(saveFilePath).getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            gridFile.create();
            gridFile.write(latVar, lats);
            gridFile.write(lonVar, lons);
            gridFile.write(rainVar, new int[2], grid);
            log.info("*** SUCCESS writing grb file " + saveFilePath);
        } catch (Exception e) {
            log.error("*** ERROR writing grb file ");
            e.printStackTrace();
        } finally {
            if (gridFile != null) {
                try {
                    gridFile.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

        }
    }


    public static void main(String[] args) throws IOException, InvalidRangeException {

        float[] latArray = new float[100];
        float[] lonArray = new float[100];
        float[][] gridArray = new float[100][100];


        String saveFilePath = "C:\\Users\\Administrator\\Desktop\\test.GRB2";
//        save(latArray, lonArray, gridArray, saveFilePath);

        saveGrb2(saveFilePath);


    }


    public static void saveGrb2(String saveFilePath) throws IOException, InvalidRangeException {
        String location = "E:\\gribprocess\\GribCMA\\TEM_HOR\\20191204\\Z_NAFP_C_BABJ_20191204024934_P_CLDAS_RT_CHN_0P05_HOR-TEM-2019120402.GRB2";
//        GridDataset dataset = GridDataset.open("");
//        Nc4Chunking nc4Chunking = new Nc4ChunkingStrategyNone();
//        NetcdfFileWriter writer = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf4, saveFilePath,nc4Chunking);
//        CFGridWriter2.writeFile(dataset, null, null, null, 1, null, null, 1, false, writer);

        ucar.nc2.jni.netcdf.Nc4Iosp.setLibraryAndPath("C:\\Program Files (x86)\\netCDF 4.7.4\\bin","netcdf");
        NetcdfFile ncfileIn =  NetcdfFile.open(location);
        FileWriter2 writer = new ucar.nc2.FileWriter2(ncfileIn, saveFilePath, NetcdfFileWriter.Version.netcdf4, new Nc4ChunkingDefault());

        NetcdfFile ncfileOut = writer.write();

        ncfileIn.close();
        ncfileOut.close();
        System.out.println("finish");

    }

}
