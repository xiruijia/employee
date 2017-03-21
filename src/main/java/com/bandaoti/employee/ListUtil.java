package com.bandaoti.employee;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
	public static <T> List<T>  asList(@SuppressWarnings("unchecked") T... as){
		List<T> list=new ArrayList<T>();
		for(T a:as){
			list.add(a);
		}
		return list;
	}
}
