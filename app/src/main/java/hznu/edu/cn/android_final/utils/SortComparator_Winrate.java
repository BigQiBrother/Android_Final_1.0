package hznu.edu.cn.android_final.utils;

import java.util.Comparator;

import hznu.edu.cn.android_final.beans.RaidersItem;

public class SortComparator_Winrate implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {
        RaidersItem a = (RaidersItem) lhs;
        RaidersItem b = (RaidersItem) rhs;

        return (Float.parseFloat(b.getWin_rate().split("%")[0]) - Float.parseFloat(a.getWin_rate().split("%")[0]))>0?1:-1;
    }
}
