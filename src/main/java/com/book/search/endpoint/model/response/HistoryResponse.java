package com.book.search.endpoint.model.response;

import com.book.search.common.data.entity.History;
import com.book.search.common.util.DateTimeUtils;
import com.book.search.endpoint.SerializedFieldNames;
import com.book.search.endpoint.model.HistoryData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
public class HistoryResponse {

    @JsonProperty(SerializedFieldNames.TOTAL_COUNT)
    private long totalCount;
    @JsonProperty(SerializedFieldNames.PAGECOUNT)
    private int pageCount;

    private List<HistoryData> histories = new ArrayList<>();

    public HistoryResponse(Page<History> historyList) {
        this.totalCount = historyList.getTotalElements();
        this.pageCount = historyList.getTotalPages();

        if (historyList != null) {
            historyList.getContent().
                    stream().
                    forEach(history -> histories.add(new HistoryData(history.getKeyword(),
                            DateTimeUtils.getDateTimeToString(history.getCreatedTime()))));
        }
    }
}
