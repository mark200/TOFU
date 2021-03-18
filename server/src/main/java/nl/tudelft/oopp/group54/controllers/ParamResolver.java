package nl.tudelft.oopp.group54.controllers;

import java.util.List;
import java.util.Map;

public class ParamResolver {

    public static boolean checkContainsRequiredParams(Map<String, Object> payload, List<String> requiredParams) {

        for (String s : requiredParams) {
            if (!payload.containsKey(s)) {
                return false;
            }
        }

        return true;

    }

}
