# Unit Converter Android App

# Android Converter App

## Overview

This project is a modern Android application designed to convert measurements between the U.S. Customary System and the Metric System. The app supports a wide range of categories including length, area, weight, volume, and temperature. It was built with a focus on usability, accuracy, clean design, and real-time interaction, providing users with an intuitive and comfortable experience.

## Functions

The application allows users to:

* Select a category of measurement using a tab-based navigation system
* Choose a specific conversion within that category
* Enter a numeric value and see results update instantly (auto-convert)
* Reverse conversions dynamically using a swap button
* View clearly formatted results including both input and output units
* Clear input quickly using a clear button
* Experience animations when results change
  The app doesn't need a manual "Convert" button as it updates the results automatically while the user is typing or pressing the keyboard’s "Enter".

---

## Project Structure

### `MainActivity.kt`

This is the core file of the application and contains all the main logic.

* **UI Initialization**: It initializes all UI components such as the tab layout, input field, result display, spinner for conversion selection, and swap button.
* **Data Model**: A `Conversion` data class is defined to represent each conversion, including forward and reverse logic.
* **Categories**: Conversions are grouped into categories using a map structure. Each category contains a list of `Conversion` objects.
* **Dynamic UI Updates**:

  * Tabs are generated dynamically usnig category names.
  * The conversion spinner updates based on the selected tab.
* **Conversion Logic**:

  * The `performConversion()` function handles input validation, conversion calculations, formatting, and result display.
  * It supports both forward and reverse conversions depending on user interaction.
* **Auto-Convert Feature**:

  * A `TextWatcher` is used to trigger conversions instantly as the user types.
  * An editor action listener allows conversions when pressing the "Enter" key on the keyboard.
* **Animation**:

  * The `animateResult()` function applies a fade and scale animation whenever the result changes.
* **State Handling**:

  * A boolean flag (`isReversed`) manages whether the conversion direction is swapped.

---

### `activity_main.xml`

This file defines the layout and visual structure of the app.

* **TabLayout**: Used for category navigation (Length, Area, Weight, Volume, Temperature). I decided to replace the earlier spinner-based approach with this for a more modern UI.
* **Spinner**: Displays available conversions within the selected category.
* **TextInputLayout & TextInputEditText**:

  * Provides an input field with a clear button. Allows users to enter "-" sign. During the testing it turned out that this opportunity was missed, the bug was subsequently fixed.
* **Swap Button**: Allows users to reverse conversion direction.
* **Result TextView**: Displays formatted conversion results directly below the input section.

The layout uses a structured vertical flow to guide the user logically from selection to input to result.

---

### `themes.xml` and styling

The app uses a dark theme with deep blue tones. Colors were chosen as my favorite and so that to reduce eye strain. Accent colors are used to highlight interactive elements and important information such as selected tabs and input focus.

---

## Design Decisions

Several important design decisions were made during development:

### 1. Tabs vs Spinner for Categories

Initially, categories were implemented using a spinner (dropdown). However, this was replaced with a tab layout. Tabs provide immediate visibility of available categories and reduce the number of interactions required to switch between them. In my opinion, this change improved usability significantly.

### 2. Auto-Convert Instead of Button-Based Conversion

The original design included a "Convert" button. This was removed in favor of automatic conversion triggered by user input. This decision was based on modern UX practices, where instant feedback is preferred. It makes the app faster to use.

### 3. Data-Driven Conversion Model

Rather than hardcoding conversion logic in multiple places, a structured data model was used. Each conversion is defined with its own forward and reverse functions. This approach improves scalability and maintainability, making it easy to add new conversions or categories in the future.

### 4. Input Handling and Validation

Invalid cases were handled such as empty input, invalid numbers, or partial decimal input (e.g., “.”). Instead of crashing or showing incorrect values, the app clears the result gracefully. This contributes to a comfortable user experience.

### 5. Visual Feedback Through Animation

To enhance the comfort from the app, animations were added when results update. Instead of numbers abruptly changing, they fade and scale smoothly. This subtle effect improves performance and makes interactions feel more natural.

### 6. Clean Input Experience

The input field was improved to include:

* A clear button for quick resets
* A visible cursor for editing
