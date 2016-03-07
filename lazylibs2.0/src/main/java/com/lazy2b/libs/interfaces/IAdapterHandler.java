package com.lazy2b.libs.interfaces;

import java.util.List;

/**
 * Created by Administrator on 2016/2/24.
 */
public interface IAdapterHandler<T> {

    void addList(List<T> _list);

    List<T> getList();

    void setList(List<T> _list);

}
