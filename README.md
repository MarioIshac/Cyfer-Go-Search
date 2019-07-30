Go Search was made for the yuuvis hackathon on July 27-28, 2019
in San Francisco, by team Cyfer.

The vision of this project is to use massively-available public data for enhancing common day-to-day activities of indivudals. We
noticed that such large-scale data was currently being used by businesses for the benefit
of their advertisements/marketing. We applied this data to the improvement of user
experiences outside the app.

It consists of four components:

1) The media scraper, written with Python using Selenium, that produces data consisting of documented user experiences shared through social media and sends it to the backend routinely. 

2) The recommender, written with Python using TensorFlow/Keras, that consumes this data and adjusts itself accordingly. When given a user interest, it will use this data to suggest actions
that the user takes based on their interest and the previous data it was provided with.

3) The backend, written with Java using Spring Boot, that serves as the middle man in this producer/consumer process. It allows
for multiple produces and consumers to hook into it through REST API calls. All data sent through it is uploaded
to the yuuvis database, where it is versioned based on timestamps. This allows for consumers to receive the latest data, corresponding to the newest trends exhibited in society. With this latest data,
the consumers can make up-to-date accurate recommendations. 

4) The user client (a mobile application), written with JavaScript using React Native, where they can explain their interest through an intuitive interface. This client will
send this interest to the backend system, which will forward the interest to the recommender and receive a recommendation, which is
forwarded back to the client for display. The repository for this component is <a href="https://github.com/ed4354/GoSearch">here</a>.

<b> Example </b>

A user goes to a coffee shop every morning to start their work of the day. Through the user client, they would specify
their interest is writing (corresponding to their work as an author). Once this interest is
sent through the whole process, the user sees coffee shops that have a lot of authors coming in doing their work. This allows for networking and collaboration
to be included in a part of their daily routine. The only input the user needed to ever provide
was the fact that they had an interest in writing, expressed through inputting `#writing`. 
