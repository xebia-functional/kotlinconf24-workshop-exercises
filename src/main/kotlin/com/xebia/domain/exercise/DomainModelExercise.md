# Modelling a music-streaming application

In this exercise, we will model the domain for a music-streaming application using the concepts learned in the workshop's functional domain modeling section. The goal is to create a comprehensive and extensible domain model that captures the application's essential entities and behaviors.

## Requirements

The model will have the following main entities:

* **Users**
* **Artists**
* **Songs**
* **Playlists**

### Users
This entity will contain information about the application users. We need to consider at least the following information:

- A numeric identifier
- Their username
- Their email address
- Their password.

***Note about the password:*** The password is sensitive information, so we should avoid logging it to prevent any potential leaks. Additionally, we should ensure the following rules when creating a Password value:
- The password should be at least eight characters long
- It should contain a digit, an uppercase, and a lowercase letter.

#### Free and premium users
There will be two different kinds of users: **Free** and **Premium**. Besides the common properties, each type will contain additional information:

- For `Free` users, we should store the maximum number of skips the users can make when reproducing music per day.
- For `Premium` users, we should store their payment information to charge the subscription fee.

#### Payment Information
The application will offer two payment methods: PayPal and Credit Card.

If the user selects **PayPal**, we need to store their **email address**. If they choose to pay by **credit card**, we will require the following information:

- Card number
- Expiration date (month and year only)

### Artists
This entity will contain information about the musicians who create songs. We need to consider at least the following information:

- A numeric identifier
- Their name

### Songs
This entity will contain information about the tracks that users can listen to. We need to consider at least the following information:

- A numeric identifier
- Its title
- The artists who create the song
- The genre to which the son belongs to
- The date when the song was released
- Its duration

#### Genres
The application's musical genres are Rock, Pop, Jazz, and Classical.

***Note:*** Feel free to add additional ones


### Playlists
This entity will contain information about the collections of songs created by premium users. We need to consider at least the following information:

- A numeric identifier
- A descriptive name for the collection
- The user who owns the playlist. Only premium users can own playlists.
- List of songs included into the playlist