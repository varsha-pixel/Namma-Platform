**Namma-Platform 🚆**

Namma-Platform is a Kannada-first railway assistance app designed for small railway stations and rural passengers.
The app helps users easily identify:

* Next arriving trains
* Platform numbers
* Coach sequence
* Kannada voice announcements

The application focuses on accessibility, simplicity, and inclusion for non-English speakers.

# Problem Statement

In many small railway stations:

* Train announcements are unclear
* Audio is not loud enough
* Information is only available in English/Hindi
* Elderly and rural passengers get confused about platforms and coach positions

Namma-Platform solves this problem using a simple Kannada-first interface.

# Features

## 🚉 Live Train Information

* Shows only the next 3 upcoming trains
* Filters trains based on current time
* Displays train name first, then train number

##  Platform Guidance

* Clearly shows platform number
* High contrast UI using blue/yellow theme

## 🚆 Coach Layout

* Visual coach strip:

  * Engine
  * General
  * Sleeper
  * AC coaches
* User can tap coaches to view guidance

## 🔊 Kannada Voice Announcements

* Loud Kannada Text-To-Speech announcements
* Helps rural passengers understand train details clearly

## 📅 Date Selection

* Calendar date picker added
* Users can check trains for tomorrow or any selected date

## 🛤 Station Selection

* Dropdown station selection
* Multiple Karnataka stations included



# Tech Stack

* Kotlin
* Jetpack Compose
* Material 3
* Android Studio
* Text-To-Speech (TTS)
* Local JSON Data


# Project Structure

```plaintext
app/
 ├── data/
 │    ├── Train.kt
 │    ├── TrainRepository.kt
 │
 ├── ui/
 │    ├── components/
 │    │      ├── TrainCard.kt
 │    │      ├── CoachStrip.kt
 │    │      ├── PlatformGuidanceView.kt
 │    │      ├── TrainViewModel.kt
 │
 ├── utils/
 │    ├── KannadaSpeaker.kt
 │
 ├── MainActivity.kt
```

# Stations Included

* Bengaluru City
* Mysuru Junction
* Hubballi Junction
* Belagavi
* Kalaburagi
* Shivamogga
* Hassan
* Mandya
* Tumakuru
* Arasikere
* Chamarajanagar

# Success Criteria Achieved

✅ Kannada-first railway assistant
✅ Clear coach layout
✅ High contrast UI
✅ Loud Kannada announcements
✅ Next 3 trains only
✅ Platform visibility
✅ Rural-friendly design


# Future Improvements

* Real-time railway API integration
* Live GPS tracking
* Offline support
* Multi-language support
* AI-based crowd prediction



