# 💰 Expense Tracker - AI-Powered Finance Manager

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg)](https://kotlinlang.org)
[![Compose Version](https://img.shields.io/badge/Jetpack%20Compose-1.10.0-brightgreen)](https://developer.android.com/jetpack/compose)
[![Firebase](https://img.shields.io/badge/Firebase-FFCA28?logo=firebase&logoColor=black&style=flat-square)](https://firebase.google.com/)
[![Google Gemini API](https://img.shields.io/badge/Google%20Gemini-4285F4?logo=google&logoColor=white&style=flat-square)](https://gemini.google.com/)

Effortlessly manage your personal finances with Expense Tracker, a modern Android app built with Jetpack Compose and powered by Firebase. Track your income and expenses, visualize your spending habits, and even scan physical receipts using Google Gemini's AI for automatic data entry!

## 🌟 Key Features

Expense Tracker helps you stay on top of your money with features like:

### 📊 Smart Transaction Management
*   ✅ **Add & Edit Transactions**: Easily log your income and expenses.
*   ✅ **Detailed Categorization**: Organize transactions across predefined categories (Food, Health, Transport, and more!).
*   ✅ **Real-time Balance**: See your total balance, income, and expenses updated instantly.
*   ✅ **Flexible Period Filters**: View transactions by day, week, month, or year to analyze trends.
*   ✅ **Transaction Details**: Tap into any transaction to see its full breakdown, including invoice details if scanned.

### 🔍 AI Receipt Scanning (Powered by Google Gemini)
*   📸 **Integrated Camera**: Capture clear images of your receipts directly within the app using CameraX.
*   ✨ **Automatic Data Extraction**: Google Gemini analyzes the receipt image to automatically pull out key information like vendor name, date, total amount, and individual items.
*   🏷️ **Smart Categorization**: The AI attempts to categorize your scanned expense.
*   📄 **Structured Invoice Parsing**: Converts receipt text into organized, structured data for easy review and saving.

### 📈 Interactive Analytics
*   📊 **Pie Charts**: Visually understand how your expenses are distributed across different categories.
*   🔍 **Category Breakdown**: Get insights into where your money is going.
*   💡 **Balance Visualization**: See your overall financial health at a glance.

### 🔒 Secure Cloud Sync with Firebase
*   🔐 **Firebase Authentication**: Securely sign in using email/password or Google accounts.
*   🔄 **Cloud Firestore Sync**: Your financial data is synchronized in real-time across all your devices using our NoSQL database, Cloud Firestore.
*   🛡️ **Data Security**: Leveraging Firebase's robust security features for your financial records.

## 🛠 Technical Stack

Expense Tracker is built using cutting-edge Android development technologies:

*   **Frontend**:
    *   **100% Jetpack Compose**: For a modern, declarative, and fluid user interface.
    *   **ViewModel & Coroutines**: Implementing the MVVM pattern for robust state management and asynchronous operations.
    *   **Navigation Compose**: Handling all screen transitions smoothly.
    *   **CameraX**: Providing reliable camera functionality for receipt scanning.
*   **Backend**:
    *   **Firebase Authentication**: Managing user sign-up, login, and sessions.
    *   **Cloud Firestore**: Storing and syncing transaction and user data in a scalable, real-time NoSQL database.
    *   **(Potential for Firebase Storage)**: Although not explicitly used in the provided `Scanning.kt` for *storing* images after parsing, Firebase Storage is a common companion for storing user-uploaded files like receipt images if required in the future.
*   **AI Integration**:
    *   **Google Gemini API**: Powering the intelligent receipt scanning and data extraction.
    *   **Kotlin Serialization**: Parsing the structured JSON output from the Gemini API.

## 📱 Screenshots

Check out how Expense Tracker looks in action:

| Home Screen                                   | Receipt Scanning                                | Analytics                                   |
|-----------------------------------------------|-------------------------------------------------|---------------------------------------------|
| ![Home](screenshots/home.png)                 | ![Scan](screenshots/scan.png)                   | ![Stats](screenshots/stats.png)             |
| **Transaction Detail**                        | **Add Transaction**                             | **Profile**                                 |
| ![Detail](screenshots/detail.png)             | ![Add](screenshots/add.png)                     | ![Profile](screenshots/profile.png)         |

*(Note: Screenshot images are representative)*

## Installation & Setup 🚀

To get Expense Tracker running on your local machine:

### Prerequisites
*   Android Studio Ladybug Feature Drop (2025.04.01) or later.
*   Android SDK 33+ installed.
*   A Firebase project setup (you already have `expense-tracker-b84b3`!).
*   Enable **Authentication** (Email/Password and Google providers) and **Cloud Firestore** in your Firebase project.
*   Obtain a Google Gemini API Key from Google AI Studio ([aistudio.google.com/apikey](https://aistudio.google.com/apikey)). You'll need to update the `BuildConfig.kt` file with this key.

### Firebase Setup (Quick Check)
Based on the information you provided, your Firebase project `expense-tracker-b84b3` already has:
*   Firestore configured in `nam5`.
*   Authentication enabled with Email/Password and Google providers.
*   Security Rules for Firestore are defined for user management, transactions, categories, etc.

You just need to make sure the Android app is connected to this project (usually done by adding the `google-services.json` file to the `app` module).

### Steps
1.  **Clone the repository**
    ```bash
    git clone https://github.com/enori-ben/Expense-Tracker.git
    ```
2.  **Open in Android Studio**
    *   Select "Open an Existing Project"
    *   Navigate to and select the cloned `Expense-Tracker` directory.
3.  **Add Google Services File:**
    *   In the Firebase console for `expense-tracker-b84b3`, add an Android app.
    *   Follow the steps to register your app using the package name `com.example.expensetracker`.
    *   Download the `google-services.json` file.
    *   Place the `google-services.json` file in your Android project's `app/` directory.
4.  **Add Google Gemini API Key:**
    *   Open the `BuildConfig.kt` file (usually located in the root of your `app` module's source sets, e.g., `app/src/main/java/com/example/expensetracker/BuildConfig.kt`).
    *   Replace `"AIzaSyB04Rd-g4N0Cio9NX1ok9w0j9QjL_6mMgc"` with your actual Gemini API Key. **Do NOT commit your API key directly to public repositories!** Consider using environment variables or build configurations for sensitive keys.
5.  **Build and Run**
    *   Sync your project with Gradle files (File > Sync Project with Gradle Files).
    *   Click the ▶️ Run button in Android Studio.
    *   Choose a connected Android device or emulator (API 33+ recommended).

## Contributing 🤝
We welcome contributions to improve Expense Tracker! Please follow these guidelines:

1.  **Fork the repository**.
2.  **Create a feature branch**:
    ```bash
    git checkout -b feature/your-feature-name
    ```
3.  **Implement your feature or fix**.
4.  **Write clear commit messages**: Follow Conventional Commits format (e.g., `feat: add user registration screen`, `fix: resolve date parsing issue`).
5.  **Ensure code follows Kotlin style guide** and keep composables focused.
6.  **Add test coverage** for new features where applicable.
7.  **Update documentation** (like this README) if your changes affect functionality or setup.
8.  **Push your branch** and create a Pull Request to the `main` branch of the original repository.

## License 📄
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Contact
For any inquiries or feedback, feel free to contact the project author:

*   **EnNori** at enoridz11@gmail.com

---
