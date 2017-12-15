package com.fpliu.newton.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据魔数检测文件类型
 * 参考：http://blog.fpliu.com/it/data/file#checkType
 * 792793182@qq.com 2017-12-10
 */
public final class FileTypeChecker {

    private static final Map<String, String> map = new HashMap<>();

    static {
        map.put("RTF", "7B5C727466");
        map.put("XML", "3C3F786D6C");
        map.put("HTML", "68746D6C3E");
        map.put("PDF", "255044462D312E");

        map.put("JPEG", "FFD8FF");
        map.put("PNG", "89504E47");
        map.put("GIF", "47494638");
        map.put("TIFF", "49492A00");
        map.put("BMP", "424D");
        map.put("DWG", "41433130");
        map.put("PSD", "38425053");

        map.put("WAV", "57415645");
        map.put("MID", "4D546864");
        map.put("MP3", "494433");

        map.put("AVI", "41564920");
        map.put("RM", "2E524D46");
        map.put("FLV", "464c56");
        map.put("MOV", "6D6F6F76");
        map.put("ASF", "3026B2758E66CF11");

        map.put("ZIP", "504B0304");
        map.put("RAR", "52617221");

        map.put("CLASS", "CAFEBABE");
        map.put("DEX", "646578");
    }

    public static void put(String type, String headerHex) {
        map.put(type, headerHex);
    }

    /**
     * 获得给定文件的文件类型
     *
     * @param filePath 文件路径
     * @return 文件类型
     */
    public static String getType(String filePath) {
        // 获取文件头
        String fileHeaderHex = null;
        try {
            fileHeaderHex = getFileHeaderHex(filePath);
            if (fileHeaderHex != null && fileHeaderHex.length() > 0) {
                fileHeaderHex = fileHeaderHex.toUpperCase();

                for (String key : map.keySet()) {
                    String value = map.get(key);
                    if (fileHeaderHex.startsWith(value)) {
                        return key;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断给定的文件与期望的文件类型是否一致
     *
     * @param filePath   给定的文件路径
     * @param expectType 期望的文件类型
     * @return 是否符合预期
     */
    public static boolean check(String filePath, String expectType) {
        String expectHex = map.get(expectType.toUpperCase());
        if (expectHex == null || expectHex.length() == 0) {
            return false;
        }

        String fileHeaderHex = null;
        try {
            fileHeaderHex = getFileHeaderHex(filePath);
            if (fileHeaderHex != null && fileHeaderHex.length() > 0) {
                return fileHeaderHex.toUpperCase().startsWith(expectHex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 读取文件开头的28个字节，并转换成16进制数字字符串
     *
     * @param filePath 文件路径
     * @return 16进制数字字符串
     * @throws IOException
     */
    private static String getFileHeaderHex(String filePath) throws IOException {
        byte[] buffer = new byte[28];
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            inputStream.read(buffer, 0, buffer.length);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return bytesToHex(buffer);
    }

    /**
     * 将字节数组转换成16进制数字字符串
     *
     * @param src 字节数组
     * @return 16进制数字字符串
     */
    public static String bytesToHex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
    private static void showHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("usage:   java -jar FileTypeChecker.jar <filePath> [expectType]\n");
        stringBuilder.append("example: java -jar FileTypeChecker.jar ~/xx.png \n");
        stringBuilder.append("         java -jar FileTypeChecker.jar ~/xx.png PNG\n");
        System.out.println(stringBuilder.toString());
    }

    /*
     * 支持命令行运行
     */
    public static void main(String[] args) {
        int length = args.length;
        if (length == 0) {
            showHelp();
        } else if (length == 1) {
            if("-h".equals(args[0]) || "--help".equals(args[0])) {
                showHelp();
            } else {
                System.out.println(getType(args[0]));
            }
        } else if (length > 1) {
            System.out.println(check(args[0], args[1]));
        }
    }
}
