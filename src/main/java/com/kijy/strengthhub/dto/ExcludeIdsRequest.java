package com.kijy.strengthhub.dto;

import java.util.List;

public class ExcludeIdsRequest {
    private List<Long> excludeIds;

    public List<Long> getExcludeIds() {
        return excludeIds;
    }

    public void setExcludeIds(List<Long> excludeIds) {
        this.excludeIds = excludeIds;
    }
}
