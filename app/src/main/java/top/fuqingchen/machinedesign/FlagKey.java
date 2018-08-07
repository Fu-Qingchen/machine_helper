package top.fuqingchen.machinedesign;

/**
 * 基于 GB/T 1095-2003, GB/T 1095-2003;
 *
 * @author Fu_Qingchen
 */
public class FlagKey {
    /**
     * 键尺寸b×h 基本尺寸系列
     */
    private final static int[][] BH = {{2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {8, 7}, {10, 8}, {12, 8},
            {14, 9}, {16, 10}, {18, 11}, {20, 12}, {22, 14}, {25, 14}, {28, 16}, {32, 18}, {36, 20},
            {40, 22}, {45, 25}, {50, 28}, {56, 32}, {63, 32}, {70, 36}, {80, 40}, {90, 45}, {100, 50}};

    /**
     * 轴t1毂t2 基本尺寸系列
     */
    private final static double[][] T1T2 = {{1.2, 1}, {1.8, 1.4}, {2.5, 1.8}, {3.0, 2.3}, {3.5, 2.8}, {4.0, 3.3},
            {5.0, 3.3}, {5.0, 3.3}, {5.5, 3.8}, {6.0, 4.3}, {7.0, 4.4}, {7.5, 4.9}, {9.0, 5.4},
            {9.0, 5.4}, {10.0, 6.4}, {11.0, 7.4}, {12.0, 8.4}, {13.0, 9.4}, {15.0, 10.4}, {17.0, 11.4},
            {20.0, 12.4}, {20.0, 12.4}, {22.0, 14.4}, {25.0, 15.4}, {28.0, 17.4}, {31.0, 19.5}};

    private final static int[] D = {6, 8, 10, 12, 17, 22, 30, 38, 44, 50, 58, 65, 75, 85, 95, 110, 130,
            150, 170, 200, 230, 260, 290, 330, 380, 440, 500};

    /**
     * 键长L 基本尺寸系列
     */
    private final static int[] L = {6, 8, 10, 12, 14, 16, 18, 20, 22, 25, 28, 32, 36, 40, 45, 50, 56, 63,
            70, 80, 90, 100, 110, 125, 140, 160, 180, 200, 220, 250, 280, 320, 360, 400, 450, 500};

    /**
     * b_limits[i][0]:  较松连接  轴   上偏差
     * b_limits[i][1]:  较松连接  毂   上偏差
     * b_limits[i][2]:  较松连接  毂   下偏差
     * b_limits[i][3]:  一般连接  轴   上偏差
     * b_limits[i][4]:  一般连接  轴   下偏差
     * b_limits[i][5]:  一般连接  毂   基本偏差
     * b_limits[i][6]:  较紧连接  轴毂 上偏差
     * b_limits[i][7]:  较紧连接  轴毂 下偏差
     */
    private final static double B_LIMITS[][] = {
            {0.025, 0.060, 0.020, -0.004, -0.029, 0.0125, -0.006, -0.031},
            {0.030, 0.078, 0.030, 0, -0.030, 0.015, -0.012, -0.042},
            {0.036, 0.098, 0.040, 0, -0.036, 0.018, -0.015, -0.051},
            {0.043, 0.120, 0.050, 0, -0.043, 0.0215, -0.018, -0.061},
            {0.052, 0.149, 0.065, 0, -0.052, 0.026, -0.022, -0.074},
            {0.062, 0.180, 0.080, 0, -0.062, 0.031, -0.026, -0.088},
            {0.074, 0.220, 0.100, 0, -0.074, 0.037, -0.032, -0.106},
            {0.087, 0.260, 0.120, 0, -0.087, 0.0435, -0.037, -0.124},
    };

    /**
     * 轴t1毂t2 极限偏差
     */
    private final static double T1T2_LIMITS[] = {0.1, 0.2, 0.3};

    /**
     * 半径r min&max
     */
    private final static double R_LIMITS[][] = {{0.08, 0.16}, {0.16, 0.25}, {0.25, 0.40}, {0.40, 0.60},
            {0.70, 1.00}, {1.20, 1.60}, {2.00, 2.50}};

    /**
     * 单个平键参数
     */
    private int b, h, l;
    private double t1, t2, b1_low, b1_up, b2_low, b2_up, t1_up, t2_up, r_min, r_max;
    private static final double t1_low = 0, t2_low = 0;
    private String type1, type2;

    /**
     * @param ld 轴段的公称长度
     */
    public void setL(int ld) {
        int lValue = 0;
        for (int i = 0; ld > L[i]; i++) {
            lValue = i;
        }
        l = L[lValue];
    }

    /**
     * @param d 轴的公称直径
     */
    public void setD(double d) {
        int caseValue = 0;
        for (int i = 0; d > D[i]; i++) {
            caseValue = i;
        }
        b = BH[caseValue][0];
        h = BH[caseValue][1];
        t1 = T1T2[caseValue][0];
        t2 = T1T2[caseValue][1];
        switch (caseValue) {
            case 0:
            case 1:
            case 2:
                r_min = R_LIMITS[0][0];
                r_max = R_LIMITS[0][1];
                break;
            case 3:
            case 4:
            case 5:
                r_min = R_LIMITS[1][0];
                r_max = R_LIMITS[1][1];
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                r_min = R_LIMITS[2][0];
                r_max = R_LIMITS[2][1];
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                r_min = R_LIMITS[3][0];
                r_max = R_LIMITS[3][1];
                break;
            case 16:
            case 17:
            case 18:
            case 19:
                r_min = R_LIMITS[4][0];
                r_max = R_LIMITS[4][1];
                break;
            case 20:
            case 21:
            case 22:
                r_min = R_LIMITS[5][0];
                r_max = R_LIMITS[5][1];
                break;
            case 23:
            case 24:
            case 25:
                r_min = R_LIMITS[6][0];
                r_max = R_LIMITS[6][1];
                break;
        }
        switch (caseValue) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                t1_up = t2_up = T1T2_LIMITS[0];
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                t1_up = t2_up = T1T2_LIMITS[1];
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                t1_up = t2_up = T1T2_LIMITS[2];
                break;
        }
    }

