# 📱 Smart Grocery Recipe – Android App (Java)

Smart Grocery Recipe is an intelligent Android application that scans handwritten or printed grocery lists using OCR, extracts ingredients, and generates personalized recipes — all on-device and offline friendly.

This project is built with **Java, MVVM Architecture, ML Kit OCR, RecyclerView**, and **Material Components**, packaged into a clean and modern UI.

---

## 🚀 Features

### 📝 OCR-Based Grocery Scanning
- Capture grocery lists using the camera
- Uses **Google ML Kit On-Device Text Recognition**
- Supports handwriting & printed text

### 🥕 Automatic Ingredient Extraction
- Parses raw OCR text into structured ingredient objects
- Identifies quantity, unit, and ingredient names
- Works even with incomplete formats

### 🍳 Recipe Generation
- Local rule-based recipe generator
- Converts grocery items into meaningful, simple recipes
- Outputs **JSON-formatted** recipe results

### 🎨 Modern & Attractive UI
- Material-themed layout
- App logo + image preview
- Clean design with RecyclerView ingredient listing

### 📦 MVVM Architecture
- ViewModel + LiveData for state management
- Repository layer for future scalability
- Clean separation of business logic & UI

---

## 🛠️ Tech Stack

| Component | Technology |
|----------|-------------|
| Language | Java |
| Architecture | MVVM |
| OCR | Google ML Kit (On-device) |
| UI | XML, RecyclerView, Material Components |
| Build System | Gradle + Version Catalog |
| Image Capture | Camera Intent |
| JSON | Gson |

---

## 📸 Screenshots

<img width="540" height="1200" alt="image" src="https://github.com/user-attachments/assets/994f6db9-bcb1-462d-8a8e-39dafd205ff5" />

