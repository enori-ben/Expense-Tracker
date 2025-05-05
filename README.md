# 💰 Expense Tracker - AI-Powered Finance Manager

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg)](https://kotlinlang.org)
[![Compose Version](https://img.shields.io/badge/Jetpack%20Compose-1.10.0-brightgreen)](https://developer.android.com/jetpack/compose)

Expense Tracker is a modern Android application that helps users effortlessly manage their personal finances. With AI-powered receipt scanning and beautiful data visualizations, it transforms financial tracking into a seamless experience.

## 🌟 Key Features

### 📊 Smart Transaction Management
- **Add/Edit Transactions**: Log income and expenses with ease
- **Detailed Categorization**: 8+ categories (Food, Health, Transport, etc.)
- **Balance Overview**: Real-time view of total balance, income & expenses
- **Period Filters**: View by day, week, month, or year

### 🔍 AI Receipt Scanning (Powered by Google Gemini)
- **Camera Integration**: Scan receipts with CameraX
- **Auto Data Extraction**: Extracts vendor, amount, date, and items
- **Smart Categorization**: Automatically classifies expenses
- **Invoice Parsing**: Converts receipts to structured JSON data

### 📈 Interactive Analytics
- **Pie Charts**: Visualize spending distribution
- **Category Breakdown**: Understand spending habits
- **Historical Data**: Track financial trends over time

### 🔒 Secure Cloud Sync
- **Firebase Authentication**: Email & Google sign-in
- **Real-time Sync**: Cloud Firestore keeps data updated across devices
- **Encrypted Storage**: Financial data protected at rest

## 🛠 Technical Stack

### Frontend
- **100% Jetpack Compose** - Modern declarative UI
- **CameraX** - For receipt scanning
- **ViewModel & Coroutines** - State management
- **Navigation Compose** - Screen transitions

### Backend
- **Firebase Auth** - Secure authentication
- **Cloud Firestore** - NoSQL database
- **Firebase Storage** - Receipt image storage

### AI Integration
- **Google Gemini API** - For receipt parsing
- **Custom JSON Parser** - Structured data extraction

## 📱 Screenshots

| Home Screen | Receipt Scanning | Analytics |
|-------------|------------------|-----------|
| ![Home](screenshots/home.png) | ![Scan](screenshots/scan.png) | ![Stats](screenshots/stats.png) |

| Transaction Detail | Add Transaction | Profile |
|--------------------|-----------------|---------|
| ![Detail](screenshots/detail.png) | ![Add](screenshots/add.png) | ![Profile](screenshots/profile.png) |

## Installation & Setup 🚀

### Prerequisites
- Android Studio Ladybug Feature Drop (2025.04.01)
- Android SDK 33+
- Kotlin 2.0.0

### Steps
1. **Clone the repository**
   ```bash
   git clone https://github.com/enori-ben/Expense-Tracker.git
   ```
2. **Open in Android Studio**
    - Select "Open an Existing Project"
    - Choose the cloned directory

3. **Build and Run**
    - Click the ▶️ Run button in Android Studio
    - Choose a connected device or emulator

## Contributing 🤝
We welcome contributions! Please follow these guidelines:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Commit changes**
    - Follow Conventional Commits format
    - Example: `feat: add user registration screen`
4. **Push and create Pull Request**
    - Ensure code follows Kotlin style guide
    - Include test coverage for new features
    - Update documentation when applicable

### Code Style
- Use meaningful variable names
- Follow official Kotlin coding conventions
- Keep composables small and focused
- Use ViewModel for business logic

## License 📄
This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Contact
For any inquiries, feel free to contact **EnNori** at enoridz11@gmail.com