    /**
     * @param d    轴的公称直径
     * @param type =0 较松连接(轴H9/毂D10)
     *             =1 正常连接(轴N9/毂JS9)
     *             =2 紧密连接(轴P9/毂P9)
     */
    public void setType(double d, int type) {
        int value = 0;
        int caseValue = 0;
        for (int i = 0; d > D[i]; i++) {
            caseValue = i;
        }
        switch (caseValue) {
            case 0:
            case 1:
                switch (type) {
                    case 0:
                        b1_up = B_LIMITS[value][0];
                        b1_low = 0;
                        b2_up = B_LIMITS[value][1];
                        b2_low = B_LIMITS[value][2];
                        type1 = "H9";
                        type2 = "D10";
                        break;
                    case 2:
                        b1_up = b2_up = B_LIMITS[value][6];
                        b1_low = b2_low = B_LIMITS[value][7];
                        type1 = "P9";
                        type2 = "P9";
                        break;
                    default:
                        b1_up = B_LIMITS[value][3];
                        b1_low = B_LIMITS[value][4];
                        b2_up = B_LIMITS[value][5];
                        b2_low = -b2_up;
                        type1 = "N9";
                        type2 = "JS9";
                }
                break;
            case 2:
            case 3:
            case 4:
                value = 1;
                switch (type) {
                    case 0:
                        b1_up = B_LIMITS[value][0];
                        b1_low = 0;
                        b2_up = B_LIMITS[value][1];
                        b2_low = B_LIMITS[value][2];
                        type1 = "H9";
                        type2 = "D10";
                        break;
                    case 2:
                        b1_up = b2_up = B_LIMITS[value][6];
                        b1_low = b2_low = B_LIMITS[value][7];
                        type1 = "P9";
                        type2 = "P9";
                        break;
                    default:
                        b1_up = B_LIMITS[value][3];
                        b1_low = B_LIMITS[value][4];
                        b2_up = B_LIMITS[value][5];
                        b2_low = -b2_up;
                        type1 = "N9";
                        type2 = "JS9";
                }
                break;
            case 5:
            case 6:
                value = 2;
                switch (type) {
                    case 0:
                        b1_up = B_LIMITS[value][0];
                        b1_low = 0;
                        b2_up = B_LIMITS[value][1];
                        b2_low = B_LIMITS[value][2];
                        type1 = "H9";
                        type2 = "D10";
                        break;
                    case 2:
                        b1_up = b2_up = B_LIMITS[value][6];
                        b1_low = b2_low = B_LIMITS[value][7];
                        type1 = "P9";
                        type2 = "P9";
                        break;
                    default:
                        b1_up = B_LIMITS[value][3];
                        b1_low = B_LIMITS[value][4];
                        b2_up = B_LIMITS[value][5];
                        b2_low = -b2_up;
                        type1 = "N9";
                        type2 = "JS9";
                }
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                value = 3;
                switch (type) {
                    case 0:
                        b1_up = B_LIMITS[value][0];
                        b1_low = 0;
                        b2_up = B_LIMITS[value][1];
                        b2_low = B_LIMITS[value][2];
                        type1 = "H9";
                        type2 = "D10";
                        break;
                    case 2:
                        b1_up = b2_up = B_LIMITS[value][6];
                        b1_low = b2_low = B_LIMITS[value][7];
                        type1 = "P9";
                        type2 = "P9";
                        break;
                    default:
                        b1_up = B_LIMITS[value][3];
                        b1_low = B_LIMITS[value][4];
                        b2_up = B_LIMITS[value][5];
                        b2_low = -b2_up;
                        type1 = "N9";
                        type2 = "JS9";
                }
                break;
            case 11:
            case 12:
            case 13:
            case 14:
                value = 4;
                switch (type) {
                    case 0:
                        b1_up = B_LIMITS[value][0];
                        b1_low = 0;
                        b2_up = B_LIMITS[value][1];
                        b2_low = B_LIMITS[value][2];
                        type1 = "H9";
                        type2 = "D10";
                        break;
                    case 2:
                        b1_up = b2_up = B_LIMITS[value][6];
                        b1_low = b2_low = B_LIMITS[value][7];
                        type1 = "P9";
                        type2 = "P9";
                        break;
                    default:
                        b1_up = B_LIMITS[value][3];
                        b1_low = B_LIMITS[value][4];
                        b2_up = B_LIMITS[value][5];
                        b2_low = -b2_up;
                        type1 = "N9";
                        type2 = "JS9";
                }
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                value = 5;
                switch (type) {
                    case 0:
                        b1_up = B_LIMITS[value][0];
                        b1_low = 0;
                        b2_up = B_LIMITS[value][1];
                        b2_low = B_LIMITS[value][2];
                        type1 = "H9";
                        type2 = "D10";
                        break;
                    case 2:
                        b1_up = b2_up = B_LIMITS[value][6];
                        b1_low = b2_low = B_LIMITS[value][7];
                        type1 = "P9";
                        type2 = "P9";
                        break;
                    default:
                        b1_up = B_LIMITS[value][3];
                        b1_low = B_LIMITS[value][4];
                        b2_up = B_LIMITS[value][5];
                        b2_low = -b2_up;
                        type1 = "N9";
                        type2 = "JS9";
                }
                break;
            case 20:
            case 21:
            case 22:
            case 23:
                value = 6;
                switch (type) {
                    case 0:
                        b1_up = B_LIMITS[value][0];
                        b1_low = 0;
                        b2_up = B_LIMITS[value][1];
                        b2_low = B_LIMITS[value][2];
                        type1 = "H9";
                        type2 = "D10";
                        break;
                    case 2:
                        b1_up = b2_up = B_LIMITS[value][6];
                        b1_low = b2_low = B_LIMITS[value][7];
                        type1 = "P9";
                        type2 = "P9";
                        break;
                    default:
                        b1_up = B_LIMITS[value][3];
                        b1_low = B_LIMITS[value][4];
                        b2_up = B_LIMITS[value][5];
                        b2_low = -b2_up;
                        type1 = "N9";
                        type2 = "JS9";
                }
                break;
            case 24:
            case 25:
                value = 7;
                switch (type) {
                    case 0:
                        b1_up = B_LIMITS[value][0];
                        b1_low = 0;
                        b2_up = B_LIMITS[value][1];
                        b2_low = B_LIMITS[value][2];
                        type1 = "H9";
                        type2 = "D10";
                        break;
                    case 2:
                        b1_up = b2_up = B_LIMITS[value][6];
                        b1_low = b2_low = B_LIMITS[value][7];
                        type1 = "P9";
                        type2 = "P9";
                        break;
                    default:
                        b1_up = B_LIMITS[value][3];
                        b1_low = B_LIMITS[value][4];
                        b2_up = B_LIMITS[value][5];
                        b2_low = -b2_up;
                        type1 = "N9";
                        type2 = "JS9";
                }
                break;
        }

    }

