package edu.bu.cs673.secondhand.vo;

import java.util.List;

/***
 Email: ybinman@bu.edu
 DateTime: 10/12/24-22:48
 *****/
public class PageVo <E> {
    private List<E> list;
    private long count;

    public PageVo() {
    }

    public PageVo(List<E> list, long count) {
        this.list = list;
        this.count = count;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public long getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"list\":")
                .append(list);
        sb.append(",\"count\":")
                .append(count);
        sb.append('}');
        return sb.toString();
    }

}
