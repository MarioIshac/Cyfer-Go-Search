package org.cyfer.gosearch;

import lombok.Value;
import lombok.val;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class ImageForm {
    String description;
    double weight;
    String location;
    String user;
    String timestamp;
    String image;

    public ImageFormDisambiguator getDisambiguator() {
        return new ImageFormDisambiguator();
    }

    public class ImageFormDisambiguator implements Comparable<ImageFormDisambiguator> {
        public ImageForm getImageForm() {
            return ImageForm.this;
        }

        @Override
        public boolean equals(Object otherObj) {
            val otherDisambiguator = (ImageFormDisambiguator) otherObj;

            val locationsMatch = getLocation().equals(otherDisambiguator.getImageForm().getLocation());
            val usersMatch = getLocation().equals(otherDisambiguator.getImageForm().getUser());

            return locationsMatch && usersMatch;
        }

        @Override
        public int compareTo(final ImageFormDisambiguator otherDisambiguator) {
            val otherImageForm = otherDisambiguator.getImageForm();

            val imageDateTime = LocalDateTime.parse(getTimestamp());
            val otherImageDateTime = LocalDateTime.parse(otherImageForm.getTimestamp());

            if (imageDateTime.isEqual(otherImageDateTime)) {
                return 0;
            }

            return imageDateTime.isBefore(otherImageDateTime) ? -1 : 1;
        }
    }

    private static final Pattern HASHTAG_EXTRACTOR = Pattern.compile("#(\\w+)");

    private static final int DESCRIPTION_COLUMN = 0;
    private static final int WEIGHT_COLUMN = 1;
    private static final int LOCATION_COLUMN = 2;
    private static final int USER_COLUMN = 3;
    private static final int TIMESTAMP_COLUMN = 4;
    private static final int IMAGE_COLUMN = 5;


    public static Stream<ImageForm> getImageForms(final String csvData) {
        return Arrays.stream(csvData.split("\n"))
                     .skip(1) // Skip the headers
                     .peek(row -> System.out.println("CSV Row " + row))
                     .map(csvEntry -> csvEntry.split(","))
                     .peek(csvEntry -> System.out.println("CSV Entry " + Arrays.toString(csvEntry)))
                     .map(ImageForm::fromCSVEntryColumns);
    }

    private static ImageForm fromCSVEntryColumns(String[] csvEntryColumns) {
        if (csvEntryColumns.length != 6) {
            throw new IllegalArgumentException("`csvEntryColumns` must be length 5, received " + Arrays.toString(csvEntryColumns));
        }

        val description = csvEntryColumns[DESCRIPTION_COLUMN];

        val weightString = csvEntryColumns[WEIGHT_COLUMN].replace("\"", "");
        System.out.println("Weight String: " + weightString);
        val weight = Double.parseDouble(weightString);

        val location = csvEntryColumns[LOCATION_COLUMN];
        val user = csvEntryColumns[USER_COLUMN];
        val timestamp = csvEntryColumns[TIMESTAMP_COLUMN];
        val image = csvEntryColumns[IMAGE_COLUMN];

        return new ImageForm(description, weight, location, user, timestamp, image);
    }
}
