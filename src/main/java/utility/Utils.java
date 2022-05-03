package utility;

import entity.Meta;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    @SneakyThrows
    public static <T> T mergeObjects(T first, T second){
        Class<?> clas = first.getClass();
        Field[] fields = clas.getDeclaredFields();
        Object result = null;
            result = clas.getDeclaredConstructor().newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value1 = field.get(first);
                Object value2 = field.get(second);
                Object value = (value1 != null) ? value1 : value2;
                field.set(result, value);
            }

        return (T) result;
    }

    public static List<Map<String, String>> getMetaList(Meta metaBefore, Meta metaAfter){
        List<Map<String, String>> metaList = new ArrayList<>();

        if(!metaBefore.getTitle().trim().equals(metaAfter.getTitle().trim())){
            Map<String, String> titleMap = new HashMap<>();
            titleMap.put("field", "title");
            titleMap.put("before", metaBefore.getTitle());
            titleMap.put("after", metaAfter.getTitle());
            metaList.add(titleMap);
        }
        if(!metaBefore.getStartTime().trim().equals(metaAfter.getStartTime().trim())){
            Map<String, String> startTimeMap = new HashMap<>();
            startTimeMap.put("field", "startTime");
            startTimeMap.put("before", metaBefore.getStartTime());
            startTimeMap.put("after", metaBefore.getStartTime());
            metaList.add(startTimeMap);
        }
        if(!metaBefore.getEndTime().trim().equals(metaAfter.getEndTime().trim())){
            Map<String, String> endTimeMap = new HashMap<>();
            endTimeMap.put("field", "endTime");
            endTimeMap.put("before", metaBefore.getEndTime());
            endTimeMap.put("after", metaAfter.getEndTime());
            metaList.add(endTimeMap);
        }
       return metaList;
    }


}
