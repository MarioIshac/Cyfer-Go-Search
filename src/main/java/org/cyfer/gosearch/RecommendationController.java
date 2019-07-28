package org.cyfer.gosearch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {
    @GetMapping("/recommendation")
    public void getRecommendation(final @RequestParam("hashtag") String hashtag, final @RequestParam("topLocationsCount") int topLocationsCount) {
        PythonInterpreter
    }
}
