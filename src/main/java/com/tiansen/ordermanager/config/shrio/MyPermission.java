package com.tiansen.ordermanager.config.shrio;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyPermission extends WildcardPermission {

    public MyPermission(String permissionString) {
        super(permissionString);
    }

    @Override
    protected void setParts(String wildcardString, boolean caseSensitive) {
        wildcardString = StringUtils.clean(wildcardString);

        if (wildcardString == null || wildcardString.isEmpty()) {
            throw new IllegalArgumentException("Wildcard string cannot be null or empty. Make sure permission strings are properly formatted.");
        }

        if (!caseSensitive) {
            wildcardString = wildcardString.toLowerCase();
        }

        List<String> parts = CollectionUtils.asList(wildcardString.split(PART_DIVIDER_TOKEN));

        List<Set<String>> parentParts = new ArrayList<>();
        for (String part : parts) {
//            Set<String> subparts = CollectionUtils.asSet(part.split(SUBPART_DIVIDER_TOKEN));
            // 防止分割正则中的","
            Set<String> subparts = CollectionUtils.asSet(part);

            if (subparts.isEmpty()) {
                throw new IllegalArgumentException("Wildcard string cannot contain parts with only dividers. Make sure permission strings are properly formatted.");
            }
            parentParts.add(subparts);
        }
        setParts(parentParts);

        if (parentParts.isEmpty()) {
            throw new IllegalArgumentException("Wildcard string cannot contain only dividers. Make sure permission strings are properly formatted.");
        }
    }

    @Override
    public boolean implies(Permission p) {
        if (!(p instanceof WildcardPermission)) {
            return false;
        }

        MyPermission wp = (MyPermission) p;

        List<Set<String>> otherParts = wp.getParts();

        int i = 0;
        for (Set<String> otherPart : otherParts) {
            // If this permission has less parts than the other permission, everything after the number of parts contained
            // in this permission is automatically implied, so return true
            if (getParts().size() - 1 < i) {
                return true;
            } else {
                Set<String> part = getParts().get(i);
                if (!part.contains(WILDCARD_TOKEN) && !part.containsAll(otherPart)) {
                    // 项目里默认size都是1
                    if (part.size() ==1 && otherPart.size() == 1) {
                        // pt: get/user/detail/**  ot: get/user/detail/1
                        try {
                            String regex = "^" + part.toArray(new String[1])[0];
//                            String regex = "^delete/user[a-zA-Z0-9/]*";
                            String otp = otherPart.toArray(new String[1])[0];
                            if (otp.matches(regex)) {
                                return true;
                            }
                        } catch (Exception e) {
                            return false;
                        }
                    }
                    return false;
                }
                i++;
            }
        }

        // If this permission has more parts than the other parts, only imply it if all of the other parts are wildcards
        for (; i < getParts().size(); i++) {
            Set<String> part = getParts().get(i);
            if (!part.contains(WILDCARD_TOKEN)) {
                return false;
            }
        }

        return true;
    }

}
