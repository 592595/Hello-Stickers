package com.iheshulin.yourname.util;

import java.awt.image.BufferedImage;

/**
 * Created by LC on 2017/5/30.
 */
public class MatchingAnalysisTool {

    public MatchingAnalysisTool(String event1, String event2){
        this.event1=event1;
        this.event2=event2;
    }
    private String event1;
    private String event2;
    private BufferedImage image1;
    private BufferedImage image2;
    public String getEvent1() {
        return event1;
    }

    public void setEvent1(String event1) {
        this.event1 = event1;
    }

    public String getEvent2() {
        return event2;
    }

    public void setEvent2(String event2) {
        this.event2 = event2;
    }

    public BufferedImage getImage1() {
        return image1;
    }

    public void setImage1(BufferedImage image1) {
        this.image1 = image1;
    }

    public BufferedImage getImage2() {
        return image2;
    }

    public void setImage2(BufferedImage image2) {
        this.image2 = image2;
    }
    private class ContentAnalysis{
        private int compare(String str, String target) {
            int d[][]; // 矩阵
            int n = str.length();
            int m = target.length();
            int i; // 遍历str的
            int j; // 遍历target的
            char ch1; // str的
            char ch2; // target的
            int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
            if (n == 0) {
                return m;
            }
            if (m == 0) {
                return n;
            }
            d = new int[n + 1][m + 1];
            for (i = 0; i <= n; i++) { // 初始化第一列
                d[i][0] = i;
            }

            for (j = 0; j <= m; j++) { // 初始化第一行
                d[0][j] = j;
            }

            for (i = 1; i <= n; i++) { // 遍历str
                ch1 = str.charAt(i - 1);
                // 去匹配target
                for (j = 1; j <= m; j++) {
                    ch2 = target.charAt(j - 1);
                    if (ch1 == ch2) {
                        temp = 0;
                    } else {
                        temp = 1;
                    }

                    // 左边+1,上边+1, 左上角+temp取最小
                    d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
                }
            }
            return d[n][m];
        }



        private int min(int one, int two, int three) {
            return (one = one < two ? one : two) < three ? one : three;
        }



        /**

         * 获取两字符串的相似度

         *

         * @param str

         * @param target

         * @return

         */

        public float getSimilarityRatio(String str, String target) {
            return 1 - (float)compare(str, target)/Math.max(str.length(), target.length());
        }
    }

    private class ImageAnalysis{
        // 改变成二进制码
        private  String[][] getPX(BufferedImage image) {
            int[] rgb = new int[3];
            int width = image.getWidth();
            int height = image.getHeight();
            int minx = image.getMinX();
            int miny = image.getMinY();
            String[][] list = new String[width][height];
            for (int i = minx; i < width; i++) {
                for (int j = miny; j < height; j++) {
                    int pixel = image.getRGB(i, j);
                    rgb[0] = (pixel & 0xff0000) >> 16;
                    rgb[1] = (pixel & 0xff00) >> 8;
                    rgb[2] = (pixel & 0xff);
                    list[i][j] = rgb[0] + "," + rgb[1] + "," + rgb[2];
                }
            }
            return list;
        }


        public  Double compareImage(BufferedImage image1, BufferedImage image2) {
            Double result = 0.00;
            // 分析图片相似度 begin
            String[][] list1 = getPX(image1);
            String[][] list2 = getPX(image2);
            Double xiangsi = 0.00;
            Double busi = 0.00;
            int i = 0, j = 0;

            for (String[] strings : list1) {
                if ((i + 1) == list1.length) {
                    continue;
                }
                for (int m = 0; m < strings.length; m++) {
                    try {
                        String[] value1 = list1[i][j].toString().split(",");
                        String[] value2 = list2[i][j].toString().split(",");
                        int k = 0;
                        for (int n = 0; n < value2.length; n++) {
                            if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 3) {
                                xiangsi++;
                            } else {
                                busi++;
                            }
                        }
                    } catch (RuntimeException e) {
                        continue;
                    }
                    j++;
                }
                i++;
            }
            list1 = getPX(image2);
            list2 = getPX(image1);
            i = 0;
            j = 0;

            for (String[] strings : list1) {
                if ((i + 1) == list1.length) {
                    continue;
                }
                for (int m = 0; m < strings.length; m++) {
                    try {
                        String[] value1 = list1[i][j].toString().split(",");
                        String[] value2 = list2[i][j].toString().split(",");
                        int k = 0;
                        for (int n = 0; n < value2.length; n++) {
                            if (Math.abs(Integer.parseInt(value1[k]) - Integer.parseInt(value2[k])) < 3) {
                                xiangsi++;
                            } else {
                                busi++;
                            }
                        }
                    } catch (RuntimeException e) {
                        continue;
                    }
                    j++;
                }
                i++;
            }



            return xiangsi/(xiangsi+busi);
        }
    }

    public double similarityDegree() throws Exception{
        ContentAnalysis contentAnalysis = new ContentAnalysis();
        ImageAnalysis imageAnalysis = new ImageAnalysis();
        return contentAnalysis.getSimilarityRatio(this.event1, this.event2);// + imageAnalysis.compareImage(this.image1, this.image2))/2;
    }
}
