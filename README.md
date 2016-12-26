# IoT | Weather #

> [**Android Things**](https://developer.android.com/things/index.html) - Internet of Things platform


A quick and dirty *Android Things* prototype application that displays live weather using the [DarkSky API](https://darksky.net/dev/). Originally developed on the [Raspberry Pi 3](https://www.raspberrypi.org/products/raspberry-pi-3-model-b/) with the [Rainbow HAT](https://shop.pimoroni.com/products/rainbow-hat-for-android-things). 

The project uses the usual bits, mainly [Dagger 2](https://github.com/google/dagger) for dependency injection, [RxJava 2](https://github.com/ReactiveX/RxJava) for asynchronous/periodic network requests and [Retrofit 2](https://github.com/square/retrofit) for networking. The project loosely follows the MVC architectural pattern, however I'm sure it has tons of room for improvement.

Uses the [Rainbow HAT contrib-drivers](https://github.com/androidthings/contrib-drivers/tree/master/rainbowhat#sample-usage), offloading most of the low level hardware implementation to the library.

**Features:**

- Displays the current temperature for the current hour.
- Network updates every 10 minutes, display will update every minute.
- Plays [Song of Storms](https://www.youtube.com/watch?v=43IPAGw01IY) if rain is more than 50% likely within the next hour.
- Pressing 'C' will display how many minutes until it will rain if rain is within the next hour.
- Displays temperature as a colour on the LED strip for the current hour and the next 6 hours.

**Usage:**

1. Download project.
2. Obtain a DarkSky API key from [here](https://darksky.net/dev/).
3. In the project root folder, open `config/common.properties` and paste your key where it says `dark_sky_key=PASTE KEY HERE`
4. Set a latitude and longitude in the same `common.properties` file.
5. ????
6. You should be all set!

