# DHBW-Rapla-Delivery

## Important
This backend wont work for every rapla-calendar.
Its only tested with the rapla calendar we have for our course.

## Why ?
Since the DHBW hasn't configured the rapla calendar to be able to subscribe it with an apple calendar, I decided to build my own application, so I can subscribe the rapla calendar.

## What does it do ?
The application parses the Rapla HTML file located at an url and exposes the file with the help of an REST-Controller.

## Libs used
* ICal4J - Used to generate the .ics file
* Java Spring - To provide the .ics file on an endpoint
* Jsoup - To retrieve and parse the html File

## License
MIT License

## Endpoints
The spring backend provides 2 endpoints to subscribe:

* Endpoint Google: https://rapla.cloudypanda.de/calendars/google/TINF19DRapla.ics (UTC) 
* Endpoint IOS: https://rapla.cloudypanda.de/calendars/ios/TINF19DRapla.ics (GMT)
