package org.cyfer.gosearch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {
    @GetMapping("/recommendation/{hashtag}")
    public void getRecommendation(final @PathVariable("hashtag") String hashtag) {

    }
}
