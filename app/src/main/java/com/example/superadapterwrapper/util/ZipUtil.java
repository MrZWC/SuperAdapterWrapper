package com.example.superadapterwrapper.util;

import com.idonans.lang.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/7/31
 * Time: 18:04
 */
public class ZipUtil {

    public static String gzip(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return Base64.encode(out.toByteArray());
        //return new BASE64Encoder().encode(out.toByteArray());
    }

    public static String gunzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = Base64.decode(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        return decompressed;
    }

    public static void unZipFile(String targetPath, String zipFilePath) {
        try {
            File zipFile = new File(zipFilePath);
            InputStream is = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry entry = null;
            System.out.println("开始解压:" + zipFile.getName() + "...");
            while ((entry = zis.getNextEntry()) != null) {
                String zipPath = entry.getName();
                try {
                    if (entry.isDirectory()) {
                        File zipFolder = new File(targetPath + File.separator
                                + zipPath);
                        if (!zipFolder.exists()) {
                            zipFolder.mkdirs();
                        }
                    } else {
                        File file = new File(targetPath + File.separator
                                + zipPath);
                        if (!file.exists()) {
                            File pathDir = file.getParentFile();
                            pathDir.mkdirs();
                            file.createNewFile();
                        }
                        FileOutputStream fos = new FileOutputStream(file);
                        int bread;
                        while ((bread = zis.read()) != -1) {
                            fos.write(bread);
                        }
                        fos.close();
                    }
                    System.out.println("成功解压:" + zipPath);

                } catch (Exception e) {
                    System.out.println("解压" + zipPath + "失败");
                    continue;
                }
            }
            zis.close();
            is.close();
            System.out.println("解压结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
