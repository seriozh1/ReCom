# ReCom: Road Problem Reporting App

An Android app that lets citizens report road problems, such as potholes or damaged signs, and send them to city road services. Built in 2018 with Artem Nazarishin at Samsung IT School (Krasnoyarsk) and presented at the final stage of the school's national project competition in Moscow.

![ReCom](Files/main_slide.png)

[Presentation (Russian)](Files/ReCom_presentation.pdf) · [Project report (Russian)](Files/ReCom_Note.pdf) · [Demo video](Files/ReCom_Final.mp4)

## The idea

Many people want better roads but have no simple way to help. With ReCom, a user photographs a problem, marks it on the city map, picks its type and submits a report. After moderation, the report goes to the traffic police or the service responsible for road maintenance. City services, in turn, get a map of problem spots based on what residents report.

## Features

- User registration and login
- City map with reported problem points, refreshed for all users
- Creating a report with location, problem type and description, with drafts
- Report history, news feed and directory of city services
- User profile and a rating of the most active reporters

## Implementation

- **Android client** (this repository): Java, Android Studio, minimum API 19. The interface is built from fragments behind a navigation drawer, with Google Maps for the city map.
- **Networking:** Retrofit for registration, login and exchanging report data with the server.
- **Server:** a Spring backend developed separately: [Salving/Roads](https://github.com/Salving/Roads).

## Repository contents

| Path | Contents |
|---|---|
| `app/src/main/java/.../auth` | Registration and login screens |
| `app/src/main/java/.../fragments` | App screens: map, new report, drafts, news, profile, rating, settings |
| `app/src/main/java/.../interfaces` | Retrofit API definitions |
| `app/src/main/java/.../model` | Data models: user, report, note, problem point |
| `Files/` | Presentation, project report, demo video |
