package top.fuqingchen.machinedesign;

/**
 * 拉格朗日插值
 * 要求输入一个二维数组data[2][j],以及要求的数x0;
 * 其中：x[0][j]为x值,y[1][j]为对应的y值,x0为需要拟合的值
 *
 * @author Fu_Qingchen
 */

public class InterpolationInMath {
    private double[][] data = {};    //原始数据
    static double[] n = {};   //最终结果的一项
    private double number = 0.0;   //最终结果

    public double getNumber() {
        return number;
    }

    public void setData(double[][] data) {
        this.data = data;
    }

    //返回拉格朗日基函数
    private double[] l(double x) {
        double[] lk = new double[data[0].length];
        for (int k = 0; k < data[0].length; k++) {
            double up = 1;  //分子
            double down = 1;    //分母
            for (int i = 0; i < data[0].length; i++) {
                if (i != k) {
                    up *= (x - data[0][i]);
                    down *= (data[0][k] - data[0][i]);
                }
            }
            lk[k] = up / down;
        }
        return lk;
    }

    public void setNumber(double xj) {
        n = new double[data[0].length];
        for (int i = 0; i < data[0].length; i++) {
            n[i] = (data[1][i] * l(xj)[i]);
            number += n[i];
        }
    }
}
