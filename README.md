# PJATK - Mobile programming - Project I

## 🥔 Too good to throw 🍅
**An Android app to track and manage product expiration dates**  
Helps you reduce waste by reminding you of upcoming expirations for food, medicine & cosmetics.

---

## 📦 Features

- **Product List**
    - Automatically sorted by expiration date (soonest first)
    - Filters:
        - By category (Food, Medicine, Cosmetics)
        - “Only valid” (hide expired items)
    - Summary count of visible items
    - Long-press actions:
        - Delete a still-valid product
        - Mark an expired product as discarded
    - Snackbar notifications for invalid operations (e.g. editing expired items)

- **Add / Edit Product**
    - Fields:
        - **Name** (required)
        - **Expiration Date** (DatePicker, must be ≥ today)
        - **Category** (dropdown)
        - **Quantity** + **Unit** (quantity must be numeric; unit required if quantity provided)
        - **Image** from device gallery (saved as a thumbnail)
    - In-place updates via Room (no delete+reinsert)
    - Real-time validation with inline error messages

- **Architecture & Tech**
    - Jetpack Compose & Material3
    - MVVM: ViewModel + StateFlow + Coroutines
    - Local storage with Room (preloaded sample data on first launch)
    - Simple dependency injection via `AppContainer`

---


