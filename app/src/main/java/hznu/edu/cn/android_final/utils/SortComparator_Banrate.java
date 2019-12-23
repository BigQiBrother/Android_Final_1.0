package hznu.edu.cn.android_final.utils;

import java.util.Comparator;

import hznu.edu.cn.android_final.beans.RaidersItem;


public class SortComparator_Banrate implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {
        RaidersItem a = (RaidersItem) lhs;
        RaidersItem b = (RaidersItem) rhs;

        return (Float.parseFloat(b.getBan_rate().split("%")[0]) - Float.parseFloat(a.getBan_rate().split("%")[0]))>0?1:-1;
    }
}
