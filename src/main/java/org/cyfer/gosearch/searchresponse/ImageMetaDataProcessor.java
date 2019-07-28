package org.cyfer.gosearch.searchresponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;

import java.util.Map;
import java.util.function.Function;

public interface ImageMetaDataProcessor<MD> {
    Map<String, Function<? super MD, Object>> getTemplateMappings();

    default Iterable<String> getCSVHeaders() {
        return getTemplateMappings().keySet();
    }

    default String getCSVHeadersRow() {
        return String.join(",", getCSVHeaders());
    }
}
