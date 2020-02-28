package com.incar.contest_ds.util;


import com.incar.contest_ds.bean.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 轨迹算法 - 优化压缩
 *
 * @author Kong, created on 2020-02-28T15:32.
 * @since 1.0.0-SNAPSHOT
 */
public class TrajectoryCompressionUtil {


//    public static void main(String[] args) {
//
//        List<Point> points = new ArrayList<Point>() {
//            {
//                add(new Point(1, 114.4036630000D, 30.4757560000D));
//                add(new Point(2, 114.4035880000D, 30.4754320000D));
//                add(new Point(3,114.4035120000D, 30.4750990000D));
//                add(new Point(4,114.4034590000D, 30.4748500000D));
//                add(new Point(5,114.4032120000D, 30.4749700000D));
//                add(new Point(6,114.4027180000D, 30.4751730000D));
//                add(new Point(7,114.4027720000D, 30.4755060000D));
//                add(new Point(8,114.4028360000D, 30.4762640000D));
//                add(new Point(9,114.4029970000D, 30.4772900000D));
//                add(new Point(10,114.4030510000D, 30.4782240000D));
//                add(new Point(11,114.4031690000D, 30.4791860000D));
//                add(new Point(12,114.4032650000D, 30.4798420000D));
//                add(new Point(13,114.4042740000D, 30.4797310000D));
//                add(new Point(14,114.4052610000D, 30.4797400000D));
//                add(new Point(15,114.4062590000D, 30.4795830000D));
//                add(new Point(16,114.4073960000D, 30.4794440000D));
//                add(new Point(17,114.4088980000D, 30.4793330000D));
//                add(new Point(18,114.4097240000D, 30.4786120000D));
//                add(new Point(19,114.4097880000D, 30.4780670000D));
//                add(new Point(20,114.4096600000D, 30.4772900000D));
//                add(new Point(21,114.4095310000D, 30.4770500000D));
//                add(new Point(22,114.4086830000D, 30.4770220000D));
//                add(new Point(23,114.4079110000D, 30.4771420000D));
//                add(new Point(24,114.4078360000D, 30.4768920000D));
//            }
//        };
//
//        double dMax = 2.0;
//        List<Point> pointList = TrajectoryOptimize(points, dMax);
//
//        System.out.println(pointList.size());
//
//    }

    /**
     * 功能：轨迹压缩
     * @param points 原始经纬度
     * @param dMax：最大距离误差
     * @return 压缩后轨迹数据
     */
    public static List<Point> TrajectoryOptimize(List<Point> points, double dMax) {
        if (null == points || points.size() < 1) {
            throw new RuntimeException("points is null or too less");
        }
        // 过滤后的轨迹点
        List<Point> pointsFilter = new ArrayList<>();

        // 获取第一个原始经纬度点坐标并添加到过滤后的数组中
        pointsFilter.add(points.get(0));
        //起始下标
        int start = 0;
        //结束下标
        int end = points.size() - 1;
        //DP压缩算法
        TraCompress(points, pointsFilter, start, end, dMax);

        //获取最后一个原始经纬度点坐标并添加到过滤后的数组中
        pointsFilter.add(points.get(points.size() - 1));

        // 排序 - 升序
        Collections.sort(pointsFilter);
        // 后面要根据别的数据进行排序
//        Collections.sort(pointsFilter, (obj1, obj2) -> {
//            // 升序
//            if (obj1.getId() > obj2.getId()) {
//                return 1;
//            }
//            if (obj1.getId() == obj2.getId()) {
//                return 0;
//            }
//            return -1;
//        });

        return pointsFilter;
    }

    /**
     * 功能：使用三角形面积（使用海伦公式求得）相等方法计算点pointX到点pointA和pointB所确定的直线的距离
     * @param pointA：起始点
     * @param pointB：结束点
     * @param pointX：第三个点
     * @return distance：点pointX到pointA和pointB所在直线的距离
     */
    public static double distToSegment(Point pointA, Point pointB, Point pointX){
        double a = Math.abs(geoDist(pointA, pointB));
        double b = Math.abs(geoDist(pointA, pointX));
        double c = Math.abs(geoDist(pointB, pointX));
        double p = (a + b + c) / 2.0;
        double s = Math.sqrt(Math.abs(p * (p - a) * (p - b) * (p - c)));
        return s * 2.0 / a;
    }
    /**
     * 功能：求两个经纬度点之间的距离
     * @param pointA：起始点
     * @param pointB：结束点
     * @return distance：距离
     */
    public static double geoDist(Point pointA, Point pointB) {
        double radLat1 = Rad(pointA.latitude);
        double radLat2 = Rad(pointB.latitude);
        double deltaLon = Rad(pointB.longitude - pointA.longitude);
        double top1 = Math.cos(radLat2) * Math.sin(deltaLon);
        double top2 = Math.cos(radLat1) * Math.sin(radLat2) - Math.sin(radLat1) * Math.cos(radLat2) * Math.cos(deltaLon);
        double top = Math.sqrt(top1 * top1 + top2 * top2);
        double bottom = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(deltaLon);
        double deltaSigma = Math.atan2(top, bottom);
        return deltaSigma * 6378137.0;
    }
    /**
     * 功能：角度转弧度
     * @param d：角度
     * @return 返回的是弧度
     */
    public static double Rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 功能：根据最大距离限制，采用DP方法递归的对原始轨迹进行采样，得到压缩后的轨迹
     * @param points：原始经纬度坐标点数组
     * @param pointsFilter：保持过滤后的点坐标数组
     * @param start：起始下标
     * @param end：终点下标
     * @param dMax：预先指定好的最大距离误差
     */
    public static void TraCompress(List<Point> points, List<Point> pointsFilter,
                                    int start, int end, double dMax){
        if(start < end){
            //最大距离
            double maxDist = 0;
            //当前下标
            int cur_pt = 0;

            for (int i = start + 1; i < end; i++){
                //当前点到对应线段的距离
                double curDist = distToSegment(points.get(start), points.get(end), points.get(i));
                //求出最大距离及最大距离对应点的下标
                if(curDist > maxDist){
                    maxDist = curDist;
                    cur_pt = i;
                }
            }
            //若当前最大距离大于最大距离误差
            if(maxDist >= dMax){
                pointsFilter.add(points.get(cur_pt));
                //将当前点加入到过滤数组中
                //将原来的线段以当前点为中心拆成两段，分别进行递归处理
                TraCompress(points, pointsFilter, start, cur_pt, dMax);
                TraCompress(points, pointsFilter, cur_pt, end, dMax);
            }
        }
    }
}
