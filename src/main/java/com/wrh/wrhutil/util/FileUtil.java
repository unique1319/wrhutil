package com.wrh.wrhutil.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * @author wrh
 * @version 1.0
 * @date 2020/5/27 14:33
 * @describe 文件操作工具类
 */
public class FileUtil {

    /**
     * 自定义过滤器，可以过滤出来文件名字中包含指定字符串的文件
     */
    public static class FileFilterByString implements IOFileFilter {
        private String strFilter;

        public FileFilterByString(String strFilter) {
            this.strFilter = strFilter;
        }

        @Override
        public boolean accept(File file, String s) {
            return false;
        }

        @Override
        public boolean accept(File file) {
            String fileName = file.getName();
            return fileName.contains(strFilter);
        }
    }


    /**
     * 获取文件的创建时间
     *
     * @param file
     * @return
     */
    public static Long getFileCreateTime(File file) {
        try {
            Path path = Paths.get(file.getAbsolutePath());
            BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            BasicFileAttributes attr = basicview.readAttributes();
            return attr.creationTime().toMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取最新修改的文件
     *
     * @param files
     * @return
     */
    public static File findLastModifiedFile(File[] files) {
        if (files == null || files.length == 0) return null;
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return (int) (file2.lastModified() - file1.lastModified());
            }
        });
        return files[0];
    }

    /**
     * 获取最新创建的文件
     *
     * @param files
     * @return
     */
    public static File findLastCreatedFile(File[] files) {
        if (files == null || files.length == 0) return null;
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return (int) (getFileCreateTime(file2) - getFileCreateTime(file1));
            }
        });
        return files[0];
    }

    /**
     * 获取文件目录下所有文件(不支持过滤)
     *
     * @param dirPath
     * @return
     * @throws Exception
     */
    public static Collection<File> getAllFiles(String dirPath) throws Exception {
        File dir = new File(dirPath);
        if (!dir.exists()) throw new Exception("文件目录不存在 -> " + dirPath);
        if (!dir.isDirectory()) throw new Exception("不是文件目录 -> " + dirPath);

        Collection<File> list = FileUtils.listFiles(dir, FileFilterUtils.and(FileFilterUtils.and(new FileFilterByString(""))), null);
        return list;
    }

    /**
     * 获取文件目录下所有文件（支持过滤）
     *
     * @param dirPath
     * @param filters
     * @return
     * @throws Exception
     */
    public static Collection<File> getAllFilesWithIOFileFilter(String dirPath, IOFileFilter... filters) throws Exception {
        File dir = new File(dirPath);
        if (!dir.exists()) throw new Exception("文件目录不存在 -> " + dirPath);
        if (!dir.isDirectory()) throw new Exception("不是文件目录 -> " + dirPath);

        Collection<File> list = FileUtils.listFiles(dir, FileFilterUtils.and(FileFilterUtils.and(filters)), null);
        return list;
    }

    /**
     * 将Collection<File>转换为 File[]
     *
     * @param files
     * @return
     */
    public static File[] getCollectionToArray(Collection<File> files) {
        File[] filesArray = new File[files.size()];
        files.toArray(filesArray);
        return filesArray;
    }

    /**
     * 获取文件名数组
     *
     * @param files
     * @return
     */
    public static List<String> getFileNames(Collection<File> files) {
        List<String> fileNames = new ArrayList<>();
        files.forEach(file -> {
            fileNames.add(file.getName());
        });
        return fileNames;
    }

    /**
     * 获取文件名数组
     *
     * @param files
     * @return
     */
    public static List<String> getFileNames(File[] files) {
        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            fileNames.add(files[i].getName());
        }
        return fileNames;
    }

    /**
     * 创建文件目录
     *
     * @param dirPath
     * @throws Exception
     */
    public static void mkDirs(String dirPath) throws Exception {
        File file = new File(dirPath);
        if (file.isDirectory()) {
            if (!file.exists()) file.mkdirs();
        } else {
            throw new Exception("文件目录创建失败，不是文件目录 -> " + dirPath);
        }
    }

    /**
     * 删除文件或者文件夹
     *
     * @param file
     * @throws IOException
     */
    public void deleteFile(File file) throws IOException {
        if (file.exists()) {
            if (file.isFile()) {
                FileUtils.deleteQuietly(file);
            } else if (file.isDirectory()) {
                FileUtils.deleteDirectory(file);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String fileDirPath = "F:\\GDS\\CJLY\\ECMWF_HR\\RAIN03";
        Collection<File> files = getAllFiles(fileDirPath);
//        Collection<File> files = getAllFilesWithIOFileFilter(fileDirPath, new FileFilterByString(".060"));
        File[] filesArray = getCollectionToArray(files);
        File latestFile = findLastCreatedFile(filesArray);
        System.out.println("ttt");
    }

}
