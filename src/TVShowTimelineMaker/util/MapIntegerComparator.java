/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TVShowTimelineMaker.util;

import java.util.Map;
import java.util.logging.Logger;


public class MapIntegerComparator<T> implements java.util.Comparator<Map.Entry<T, Integer>>{

    private static final Logger LOG = Logger.getLogger(MapIntegerComparator.class.getName());

    @Override
    public int compare(Map.Entry<T, Integer> o1, Map.Entry<T, Integer> o2) {
        return Integer.compare(o1.getValue(), o2.getValue());
    }

    
    
}