    public String getType1() {
        return type1;
    }

    public String getType2() {
        return type2;
    }

    public int getB() {
        return b;
    }

    public int getH() {
        return h;
    }

    public int getL() {
        return l;
    }

    public double getT1() {
        return t1;
    }

    public double getT1_low() {
        return t1_low;
    }

    public double getT1_up() {
        return t1_up;
    }

    public double getT2() {
        return t2;
    }

    public double getT2_low() {
        return t2_low;
    }

    public double getT2_up() {
        return t2_up;
    }

    public double getR_min() {
        return r_min;
    }

    public double getR_max() {
        return r_max;
    }

    public double getB1_low() {
        return b1_low;
    }

    public double getB1_up() {
        return b1_up;
    }

    public double getB2_low() {
        return b2_low;
    }

    public double getB2_up() {
        return b2_up;
    }

    @Override
    public String toString() {
        double b1low = b1_low + b;
        double b2low = b2_low + b;
        double b1up = b1_up + b;
        double b2up = b2_up + b;
        return "键 " + b + " × " + h + " × " + l + "\n" +
                "轴深 t1\t=\t" + t1 + "\t(" + t1_low + "\t,\t" + t1_up + ")\n" +
                "毂深 t2\t=\t" + t2 + "\t(" + t2_low + "\t,\t" + t2_up + ")\n" +
                "半径 r\t∈\t" + "(" + r_min + "\t,\t" + r_max + ")\n" +
                "对轴 b\t∈\t" + "(" + b1low + "\t,\t" + b1up + ")\n" +
                "对毂 b\t∈\t" + "(" + b2low + "\t,\t" + b2up + ")\n";
    }
}
